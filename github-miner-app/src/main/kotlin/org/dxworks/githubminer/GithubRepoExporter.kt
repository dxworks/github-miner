package org.dxworks.githubminer

import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.dto.export.*
import org.dxworks.githubminer.http.factory.DefaultGithubHttpClientFactory
import org.dxworks.githubminer.http.factory.GithubHttpClientFactory
import org.dxworks.githubminer.service.repository.branches.GithubBranchService
import org.dxworks.githubminer.service.repository.commits.GithubCommitService
import org.dxworks.githubminer.service.repository.pullrequests.GithubPullRequestsService
import org.dxworks.githubminer.service.search.GithubPullRequestsSearchService
import java.time.LocalDate
import kotlin.concurrent.thread

class GithubRepoExporter(
    owner: String,
    repo: String,
    githubBaseApiPath: String = GITHUB_API_PATH,
    githubTokens: List<String> = listOf(ANONYMOUS),
    clientFactory: GithubHttpClientFactory = DefaultGithubHttpClientFactory(),
    private val since: LocalDate? = null,
) {
    private val pullRequestsSearchService: GithubPullRequestsSearchService =
        GithubPullRequestsSearchService(owner, repo, githubBaseApiPath, githubTokens, clientFactory)
    private val pullRequestsService: GithubPullRequestsService =
        GithubPullRequestsService(owner, repo, githubBaseApiPath, githubTokens, clientFactory)
    private val commitService: GithubCommitService =
        GithubCommitService(owner, repo, githubBaseApiPath, githubTokens, clientFactory)
    private val branchService: GithubBranchService =
        GithubBranchService(owner, repo, githubBaseApiPath, githubTokens, clientFactory)

    fun export(): GithubProjectDTO {
        return GithubProjectDTO().also {
            it.branches = branches
            it.commitInfos = commits
            it.pullRequests = pullRequests
        }
    }

    private val pullRequests: List<PullRequestDTO>
        get() = (since?.let { pullRequestsSearchService.getPullRequestsModifiedSince(it) }
            ?: pullRequestsService.allPullRequests)
            .mapNotNull(PullRequestDTO.Companion::fromPullRequest)
            .onEach { addAdditionalPrFields(it) }
            .toList()

    private fun addAdditionalPrFields(pullRequestDTO: PullRequestDTO) {
        val commitsThread = thread { addPrCommits(pullRequestDTO) }
        val commentsThread = thread { addPrComments(pullRequestDTO) }
        val reviewsThread = thread { addPrReviews(pullRequestDTO) }
        commitsThread.join()
        commentsThread.join()
        reviewsThread.join()
    }

    private fun addPrReviews(pullRequestDTO: PullRequestDTO) {
        pullRequestDTO.reviews = pullRequestsService.getPullRequestReviews(pullRequestDTO.number!!)
            .mapNotNull(PullRequestReviewDTO.Companion::fromPullRequestReview)
    }

    private fun addPrComments(pullRequestDTO: PullRequestDTO) {
        pullRequestDTO.comments = emptyList()
    }

    private fun addPrCommits(pullRequestDTO: PullRequestDTO) {
        pullRequestDTO.commits = pullRequestsService.getPullRequestCommits(pullRequestDTO.number!!)
            .mapNotNull { it.sha }
    }

    private val commits: List<CommitInfoDTO>
        get() = commitService.allCommits
            .mapNotNull(CommitInfoDTO.Companion::fromCommit)

    private val branches: List<BranchDTO>
        get() = branchService.allBranches
            .mapNotNull(BranchDTO.Companion::fromBranch)

}
