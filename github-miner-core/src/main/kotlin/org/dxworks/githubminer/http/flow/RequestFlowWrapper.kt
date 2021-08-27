package org.dxworks.githubminer.http.flow

import com.google.api.client.http.GenericUrl
import org.dxworks.utils.java.rest.client.response.HttpResponse

interface RequestFlowWrapper {
    fun execute(url: GenericUrl, request: (url: GenericUrl) -> HttpResponse): HttpResponse
}
