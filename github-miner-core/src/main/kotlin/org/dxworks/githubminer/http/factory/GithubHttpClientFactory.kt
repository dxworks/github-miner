package org.dxworks.githubminer.http.factory

import org.dxworks.githubminer.http.GithubHttpClient

interface GithubHttpClientFactory {
    fun create(githubTokens: List<String>, githubBasePath: String): GithubHttpClient
}
