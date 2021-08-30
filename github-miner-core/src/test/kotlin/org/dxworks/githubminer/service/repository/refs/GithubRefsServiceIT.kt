package org.dxworks.githubminer.service.repository.refs

import org.dxworks.githubminer.utils.TestUtils.githubCredentials
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class GithubRefsServiceIT {
    private val service = GithubRefsService("MarioRivis", "SimpleRegistrationExample", githubTokens = githubCredentials)

    @Test
    fun getAllBranches() {
        val ref = service.getRef("heads/master")
        Assertions.assertEquals("refs/heads/master", ref?.ref)
        Assertions.assertEquals("commit", ref?.`object`!!.type)
        Assertions.assertNotNull(ref.`object`!!.sha)
    }
}
