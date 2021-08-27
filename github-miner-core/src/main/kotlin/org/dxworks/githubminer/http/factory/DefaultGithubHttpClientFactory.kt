package org.dxworks.githubminer.http.factory

import org.dxworks.githubminer.http.GithubHttpClient

class DefaultGithubHttpClientFactory : GithubHttpClientFactory {
    override fun create(githubTokens: List<String>, githubBasePath: String) =
        GithubHttpClient(githubTokens, githubBasePath)
}
