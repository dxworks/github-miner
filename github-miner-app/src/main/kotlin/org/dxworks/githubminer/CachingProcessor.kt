package org.dxworks.githubminer

import com.google.api.client.http.LowLevelHttpRequest
import com.google.api.client.http.LowLevelHttpResponse
import com.google.api.client.json.Json
import com.google.api.client.testing.http.MockHttpTransport
import com.google.api.client.testing.http.MockLowLevelHttpRequest
import com.google.api.client.testing.http.MockLowLevelHttpResponse
import org.dizitart.no2.objects.ObjectRepository
import org.dxworks.utils.java.rest.client.response.HttpResponse


class CachingProcessor(private val repo: ObjectRepository<GithubResponseCache>) : (HttpResponse) -> HttpResponse {
    override fun invoke(response: HttpResponse): HttpResponse {
        repo.insert(response.toGithubResponseCache())
        return response
    }
}

fun HttpResponse.toGithubResponseCache() = GithubResponseCache(
    url = request.url,
    body = this.parseAsString(),
    headers = this.headers,
)

fun GithubResponseCache.toHttpResponse(): HttpResponse {
    val x = this
    return HttpResponse(object : MockHttpTransport() {
        override fun buildRequest(method: String, url: String): LowLevelHttpRequest {
            return object : MockLowLevelHttpRequest() {
                override fun execute(): LowLevelHttpResponse {
                    val result = MockLowLevelHttpResponse()
                    result.contentType = Json.MEDIA_TYPE
                    result.setContent(x.body)
                    x.headers.forEach {
                        result.addHeader(it.key, it.value.toString())
                    }
                    return result
                }
            }
        }
    }.createRequestFactory().buildGetRequest(url).execute())
}
