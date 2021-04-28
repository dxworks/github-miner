package org.dxworks.githubminer.dto.response.repository.actions.run

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GithubRun : GenericJson() {
    @Key("id")
    var id: Int = -1

    @Key("name")
    var name: String? = null

    @Key("event")
    var event: String? = null

    @Key("conclusion")
    var conclusion: String? = null

    val duration: Long by lazy {
        Duration.between(LocalDateTime.parse(startedAt as CharSequence?, DateTimeFormatter.ISO_OFFSET_DATE_TIME),
            LocalDateTime.parse(completedAt as CharSequence?, DateTimeFormatter.ISO_OFFSET_DATE_TIME)).getSeconds();
    }

    @Key("created_at")
    private var startedAt: String? = null
    @Key("updated_at")
    private var completedAt: String? = null

    @Key("head_sha")
    var commitId: String? = null

    @Key("head_branch")
    var branch: String? = null

    @Key("url")
    var url: String? = null

    var jobs: List<GithubJob> = emptyList()

    override fun toString(): String = " $id: duration : $duration \n"
}
