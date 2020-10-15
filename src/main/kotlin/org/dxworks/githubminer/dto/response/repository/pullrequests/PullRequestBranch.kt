package org.dxworks.githubminer.dto.response.repository.pullrequests

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key
import org.dxworks.githubminer.dto.commons.User
import org.dxworks.githubminer.dto.response.repository.Repository

class PullRequestBranch : GenericJson() {
    @Key
    var label: String? = null

    @Key
    var ref: String? = null

    @Key
    var sha: String? = null

    @Key
    var user: User? = null

    @Key
    var repo: Repository? = null
}
