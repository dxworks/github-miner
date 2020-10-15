package org.dxworks.githubminer.dto.rate

import org.dxworks.githubminer.http.GithubHttpResponse
import org.dxworks.githubminer.utils.GithubResponseHeaderExtractor
import java.time.LocalDateTime
import java.time.ZoneOffset

class RateLimit(githubHttpResponse: GithubHttpResponse) : GithubResponseHeaderExtractor(githubHttpResponse) {
    val requestLimit: Int
    val remainingRequests: Int
    var resetTimestamp: LocalDateTime = LocalDateTime.MIN

    companion object {
        protected const val RATE_LIMIT = "X-RateLimit-Limit"
        protected const val REMAINING_REQUESTS = "X-RateLimit-Remaining"
        protected const val RESET_TIMESTAMP = "X-RateLimit-Reset"
    }

    init {
        requestLimit = getNumberHeaderValue(RATE_LIMIT, 5000).toInt()
        remainingRequests = getNumberHeaderValue(REMAINING_REQUESTS, 5000).toInt()
        val resetUtcSeconds: Long = getNumberHeaderValue(RESET_TIMESTAMP, 0).toLong()
        if (resetUtcSeconds != 0L) resetTimestamp = LocalDateTime.ofEpochSecond(resetUtcSeconds, 0, ZoneOffset.UTC)
    }
}
