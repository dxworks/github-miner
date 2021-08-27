package org.dxworks.githubminer.service

import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.constants.SERVER_API_PATH
import org.dxworks.githubminer.http.factory.DefaultGithubHttpClientFactory
import org.dxworks.githubminer.http.factory.GithubHttpClientFactory
import org.dxworks.githubminer.pagination.GithubPaginationUtils
import org.dxworks.utils.java.rest.client.RestClient

open class GithubApiService(
    private val githubBasePath: String = GITHUB_API_PATH,
    private val githubTokens: List<String> = listOf(ANONYMOUS),
    clientFactory: GithubHttpClientFactory? = DefaultGithubHttpClientFactory()
) : RestClient(
    githubBasePath,
    clientFactory!!.create(githubTokens, computeGithubBasePath(githubBasePath))
) {

    protected val paginationUtils: GithubPaginationUtils by lazy {
        GithubPaginationUtils(
            computeGithubBasePath(
                githubBasePath
            ),
            githubTokens, clientFactory
        )
    }

    companion object {
        private fun computeGithubBasePath(githubBasePath: String): String {
            return when {
                githubBasePath == GITHUB_API_PATH -> githubBasePath
                githubBasePath.endsWith(SERVER_API_PATH) -> githubBasePath
                else -> githubBasePath.removeSuffix("/") + SERVER_API_PATH
            }
        }
    }
}
