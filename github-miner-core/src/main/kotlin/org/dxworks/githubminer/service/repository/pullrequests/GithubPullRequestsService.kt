package org.dxworks.githubminer.service.repository.pullrequests

import com.google.api.client.http.GenericUrl
import com.google.common.collect.ImmutableMap
import com.google.common.reflect.TypeToken
import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.dto.request.repository.pullrequests.CreatePullRequestBody
import org.dxworks.githubminer.dto.response.repository.commits.RepoCommit
import org.dxworks.githubminer.dto.response.repository.pullrequests.PullRequest
import org.dxworks.githubminer.dto.response.repository.pullrequests.PullRequestReview
import org.dxworks.githubminer.http.factory.GithubHttpClientFactory
import org.dxworks.githubminer.http.parseIfOk
import org.dxworks.githubminer.service.repository.GithubRepositoryService
import org.slf4j.LoggerFactory

class GithubPullRequestsService(
    owner: String,
    repo: String,
    githubBasePath: String = GITHUB_API_PATH,
    githubTokens: List<String> = listOf(ANONYMOUS),
    clientFactory: GithubHttpClientFactory? = null
) : GithubRepositoryService(owner, repo, githubBasePath, githubTokens, clientFactory) {

    fun createPullRequest(body: CreatePullRequestBody?): PullRequest? {
        val apiPath = getApiPath("pulls")
        return httpClient.post(GenericUrl(apiPath), body)
            .parseIfOk(PullRequest::class.java)
    }

    val allPullRequests: List<PullRequest>
        get() {
            return performGetPullRequests(PullRequestUrl(getApiPath("pulls"), "all"))
        }

    private fun performGetPullRequests(pullRequestUrl: PullRequestUrl): List<PullRequest> {
        return paginationUtils.getAllElements(pullRequestUrl) {
            (it.parseIfOk(PULL_REQUESTS_LIST_TYPE) ?: emptyList<PullRequest>()) as List<PullRequest>
        }.mapNotNull { pullRequest: PullRequest -> getPullRequest(pullRequest.number!!) }
    }

    fun getPullRequest(pullRequestNumber: Number): PullRequest? {
        val apiPath = getApiPath(ImmutableMap.of("pull_number", pullRequestNumber.toString()), "pulls", ":pull_number")
        return httpClient.get(GenericUrl(apiPath))
            .parseIfOk(PullRequest::class.java)
    }

    fun getPullRequestCommits(pullRequest: PullRequest): List<RepoCommit> {
        return getPullRequestCommits(pullRequest.number!!)
    }

    fun getPullRequestCommits(pullRequestNumber: Number): List<RepoCommit> {
        val apiPath =
            getApiPath(ImmutableMap.of("pull_number", pullRequestNumber.toString()), "pulls", ":pull_number", "commits")
        val pullRequestCommitsUrl = GenericUrl(apiPath)
        return paginationUtils.getAllElements(pullRequestCommitsUrl) {
            (it.parseIfOk(PULL_REQUESTS_COMMITS_LIST_TYPE) ?: emptyList<RepoCommit>()) as List<RepoCommit>
        }
    }

    fun getPullRequestReviews(pullRequest: PullRequest): List<PullRequestReview> {
        return getPullRequestReviews(pullRequest.number!!)
    }

    fun getPullRequestReviews(pullRequestNumber: Number): List<PullRequestReview> {
        val apiPath =
            getApiPath(ImmutableMap.of("pull_number", pullRequestNumber.toString()), "pulls", ":pull_number", "reviews")
        val pullRequestReviewsUrl = GenericUrl(apiPath)
        return paginationUtils.getAllElements(pullRequestReviewsUrl) {
            (it.parseIfOk(PULL_REQUESTS_REVIEW_LIST_TYPE) ?: emptyList<PullRequestReview>()) as List<PullRequestReview>
        }
    }

    companion object {
        private val PULL_REQUESTS_LIST_TYPE = object : TypeToken<List<PullRequest?>?>() {}.type
        private val PULL_REQUESTS_COMMITS_LIST_TYPE = object : TypeToken<List<RepoCommit?>?>() {}.type
        private val PULL_REQUESTS_REVIEW_LIST_TYPE = object : TypeToken<List<PullRequestReview?>?>() {}.type
        private val log = LoggerFactory.getLogger(GithubPullRequestsService::class.java)
    }
}
