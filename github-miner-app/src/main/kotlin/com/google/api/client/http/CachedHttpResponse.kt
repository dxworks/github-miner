package com.google.api.client.http

import org.dxworks.githubminer.GithubResponseCache
import org.dxworks.githubminer.dto.rate.RateLimit
import org.dxworks.githubminer.http.GithubHttpResponse
import org.dxworks.githubminer.pagination.PageLinks
import org.dxworks.utils.java.rest.client.response.HttpResponse

class CachedHttpResponse(
    val cache: GithubResponseCache,
    override val pageLinks: PageLinks,
    override val rateLimit: RateLimit
) : GithubHttpResponse(HttpResponse(HttpResponse(null, null))) {

    override fun readContentString(): String = cache.body
    fun get_Headers(): HttpHeaders = cache.headers
}
