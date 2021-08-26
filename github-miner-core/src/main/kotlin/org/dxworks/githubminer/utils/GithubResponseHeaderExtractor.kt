package org.dxworks.githubminer.utils

import com.google.api.client.http.HttpHeaders
import org.dxworks.githubminer.http.GithubHttpResponse

abstract class GithubResponseHeaderExtractor(protected val response: GithubHttpResponse) {
    protected fun getHeaderValue(headerName: String?) = getHeaderValue(headerName, response.headers)

    protected fun getHeaderValue(headerName: String?, httpHeaders: HttpHeaders): String {
        val headers = httpHeaders[headerName]
        if (headers is String) return headers else if (headers is List<*>) return (headers as List<String>).stream()
            .findFirst()
            .orElse("")
        return ""
    }

    protected fun getNumberHeaderValue(headerName: String?, defaultValue: Number): Number {
        return try {
            getHeaderValue(headerName).toDouble()
        } catch (e: NumberFormatException) {
            defaultValue
        }
    }

}
