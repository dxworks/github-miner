package org.dxworks.githubminer.dto.rate

import com.google.api.client.http.HttpResponseException
import org.dxworks.githubminer.http.GithubHttpResponse
import org.dxworks.githubminer.utils.GithubResponseHeaderExtractor
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

class RateLimit(githubHttpResponse: GithubHttpResponse) : GithubResponseHeaderExtractor(githubHttpResponse) {
    val requestLimit: Int = getNumberHeaderValue(RATE_LIMIT, 5000).toInt()
    val remainingRequests: Int = getNumberHeaderValue(REMAINING_REQUESTS, 5000).toInt()
    var resetTimestamp: ZonedDateTime = ZonedDateTime.now()

    companion object {
        private const val RATE_LIMIT = "X-RateLimit-Limit"
        private const val REMAINING_REQUESTS = "X-RateLimit-Remaining"
        private const val RESET_TIMESTAMP = "X-RateLimit-Reset"
        private const val RETRY_AFTER = "Retry-After"
    }

    init {
        val resetUtcSeconds = getNumberHeaderValue(RESET_TIMESTAMP, 0)
        if (resetUtcSeconds != 0) resetTimestamp =
            LocalDateTime.ofEpochSecond(resetUtcSeconds.toLong(), 0, ZoneOffset.UTC).atZone(ZoneId.systemDefault())
    }

    fun resetTimestampFromAbuse(e: HttpResponseException) {
        resetTimestamp = ZonedDateTime.now().plusSeconds(
            try {
                getHeaderValue(RETRY_AFTER, e.headers).toLong()
            } catch (e: Exception) {
                0
            }
        )
    }
}
