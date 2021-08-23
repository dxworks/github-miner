package org.dxworks.githubminer

import org.dizitart.no2.objects.ObjectRepository
import org.dxworks.utils.java.rest.client.response.HttpResponse

class CachingProcessor(private val repo: ObjectRepository<GithubResponseCache>) : (HttpResponse) -> HttpResponse {
    override fun invoke(response: HttpResponse): HttpResponse {
        repo.insert(response.toGithubResponseCache())
        return response
    }
}

fun HttpResponse.toGithubResponseCache(): GithubResponseCache {
    return GithubResponseCache(
        request.url.build(),
        this.parseAsString(),
        this.headers,
        this.headers.eTag,
        this.headers.lastModified
    )
}
