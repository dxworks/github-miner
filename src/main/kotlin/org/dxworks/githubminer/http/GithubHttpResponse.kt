package org.dxworks.githubminer.http

import org.dxworks.githubminer.dto.rate.RateLimit
import org.dxworks.githubminer.pagination.PageLinks
import org.dxworks.utils.java.rest.client.response.HttpResponse

class GithubHttpResponse(response: HttpResponse) : HttpResponse(response.response) {
    val pageLinks: PageLinks = PageLinks(this)
    val rateLimit: RateLimit = RateLimit(this)
}
