package org.dxworks.githubminer.factory

import org.dizitart.no2.objects.ObjectRepository
import org.dxworks.githubminer.CachingGithubHttpClient
import org.dxworks.githubminer.GithubResponseCache
import org.dxworks.githubminer.http.factory.GithubHttpClientFactory

class CachingGithubHttpClientFactory(private val repo: ObjectRepository<GithubResponseCache>) :
    GithubHttpClientFactory {
    override fun create(githubTokens: List<String>, githubBasePath: String) =
        CachingGithubHttpClient(repo, githubTokens, githubBasePath)
}
