package org.dxworks.githubminer.utils

import org.dxworks.githubminer.http.GithubHttpResponse

abstract class GithubResponseHeaderExtractor(protected val response: GithubHttpResponse) {
    protected fun getHeaderValue(headerName: String?): String {
        val headers = response.headers[headerName]
        if (headers is String) return headers else if (headers is List<*>) return (headers as List<String>).stream().findFirst()
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
