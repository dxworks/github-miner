package org.dxworks.githubminer.dto.response.repository.actions.run

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key

class GithubRunsResponseDTO : GenericJson() {
    @Key("total_count")
    var totalCount: Number? = 0

    @Key("workflow_runs")
    var runs: List<GithubRun> = emptyList()
}
