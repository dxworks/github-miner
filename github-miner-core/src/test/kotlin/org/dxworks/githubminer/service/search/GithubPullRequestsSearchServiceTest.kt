package org.dxworks.githubminer.service.search

import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.http.factory.DefaultGithubHttpClientFactory
import org.dxworks.githubminer.TestUtils
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertNotNull

internal class GithubPullRequestsSearchServiceTest {

    @Test
    fun getPullRequestsAfter() {
        val pullRequestsAfter = service.getPullRequestsModifiedSince(LocalDate.of(2021, 1, 1))
        assertNotNull(pullRequestsAfter)
    }

    companion object {
        private val service = GithubPullRequestsSearchService(
            "apache",
            "kafka",
            GITHUB_API_PATH,
            TestUtils.githubCredentials,
            DefaultGithubHttpClientFactory()
        )
    }
}
