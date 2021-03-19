package org.dxworks.githubminer.dto.response.repository.actions.run

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key

class GithubJobsResponseDTO : GenericJson() {
    @Key("total_count")
    var totalCount: Number? = 0

    @Key
    var jobs: List<GithubJob> = emptyList()
}
