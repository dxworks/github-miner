package org.dxworks.githubminer.http

import com.google.api.client.http.GenericUrl
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.HttpResponseException
import com.google.api.client.http.HttpResponseInterceptor
import com.google.api.client.http.HttpStatusCodes.*
import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.RATE_LIMIT_PATH
import org.dxworks.githubminer.dto.rate.RateLimit
import org.dxworks.githubminer.utils.GithubBearerAuthenticationProvider
import org.dxworks.utils.java.rest.client.HttpClient
import org.dxworks.utils.java.rest.client.providers.CompositeHttpRequestInitializer
import org.dxworks.utils.java.rest.client.response.HttpResponse
import org.slf4j.LoggerFactory
import java.lang.reflect.Type
import java.time.ZoneOffset
import java.time.ZonedDateTime

open class GithubHttpClient(private val githubTokens: List<String>, private val githubBasePath: String) :
    HttpClient(defaultHttpRequestInitializer) {
    private var rateLimitEnabled = true
    private val invalidTokes = HashSet<String>()
    private val validTokens get() = githubTokens.filterNot { invalidTokes.contains(it) }

    init {
        getTokenRateLimits()
    }

    override fun get(url: GenericUrl, customRequestInitializer: HttpRequestInitializer?): HttpResponse {
        return performRequest(url) { genericUrl, token ->
            GithubHttpResponse(
                super.get(
                    genericUrl,
                    CompositeHttpRequestInitializer(GithubBearerAuthenticationProvider(token), customRequestInitializer)
                )
            )
        }
    }

    override fun patch(url: GenericUrl, body: Any?, customRequestInitializer: HttpRequestInitializer?): HttpResponse {
        log.info("[PATCH] $url")
        return performRequest(url) { genericUrl, token ->
            GithubHttpResponse(
                super.patch(
                    genericUrl,
                    body,
                    CompositeHttpRequestInitializer(GithubBearerAuthenticationProvider(token), customRequestInitializer)
                )
            )
        }
    }

    override fun post(url: GenericUrl, body: Any?, customRequestInitializer: HttpRequestInitializer?): HttpResponse {
        log.info("[POST] $url")
        return performRequest(url) { genericUrl, token ->
            GithubHttpResponse(
                super.post(
                    genericUrl,
                    body,
                    CompositeHttpRequestInitializer(GithubBearerAuthenticationProvider(token), customRequestInitializer)
                )
            )
        }
    }

    override fun put(url: GenericUrl?, body: Any?, customRequestInitializer: HttpRequestInitializer?): HttpResponse {
        log.info("[PUT] $url")
        return url?.let {
            performRequest(it) { genericUrl, token ->
                GithubHttpResponse(
                    super.put(
                        genericUrl,
                        body,
                        CompositeHttpRequestInitializer(
                            GithubBearerAuthenticationProvider(token),
                            customRequestInitializer
                        )
                    )
                )
            }
        } ?: GithubHttpErrorResponse(url, NullPointerException("Url was null"))
    }

    private fun getTokenRateLimits() {
        var response: GithubHttpResponse?
        for (token in validTokens) {
            try {
                response = GithubHttpResponse(
                    super.get(
                        GenericUrl("$githubBasePath/$RATE_LIMIT_PATH"),
                        GithubBearerAuthenticationProvider(token)
                    )
                )
                tokenRateLimits[token] = response.rateLimit
                response.readContentString()
            } catch (e: HttpResponseException) {
                if (severRateLimitDisabled(e)) {
                    this.rateLimitEnabled = false
                    return
                }
                throw e
            }
        }
    }

    private fun performRequest(url: GenericUrl, doRequest: (url: GenericUrl, token: String) -> GithubHttpResponse) =
        try {
            if (!rateLimitEnabled)
                doRequest(url, validTokens.firstOrNull() ?: ANONYMOUS)
            else
                tryPerformRequestConsideringRateLimit(url, doRequest)
        } catch (e: Exception) {
            GithubHttpErrorResponse(url, e)
        }

    private fun tryPerformRequestConsideringRateLimit(
        url: GenericUrl,
        doRequest: (url: GenericUrl, token: String) -> GithubHttpResponse
    ): GithubHttpResponse {
        while (true) {
            val activeTokens = retrieveActiveTokens()
            var response: GithubHttpResponse?
            if (activeTokens.isEmpty()) {
                waitUntilSoonestRateLimitReset()
            } else {
                val usedToken = activeTokens.first()
                try {
                    response = doRequest(url, usedToken).also {
                        tokenRateLimits[usedToken] = it.rateLimit
                    }
                    return response
                } catch (e: HttpResponseException) {
                    log.warn(e.content)
                    when {
                        rateLimitExceeded(e) -> tokenRateLimits[usedToken] = GithubHttpResponse(
                            super.get(
                                GenericUrl("$githubBasePath/$RATE_LIMIT_PATH"),
                                GithubBearerAuthenticationProvider(usedToken)
                            )
                        ).rateLimit
                        abuseMechanismTriggered(e) -> tokenRateLimits[usedToken]?.resetTimestampFromAbuse(e)
                        isNotFound(e) -> invalidTokes.add(usedToken)
                    }
                }
            }
        }
    }

    private fun isNotFound(e: HttpResponseException) = e.statusCode == STATUS_CODE_NOT_FOUND

    private fun waitUntilSoonestRateLimitReset() {
        val waitUntil = tokenRateLimits.values.minOf { it.resetTimestamp }
        log.info("Waiting until $waitUntil")
        Thread.sleep(
            (waitUntil.toEpochSecond(ZoneOffset.UTC) - ZonedDateTime.now(ZoneOffset.UTC).toEpochSecond() + 1) * 1000
        )
        getTokenRateLimits()
    }

    private fun retrieveActiveTokens(): List<String> {
        return tokenRateLimits
            .filterNot { invalidTokes.contains(it.key) }
            .filter { it.value.remainingRequests > 0 }
            .map { it.key }
    }

    private fun severRateLimitDisabled(e: HttpResponseException) =
        e.statusCode == STATUS_CODE_NOT_FOUND && e.content.contains("Rate limiting is not enabled")

    private fun rateLimitExceeded(e: HttpResponseException) =
        e.statusCode == STATUS_CODE_FORBIDDEN &&
                e.content.contains("API rate limit exceeded for")

    private fun abuseMechanismTriggered(e: HttpResponseException) =
        e.statusCode == STATUS_CODE_FORBIDDEN &&
                e.content.contains("You have triggered an abuse detection mechanism.")

    companion object {
        private val tokenRateLimits: MutableMap<String, RateLimit> = HashMap()
        private val defaultHttpRequestInitializer = HttpRequestInitializer {
            it.readTimeout = 50000
            it.throwExceptionOnExecuteError = false
            log.info(it.url.toString())
            it.responseInterceptor = HttpResponseInterceptor {
                println("${it.request.requestMethod} ${it.statusCode} ${it.statusMessage}")
            }
        }
        private val log = LoggerFactory.getLogger(GithubHttpClient::class.java)
    }
}

fun <T> HttpResponse.parseIfOk(dataClass: Class<T>, orElseGet: ((HttpResponse) -> T?)? = null) =
    if (statusCode == STATUS_CODE_OK) parseAs(dataClass) else orElseGet?.invoke(this)

fun HttpResponse.parseIfOk(dataClass: Type, orElseGet: ((HttpResponse) -> Any?)? = null): Any? =
    if (statusCode == STATUS_CODE_OK) parseAs(dataClass) else orElseGet?.invoke(this)
