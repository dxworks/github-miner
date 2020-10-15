package org.dxworks.githubminer.dto.export

import com.google.api.client.util.Data
import com.google.api.client.util.Key
import org.dxworks.githubminer.dto.response.repository.pullrequests.PullRequest

class PullRequestDTO (
    @Key
    var id: Long? = null,

    @Key
    var number: Long,

    @Key
    var title: String? = null,

    @Key
    var body: String? = null,

    @Key
    var head: PrBranchDTO? = null,

    @Key
    var base: PrBranchDTO? = null,

    @Key
    var commits: List<String>? = null,

    @Key
    var createdAt: String? = null,

    @Key
    var updatedAt: String? = null,

    @Key
    var mergedAt: String? = null,

    @Key
    var closedAt: String? = null,

    @Key
    var state: String? = null,

    @Key
    var createdBy: UserDTO? = null,

    @Key
    var assignee: UserDTO? = null,

    @Key
    var mergedBy: UserDTO? = null,

    @Key
    var comments: List<PullRequestCommentDTO>? = null,

    @Key
    var reviews: List<PullRequestReviewDTO>? = null
) {
    companion object {
        fun fromPullRequest(pullRequest: PullRequest?): PullRequestDTO? {
            return if (pullRequest == null || Data.isNull(pullRequest)) null else PullRequestDTO(
                    id = pullRequest.id,
                    number = pullRequest.number,
                    title = pullRequest.title,
                    body = pullRequest.body,
                    head = PrBranchDTO.fromPullRequestBranch(pullRequest.head),
                    base = PrBranchDTO.fromPullRequestBranch(pullRequest.base),
                    createdAt = pullRequest.createdAt,
                    mergedAt = pullRequest.mergedAt,
                    updatedAt = pullRequest.updatedAt,
                    closedAt = pullRequest.closedAt,
                    state = pullRequest.state,
                    createdBy = UserDTO.fromUser(pullRequest.user),
                    assignee = UserDTO.fromUser(pullRequest.assignee),
                    mergedBy = UserDTO.fromUser(pullRequest.mergedBy)
            )
        }
    }
}
