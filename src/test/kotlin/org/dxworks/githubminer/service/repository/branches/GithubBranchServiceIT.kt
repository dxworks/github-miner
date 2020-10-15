package org.dxworks.githubminer.service.repository.branches

import org.dxworks.githubminer.dto.response.repository.branches.Branch
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class GithubBranchServiceIT {
    private val githubBranchService = GithubBranchService("apache", "kafka")

    @Test
    fun tetGetAllBranches() {
        val allCommits = githubBranchService.allBranches
        Assertions.assertTrue(allCommits.stream().allMatch { repoCommit: Branch? -> repoCommit is Branch })
        Assertions.assertEquals(9, allCommits.size)
    }
}
