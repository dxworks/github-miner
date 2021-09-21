package org.dxworks.githubminer

import com.google.api.client.http.GenericUrl
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.HttpStatusCodes.STATUS_CODE_NOT_MODIFIED
import com.google.api.client.http.LowLevelHttpRequest
import com.google.api.client.http.LowLevelHttpResponse
import com.google.api.client.json.Json
import com.google.api.client.testing.http.MockHttpTransport
import com.google.api.client.testing.http.MockLowLevelHttpRequest
import com.google.api.client.testing.http.MockLowLevelHttpResponse
import com.google.common.net.HttpHeaders.IF_NONE_MATCH
import org.dizitart.no2.objects.ObjectRepository
import org.dizitart.no2.objects.filters.ObjectFilters
import org.dxworks.githubminer.http.GithubHttpClient
import org.dxworks.githubminer.http.GithubHttpResponse
import org.dxworks.githubminer.utils.EXCEPTION_STATUS_CODE
import org.dxworks.utils.java.rest.client.providers.CompositeHttpRequestInitializer
import org.dxworks.utils.java.rest.client.response.HttpResponse

class CachingGithubHttpClient(
    private val repo: ObjectRepository<GithubResponseCache>,
    githubTokens: List<String>, githubBasePath: String
) : GithubHttpClient(
    githubTokens,
    githubBasePath
) {
    override fun get(url: GenericUrl, customRequestInitializer: HttpRequestInitializer?): HttpResponse {
        return getCachedResponse(url)?.takeIf { it.statusCode != EXCEPTION_STATUS_CODE }?.let { cachedResponse ->
            val response = super.get(
                url, getModifiedCheckingInitializer(customRequestInitializer, cachedResponse)
            )
            if (response.statusCode == STATUS_CODE_NOT_MODIFIED)
                cachedResponse.apply {
                    headers.putAll(response.headers)
                    repo.update(this)
                }.toHttpResponse()
            else
                response.also { repo.update(it.toGithubResponseCache()) }
        } ?: super.get(url, customRequestInitializer).also { repo.insert(it.toGithubResponseCache()) }
    }

    private fun getModifiedCheckingInitializer(
        customRequestInitializer: HttpRequestInitializer?,
        cachedResponse: GithubResponseCache
    ) = CompositeHttpRequestInitializer(
        customRequestInitializer,
        HttpRequestInitializer {
            it.headers[IF_NONE_MATCH] = cachedResponse.headers.eTag
        })

    private fun getCachedResponse(url: GenericUrl) =
        repo.find(ObjectFilters.eq("url", url.toString())).firstOrNull()
}


fun HttpResponse.toGithubResponseCache() = GithubResponseCache(
    url = request.url.toString(),
    body = parseAsString(),
    headers = headers,
    statusCode = statusCode
)

fun GithubResponseCache.toHttpResponse(): HttpResponse {
    val cache = this
    return GithubHttpResponse(HttpResponse(object : MockHttpTransport() {
        override fun buildRequest(method: String, url: String): LowLevelHttpRequest {
            return object : MockLowLevelHttpRequest() {
                override fun execute(): LowLevelHttpResponse {
                    val result = MockLowLevelHttpResponse()
                    result.contentType = Json.MEDIA_TYPE
                    result.setContent(cache.body)
                    result.statusCode = cache.statusCode
                    cache.headers.forEach {
                        result.addHeader(it.key, it.value.toString())
                    }
                    return result
                }
            }
        }
    }.createRequestFactory().buildGetRequest(GenericUrl(url)).execute()))
}
