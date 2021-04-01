package org.dxworks.githubminer.dto.response.repository.actions.run

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key

class GithubRun : GenericJson() {
    @Key
    var id: Number = -1

    @Key
    var status: String? = null

    @Key
    var event: String? = null

    @Key("workflow_id")
    var workflowId: Number = -1

    var jobs: List<GithubJob> = emptyList()
}
