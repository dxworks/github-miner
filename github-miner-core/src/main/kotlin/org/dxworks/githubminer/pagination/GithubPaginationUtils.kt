package org.dxworks.githubminer.pagination

import com.google.api.client.http.GenericUrl
import com.google.api.client.http.HttpStatusCodes.STATUS_CODE_OK
import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.http.GithubHttpResponse
import org.dxworks.githubminer.http.factory.GithubHttpClientFactory
import org.dxworks.githubminer.service.GithubApiService
import org.slf4j.LoggerFactory

class GithubPaginationUtils(
    githubBasePath: String = GITHUB_API_PATH,
    githubTokens: List<String> = listOf(ANONYMOUS),
    clientFactory: GithubHttpClientFactory?
) : GithubApiService(githubBasePath, githubTokens, clientFactory) {

    fun <T> getAllElements(firstUrl: GenericUrl, responseParser: (GithubHttpResponse) -> List<T>): List<T> {
        var httpResponse = getHttpResponse(firstUrl)
        return if (httpResponse.statusCode != STATUS_CODE_OK)
            emptyList()
        else {
            val elements: MutableList<T> = ArrayList()
            while (hasNextPageLink(httpResponse)) {
                elements.addAll(responseParser(httpResponse))
                val genericUrl = GenericUrl(httpResponse.pageLinks.next)
                httpResponse = getHttpResponse(genericUrl)
            }
            elements.addAll(responseParser(httpResponse))
            elements
        }
    }

    private fun getHttpResponse(genericUrl: GenericUrl): GithubHttpResponse {
        return httpClient.get(genericUrl) as GithubHttpResponse
    }

    private fun hasNextPageLink(httpResponse: GithubHttpResponse?): Boolean {
        return httpResponse != null && httpResponse.pageLinks.next?.isNotBlank() ?: false
    }

    companion object {
        private val log = LoggerFactory.getLogger(GithubPaginationUtils::class.java)
    }
}
