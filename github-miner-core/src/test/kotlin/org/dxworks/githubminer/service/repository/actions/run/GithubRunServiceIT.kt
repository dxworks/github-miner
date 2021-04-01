package org.dxworks.githubminer.service.repository.actions.run

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.dxworks.githubminer.utils.TestUtils.githubCredentials


internal class GithubRunServiceIT {

    val githubRunService = GithubRunService("cadeca", "weasylearn-be", githubTokens = githubCredentials)

    @Test
    fun getAllRuns() {
        val allRuns = githubRunService.getAllRuns()
        assertNotNull(allRuns)
    }

//    @Test
//    fun getJobs() {
//        githubRunService.getJobs()
//    }
}
