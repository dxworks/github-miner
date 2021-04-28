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
        assertEquals(172, allRuns.size)
        //println(allRuns)

    }

    @Test
    fun getSpecificRun() {
        val run = githubRunService.getAllRuns().first()

        assertEquals(569114191, run.id)
        assertEquals("Build", run.name)
        assertEquals("push", run.event)
        assertEquals("success", run.conclusion)
        assertEquals(146, run.duration)
        assertEquals("fc7acca2fe8b5d698a6a228de72ca7fecf880787", run.commitId)
        assertEquals("testExtension", run.branch)
        assertEquals("https://api.github.com/repos/cadeca/weasylearn-be/actions/runs/569114191", run.url)
        assertEquals(1, run.jobs.size)

    }
}
