package org.dxworks.githubminer.dto.response.repository.actions.run

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key

class GithubStep : GenericJson() {
    @Key
    var name: String? = null

    @Key
    var status: String? = null

    @Key
    var conclusion: String? = null

    @Key("started_at")
    var startedAt: String? = null

    @Key("completed_at")
    var completedAt: String? = null

    @Key
    var number: Number? = null
}
