package org.dxworks.githubminer.http

import org.dxworks.githubminer.dto.rate.RateLimit
import org.dxworks.githubminer.pagination.PageLinks
import org.dxworks.utils.java.rest.client.response.HttpResponse
import java.io.InputStream

class GithubHttpResponse(private val wrappedResponse: HttpResponse) : HttpResponse(wrappedResponse.response) {
    val pageLinks: PageLinks = PageLinks(this)
    val rateLimit: RateLimit = RateLimit(this)

    override fun readContentString() = wrappedResponse.parseAsString()
}
