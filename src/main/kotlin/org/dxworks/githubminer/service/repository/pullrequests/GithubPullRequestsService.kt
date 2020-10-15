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
import org.dxworks.githubminer.service.repository.GithubRepositoryService
import org.dxworks.utils.java.rest.client.providers.BasicAuthenticationProvider
import org.slf4j.LoggerFactory
import java.util.stream.Collectors

class GithubPullRequestsService(
        owner: String,
        repo: String,
        githubBasePath: String = GITHUB_API_PATH,
        githubTokens: List<String> = listOf(ANONYMOUS)
) : GithubRepositoryService(owner, repo, githubBasePath, githubTokens) {

    fun createPullRequest(body: CreatePullRequestBody?): PullRequest {
        val apiPath = getApiPath("pulls")
        val httpResponse = httpClient.post(GenericUrl(apiPath), body)
        return httpResponse.parseAs(PullRequest::class.java)
    }

    val allPullRequests: List<PullRequest?>
        get() {
            val pullRequestUrl = PullRequestUrl(getApiPath("pulls"), "all")
            return paginationUtils.getAllElements<PullRequest>(pullRequestUrl, PULL_REQUESTS_LIST_TYPE).stream()
                    .map { pullRequest: PullRequest -> getPullRequest(pullRequest.number!!) }
                    .collect(Collectors.toList())
        }

    fun getPullRequest(pullRequestNumber: Long): PullRequest {
        val apiPath = getApiPath(ImmutableMap.of("pull_number", pullRequestNumber.toString()), "pulls", ":pull_number")
        log.info("Retrieving $apiPath")
        val httpResponse = httpClient.get(GenericUrl(apiPath))
        return httpResponse.parseAs(PullRequest::class.java)
    }

    fun getPullRequestCommits(pullRequest: PullRequest): List<RepoCommit> {
        return getPullRequestCommits(pullRequest.number!!)
    }

    fun getPullRequestCommits(pullRequestNumber: Long): List<RepoCommit> {
        val apiPath = getApiPath(ImmutableMap.of("pull_number", pullRequestNumber.toString()), "pulls", ":pull_number", "commits")
        val pullRequestCommitsUrl = GenericUrl(apiPath)
        return paginationUtils.getAllElements(pullRequestCommitsUrl, PULL_REQUESTS_COMMITS_LIST_TYPE)
    }

    fun getPullRequestReviews(pullRequest: PullRequest): List<PullRequestReview> {
        return getPullRequestReviews(pullRequest.number!!)
    }

    fun getPullRequestReviews(pullRequestNumber: Long): List<PullRequestReview> {
        val apiPath = getApiPath(ImmutableMap.of("pull_number", pullRequestNumber.toString()), "pulls", ":pull_number", "reviews")
        val pullRequestReviewsUrl = GenericUrl(apiPath)
        return paginationUtils.getAllElements(pullRequestReviewsUrl, PULL_REQUESTS_REVIEW_LIST_TYPE)
    }

    companion object {
        private val PULL_REQUESTS_LIST_TYPE = object : TypeToken<List<PullRequest?>?>() {}.type
        private val PULL_REQUESTS_COMMITS_LIST_TYPE = object : TypeToken<List<RepoCommit?>?>() {}.type
        private val PULL_REQUESTS_REVIEW_LIST_TYPE = object : TypeToken<List<PullRequestReview?>?>() {}.type
        private val log = LoggerFactory.getLogger(GithubPullRequestsService::class.java)
    }
}
