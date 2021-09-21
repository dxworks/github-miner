package org.dxworks.githubminer.http

import com.google.api.client.http.GenericUrl
import com.google.api.client.http.LowLevelHttpRequest
import com.google.api.client.http.LowLevelHttpResponse
import com.google.api.client.testing.http.MockHttpTransport
import com.google.api.client.testing.http.MockLowLevelHttpRequest
import com.google.api.client.testing.http.MockLowLevelHttpResponse
import org.apache.http.entity.ContentType
import org.dxworks.githubminer.utils.EXCEPTION_STATUS_CODE
import org.dxworks.utils.java.rest.client.response.HttpResponse

class GithubHttpErrorResponse(wrappedResponse: HttpResponse) : GithubHttpResponse(wrappedResponse) {
    constructor(url: GenericUrl?, exception: Exception) : this(
        HttpResponse(object : MockHttpTransport() {
            override fun buildRequest(method: String, url: String): LowLevelHttpRequest {
                return object : MockLowLevelHttpRequest() {
                    override fun execute(): LowLevelHttpResponse {
                        val result = MockLowLevelHttpResponse()
                        result.contentType = ContentType.TEXT_PLAIN.mimeType
                        result.setContent(exception.stackTraceToString())
                        result.statusCode = EXCEPTION_STATUS_CODE
                        return result
                    }
                }
            }
        }.createRequestFactory().buildGetRequest(url).execute())
    )
}
