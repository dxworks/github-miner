package org.dxworks.githubminer.service.oraganizations

import org.dxworks.githubminer.TestUtils.githubCredentials
import org.junit.jupiter.api.Test

internal class GithubOrganizationInvitationServiceIT {

    private val githubOrganizationInvitationService =
        GithubOrganizationInvitationService("", githubTokens = githubCredentials)

    @Test
    internal fun testInviteUserToOrg() {

    }
}
