package org.dxworks.githubminer.service.repository.commits

import org.dxworks.githubminer.dto.response.repository.commits.RepoCommit
import org.dxworks.githubminer.TestUtils.githubCredentials
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class GithubCommitServiceIT {
    private val githubCommitService = GithubCommitService("apache", "kafka", githubTokens = githubCredentials)

    @Test
    fun testGetAllCommits() {
        val allCommits = githubCommitService.allCommits
        Assertions.assertTrue(allCommits.stream().allMatch { repoCommit: RepoCommit? -> repoCommit is RepoCommit })
        Assertions.assertEquals(7, allCommits.size)
    }
}
