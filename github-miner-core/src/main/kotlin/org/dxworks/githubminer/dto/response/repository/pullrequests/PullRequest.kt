package org.dxworks.githubminer.dto.response.repository.pullrequests

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key
import org.dxworks.githubminer.dto.commons.User

class PullRequest : GenericJson() {
    @Key
    var id: Number? = null

    @Key
    var state: String? = null

    @Key
    var title: String? = null

    @Key
    var body: String? = null

    @Key
    var user: User? = null

    @Key
    var assignee: User? = null

    @Key("merged_by")
    var mergedBy: User? = null

    @Key("created_at")
    var createdAt: String? = null

    @Key("merged_at")
    var mergedAt: String? = null

    @Key("updated_at")
    var updatedAt: String? = null

    @Key("closed_at")
    var closedAt: String? = null

    @Key
    var head: PullRequestBranch? = null

    @Key
    var base: PullRequestBranch? = null

    @Key
    var number: Number? = null
}
