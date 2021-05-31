package org.dxworks.githubminer.dto.response.repository.actions.run

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*

class GithubJob: GenericJson() {
    @Key("id")
    var id: Number = -1

    @Key("name")
    var name: String? = null

    @Key("event")
    var event: String? = null

    @Key("conclusion")
    var conclusion: String? = null

    @Key("started_at")
    var startedAt: String? = null
    @Key("completed_at")
    var completedAt: String? = null

    @Key("url")
    var url: String? = null

    @Key("run_url")
    var run_url: String? = null

    @Key
    var steps: List<GithubStep> = emptyList()
}
