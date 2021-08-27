package org.dxworks.githubminer.rateLimit

import com.google.api.client.http.GenericUrl
import org.dxworks.githubminer.service.GithubApiService
import org.junit.jupiter.api.Test

class RateLimitIT{
    @Test
    internal fun testRateLimit() {
        val githubApiService = TestGithubApiService("http://localhost:4004", listOf("aaa", "bbb", "ccc", "abc", "bcd", "asd"))

        for (i in 0 until 100) {
            githubApiService.randomRequest()
        }
    }
}

class TestGithubApiService(githubBasePath: String, githubTokens: List<String>) : GithubApiService(
    githubBasePath,
    githubTokens,
) {
    fun randomRequest() {
        httpClient.get(GenericUrl(getApiPath("Dsadsadsa")))
    }
}
