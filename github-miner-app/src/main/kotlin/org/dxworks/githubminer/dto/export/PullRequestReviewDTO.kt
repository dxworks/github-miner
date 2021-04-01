package org.dxworks.githubminer.dto.export

import com.google.api.client.util.Data
import com.google.api.client.util.Key
import org.dxworks.githubminer.dto.response.repository.pullrequests.PullRequestReview

class PullRequestReviewDTO {
    @Key
    var user: UserDTO? = null

    @Key
    var state: String? = null

    @Key
    var body: String? = null

    @Key
    var date: String? = null

    companion object {
        fun fromPullRequestReview(pullRequestReview: PullRequestReview?): PullRequestReviewDTO? {
            return if (pullRequestReview == null || Data.isNull(pullRequestReview)) null else PullRequestReviewDTO().apply {

                    body = pullRequestReview.body
                    date = pullRequestReview.submittedAt
                    state = pullRequestReview.state
                    user = UserDTO.Companion.fromUser(pullRequestReview.user)
            }
        }
    }
}
