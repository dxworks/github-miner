package org.dxworks.githubminer.service.search

import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.dto.response.repository.pullrequests.PullRequest
import org.dxworks.githubminer.http.factory.GithubHttpClientFactory
import org.dxworks.githubminer.service.repository.pullrequests.GithubPullRequestsService
import org.dxworks.githubminer.utils.githubDateFormatter
import java.time.LocalDate

class GithubPullRequestsSearchService(
    val owner: String,
    val repo: String,
    githubBasePath: String = GITHUB_API_PATH,
    githubTokens: List<String> = listOf(ANONYMOUS),
    clientFactory: GithubHttpClientFactory? = null
) : GithubSearchService(githubBasePath, githubTokens, clientFactory, "issues") {

    private val githubPullRequestsService =
        GithubPullRequestsService(owner, repo, githubBasePath, githubTokens, clientFactory)

    fun getPullRequestsModifiedSince(date: LocalDate): List<PullRequest> {
        return search(
            "user:$owner repo:$repo is:pr updated:>=${date.format(githubDateFormatter)}"
        ).items.mapNotNull { pr -> githubPullRequestsService.getPullRequest(pr["number"] as Number) }
    }
}
