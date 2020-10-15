package org.dxworks.githubminer.http

import com.google.api.client.http.GenericUrl
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.HttpResponseException
import com.google.api.client.http.HttpStatusCodes.STATUS_CODE_FORBIDDEN
import com.google.api.client.http.HttpStatusCodes.STATUS_CODE_NOT_FOUND
import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.RATE_LIMIT_PATH
import org.dxworks.githubminer.dto.rate.RateLimit
import org.dxworks.githubminer.utils.GithubBearerAuthenticationProvider
import org.dxworks.utils.java.rest.client.HttpClient
import org.dxworks.utils.java.rest.client.response.HttpResponse
import org.slf4j.LoggerFactory
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.*

class GithubHttpClient(private val githubTokens: List<String>, private val githubBasePath: String) : HttpClient(defaultHttpRequestInitializer) {
    private var rateLimitEnabled = true

    init {
        getTokenRateLimits()
    }

    override fun get(url: GenericUrl, customRequestInitializer: HttpRequestInitializer?): HttpResponse {
        log.info("[GET] $url")
        return performRequest { GithubHttpResponse(super.get(url, GithubBearerAuthenticationProvider(it))) }

    }

    override fun patch(url: GenericUrl, body: Any?, customRequestInitializer: HttpRequestInitializer?): HttpResponse {
        log.info("[PATCH] $url")
        return performRequest { GithubHttpResponse(super.patch(url, body, GithubBearerAuthenticationProvider(it))) }
    }

    override fun post(url: GenericUrl, body: Any?, customRequestInitializer: HttpRequestInitializer?): HttpResponse {
        log.info("[POST] $url")
        return performRequest { GithubHttpResponse(super.post(url, body, GithubBearerAuthenticationProvider(it))) }
    }

    override fun put(url: GenericUrl?, body: Any?, customRequestInitializer: HttpRequestInitializer?): HttpResponse {
        log.info("[PUT] $url")
        return performRequest { GithubHttpResponse(super.put(url, body, GithubBearerAuthenticationProvider(it))) }
    }

    private fun getTokenRateLimits() {
        var response: GithubHttpResponse? = null
        for (token in githubTokens) {
            try {
                response = GithubHttpResponse(super.get(GenericUrl("$githubBasePath/$RATE_LIMIT_PATH"),
                        GithubBearerAuthenticationProvider(token)))
                tokenRateLimits[token] = response.rateLimit
            } catch (e: HttpResponseException) {
                if (severRateLimitDisabled(e)) {
                    this.rateLimitEnabled = false
                    return
                }
                throw e
            } finally {
                response?.response?.content?.close()
            }
        }
    }

    private fun performRequest(doRequest: (token: String) -> GithubHttpResponse): HttpResponse {
        if (!rateLimitEnabled)
            return doRequest(githubTokens.firstOrNull() ?: ANONYMOUS)

        return tryPerformRequestConsideringRateLimit(doRequest)
    }

    private fun tryPerformRequestConsideringRateLimit(doRequest: (token: String) -> GithubHttpResponse): GithubHttpResponse {
        while (true) {
            val activeTokens = retrieveActiveTokens()
            var response:GithubHttpResponse? = null
            if (activeTokens.isEmpty()) {
                waitUntilSoonestRateLimitReset()
            } else {
                val usedToken = activeTokens.first()
                try {
                    response = doRequest(usedToken).also {
                        tokenRateLimits[usedToken] = it.rateLimit
                    }
                    return response
                } catch (e: HttpResponseException) {
                    if (rateLimitExceeded(e)) tokenRateLimits[usedToken] = GithubHttpResponse(super.get(GenericUrl("$githubBasePath/$RATE_LIMIT_PATH"),
                            GithubBearerAuthenticationProvider(usedToken))).rateLimit
                    else throw e
                } finally {
                    response?.response?.content?.close()
                }
            }
        }
    }

    private fun waitUntilSoonestRateLimitReset() {
        val waitUntil = tokenRateLimits.values.minBy { it.resetTimestamp }!!.resetTimestamp
        log.info("Waiting until $waitUntil")
        Thread.sleep((waitUntil.toEpochSecond(ZoneOffset.UTC) - ZonedDateTime.now(ZoneOffset.UTC).toEpochSecond() + 1) * 1000)
        getTokenRateLimits()
    }

    private fun retrieveActiveTokens(): List<String> {
        return tokenRateLimits
                .filter { it.value.remainingRequests > 0 }
                .map { it.key }
    }

    private fun severRateLimitDisabled(e: HttpResponseException) =
            e.statusCode == STATUS_CODE_NOT_FOUND && e.content.contains("Rate limiting is not enabled")

    private fun rateLimitExceeded(e: HttpResponseException) =
            e.statusCode == STATUS_CODE_FORBIDDEN && e.content.contains("API rate limit exceeded for")

    companion object {
        private val tokenRateLimits: MutableMap<String, RateLimit> = HashMap()
        private val defaultHttpRequestInitializer = HttpRequestInitializer { it.readTimeout = 5000 }
        private val log = LoggerFactory.getLogger(GithubHttpClient::class.java)
    }
}

