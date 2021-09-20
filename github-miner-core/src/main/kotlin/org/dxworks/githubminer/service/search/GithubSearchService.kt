package org.dxworks.githubminer.service.search

import com.google.api.client.http.GenericUrl
import org.dxworks.githubminer.http.factory.GithubHttpClientFactory
import org.dxworks.githubminer.http.parseIfOk
import org.dxworks.githubminer.service.GithubApiService

open class GithubSearchService(
    githubBasePath: String,
    githubTokens: List<String>,
    clientFactory: GithubHttpClientFactory?,
    protected vararg val apiPath: String
) :
    GithubApiService(githubBasePath, githubTokens, clientFactory) {


    fun search(query: String): SearchResult {
        return httpClient.get(GenericUrl(getApiPath("search", *apiPath)).apply {
            set("q", query)
        }).parseIfOk(SearchResult::class.java) ?: SearchResult()
    }
}
