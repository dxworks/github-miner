package org.dxworks.githubminer.service

import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.http.GithubHttpClient
import org.dxworks.githubminer.pagination.GithubPaginationUtils
import org.dxworks.utils.java.rest.client.RestClient

open class GithubApiService(
        private val githubBasePath: String = GITHUB_API_PATH,
        private val githubTokens: List<String> = listOf(ANONYMOUS)
) : RestClient(githubBasePath, GithubHttpClient(githubTokens, githubBasePath)) {

    protected val paginationUtils: GithubPaginationUtils by lazy { GithubPaginationUtils(githubBasePath, githubTokens) }
}
