package org.dxworks.githubminer.http

import com.google.api.client.http.GenericUrl
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.HttpResponseException
import org.dxworks.githubminer.dto.rate.RateLimit
import org.dxworks.githubminer.utils.GithubBearerAuthenticationProvider
import org.dxworks.utils.java.rest.client.HttpClient
import org.dxworks.utils.java.rest.client.response.HttpResponse
import org.slf4j.LoggerFactory
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.*

class GithubHttpClient(private val githubTokens: List<String>, private val githubBasePath: String) : HttpClient(defaultHttpRequestInitializer) {
    init {
        getTokenRateLimits()
    }

    private fun getTokenRateLimits() {
        githubTokens.forEach { token ->
            Thread.sleep(500)
            tokenRateLimits[token] = getRequestLimitFor(token)
        }
    }

    private fun getRequestLimitFor(token: String) =
            GithubHttpResponse(super.get(GenericUrl("${githubBasePath}/rate_limit"),
                    GithubBearerAuthenticationProvider(token))).rateLimit

    private fun postRequest(token: String, response: GithubHttpResponse) {
        tokenRateLimits[token] = response.rateLimit
    }

    private fun preRequest(): String {
        var activeTokens: List<String>
        do {
            activeTokens = tokenRateLimits
                    .filter { it.value.remainingRequests > 0 }
                    .map { it.key }

            if (activeTokens.isEmpty()) {
                val waitUntil = tokenRateLimits.values.minBy { it.resetTimestamp }!!.resetTimestamp
                log.info("Waiting until ${waitUntil}")
                Thread.sleep((waitUntil.toEpochSecond(ZoneOffset.UTC) - ZonedDateTime.now(ZoneOffset.UTC).toEpochSecond() + 1) * 1000)
                getTokenRateLimits()
            }
        } while (activeTokens.isEmpty())

        return activeTokens.first()
    }

    override fun get(url: GenericUrl, customRequestInitializer: HttpRequestInitializer?): HttpResponse {
        val usedToken = preRequest()
        try {
            val response = GithubHttpResponse(super.get(url, GithubBearerAuthenticationProvider(usedToken)))
            postRequest(usedToken, response)
            return response
        } catch (e: HttpResponseException) {
            if (e.statusCode == 403 && e.content.contains("API rate limit exceeded for")) {
                tokenRateLimits[usedToken] = getRequestLimitFor(usedToken)
                return get(url, customRequestInitializer)
            }
            throw e
        }
    }

    override fun patch(url: GenericUrl, body: Any?, customRequestInitializer: HttpRequestInitializer?): HttpResponse {
        val usedToken = preRequest()
        val response = GithubHttpResponse(super.patch(url, body, GithubBearerAuthenticationProvider(usedToken)))
        postRequest(usedToken, response)
        return response
    }

    override fun post(url: GenericUrl, body: Any?, customRequestInitializer: HttpRequestInitializer?): HttpResponse {
        val usedToken = preRequest()
        val response = GithubHttpResponse(super.post(url, body, GithubBearerAuthenticationProvider(usedToken)))
        postRequest(usedToken, response)
        return response
    }

    override fun put(url: GenericUrl?, body: Any?, customRequestInitializer: HttpRequestInitializer?): HttpResponse {
        val usedToken = preRequest()
        val response = GithubHttpResponse(super.put(url, body, GithubBearerAuthenticationProvider(usedToken)))
        postRequest(usedToken, response)
        return response
    }

    companion object {
        private val tokenRateLimits: MutableMap<String, RateLimit> = HashMap()
        private val defaultHttpRequestInitializer = HttpRequestInitializer { it.readTimeout = 5000 }
        private val log = LoggerFactory.getLogger(GithubHttpClient::class.java)
    }
}

