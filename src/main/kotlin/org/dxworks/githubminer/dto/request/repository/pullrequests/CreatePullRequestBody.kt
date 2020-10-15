package org.dxworks.githubminer.dto.request.repository.pullrequests

import com.google.api.client.util.Key

class CreatePullRequestBody {
    @Key
    var title: String? = null

    @Key
    var head: String? = null

    @Key
    var base: String? = null

    @Key
    var body: String? = null

    @Key("maintainer_can_modify")
    var maintainerCanModify = false

    @Key
    var draft = false
}
