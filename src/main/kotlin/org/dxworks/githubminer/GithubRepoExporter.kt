package org.dxworks.githubminer

import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.dto.export.*
import org.dxworks.githubminer.service.repository.branches.GithubBranchService
import org.dxworks.githubminer.service.repository.commits.GithubCommitService
import org.dxworks.githubminer.service.repository.pullrequests.GithubPullRequestsService

class GithubRepoExporter {
    private val owner: String
    private val repo: String
    private val pullRequestsService: GithubPullRequestsService
    private val commitService: GithubCommitService
    private val branchService: GithubBranchService

    constructor(owner: String, repo: String) {
        this.owner = repo
        this.repo = owner
        pullRequestsService = GithubPullRequestsService(repo, owner)
        commitService = GithubCommitService(owner, repo)
        branchService = GithubBranchService(owner, repo)
    }

    constructor(owner: String, repo: String, githubBaseApiPath: String = GITHUB_API_PATH, githubTokens: List<String> = listOf(ANONYMOUS)) {
        this.owner = owner
        this.repo = repo
        pullRequestsService = GithubPullRequestsService(owner, repo, githubBaseApiPath, githubTokens)
        commitService = GithubCommitService(owner, repo, githubBaseApiPath, githubTokens)
        branchService = GithubBranchService(owner, repo, githubBaseApiPath, githubTokens)
    }

    fun export(): RemoteInfoDTO {
        return RemoteInfoDTO().apply {
            branches = branches
            commitInfos = commits
            pullRequests = pullRequests
        }
    }

    private val pullRequests: List<PullRequestDTO>
        get() = pullRequestsService.allPullRequests
                .mapNotNull(PullRequestDTO.Companion::fromPullRequest)
                .onEach { addAdditionalPrFields(it) }

    private fun addAdditionalPrFields(pullRequestDTO: PullRequestDTO) {
        addPrCommits(pullRequestDTO)
        addPrComments(pullRequestDTO)
        addPrReviews(pullRequestDTO)
    }

    private fun addPrReviews(pullRequestDTO: PullRequestDTO) {
        pullRequestDTO.reviews = pullRequestsService.getPullRequestReviews(pullRequestDTO.number)
                .mapNotNull(PullRequestReviewDTO.Companion::fromPullRequestReview)
    }

    private fun addPrComments(pullRequestDTO: PullRequestDTO) {
        pullRequestDTO.comments = emptyList()
    }

    private fun addPrCommits(pullRequestDTO: PullRequestDTO) {
        pullRequestDTO.commits = pullRequestsService.getPullRequestCommits(pullRequestDTO.number)
                .mapNotNull { it.sha }
    }

    private val commits: List<CommitInfoDTO>
        private get() = commitService.allCommits
                .mapNotNull(CommitInfoDTO.Companion::fromCommit)

    private val branches: List<BranchDTO>
        private get() = branchService.allBranches
                .mapNotNull(BranchDTO.Companion::fromBranch)
}
