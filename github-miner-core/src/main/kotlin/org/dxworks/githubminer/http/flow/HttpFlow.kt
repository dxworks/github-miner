package org.dxworks.githubminer.http.flow

import com.google.api.client.http.GenericUrl
import com.google.api.client.http.HttpRequestInitializer
import org.dxworks.utils.java.rest.client.response.HttpResponse

class HttpFlow(
    private val wrap: RequestFlowWrapper? = null
) {
    fun executeRequest(
        url: GenericUrl,
        request: (url: GenericUrl, requestInitializer: HttpRequestInitializer?) -> HttpResponse
    ) =
        wrap?.execute(url) { request.invoke(it, null) } ?: request.invoke(url, null)
}
