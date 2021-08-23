package org.dxworks.githubminer.http

import org.dxworks.githubminer.dto.rate.RateLimit
import org.dxworks.githubminer.pagination.PageLinks
import org.dxworks.utils.java.rest.client.response.HttpResponse
import java.io.InputStream

open class GithubHttpResponse(private val wrappedResponse: HttpResponse) : HttpResponse(wrappedResponse.response) {
    open val pageLinks: PageLinks = PageLinks(this)
    open val rateLimit: RateLimit = RateLimit(this)

    public override fun readContentString() = wrappedResponse.parseAsString()
}
