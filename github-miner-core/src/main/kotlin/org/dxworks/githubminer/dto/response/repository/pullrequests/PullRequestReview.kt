package org.dxworks.githubminer.dto.response.repository.pullrequests

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key
import org.dxworks.githubminer.dto.commons.User

class PullRequestReview : GenericJson() {
    @Key
    var state: String? = null

    @Key
    var user: User? = null

    @Key
    var body: String? = null

    @Key("submitted_at")
    var submittedAt: String? = null
}
