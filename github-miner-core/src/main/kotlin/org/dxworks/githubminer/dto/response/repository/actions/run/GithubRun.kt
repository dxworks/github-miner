package org.dxworks.githubminer.dto.response.repository.actions.run

import com.google.api.client.json.GenericJson
import com.google.api.client.util.ArrayMap
import com.google.api.client.util.Key
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GithubRun : GenericJson() {
    @Key("id")
    var id: Number = -1

    @Key("name")
    var parentName: String? = null

    @Key("run_number")
    var run_number: Number? = 0

    val name: String by lazy { "$parentName #$run_number" }

    @Key("event")
    var event: String? = null

    @Key("conclusion")
    var conclusion: String? = null

    val duration: Long by lazy {
        Duration.between(LocalDateTime.parse(startedAt as CharSequence?, DateTimeFormatter.ISO_OFFSET_DATE_TIME),
            LocalDateTime.parse(completedAt as CharSequence?, DateTimeFormatter.ISO_OFFSET_DATE_TIME)).getSeconds();
    }

    @Key("head_commit")
    var head_commit: Map<String, Any>? = null

    @Key("created_at")
    var startedAt: String? = null
    @Key("updated_at")
    var completedAt: String? = null

    @Key("head_sha")
    var commitId: String? = null

    @Key("head_branch")
    var branch: String? = null

    @Key("url")
    var url: String? = null

    var jobs: List<GithubJob> = emptyList()
}
