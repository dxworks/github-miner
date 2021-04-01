package org.dxworks.githubminer.service.oraganizations

import org.dxworks.githubminer.service.users.GithubUsersService
import org.dxworks.githubminer.utils.TestUtils.githubCredentials
import org.junit.jupiter.api.Test
import kotlin.io.path.ExperimentalPathApi

internal class GithubOrganizationInvitationServiceIT {

    private val githubOrganizationInvitationService =
        GithubOrganizationInvitationService("", githubTokens = githubCredentials)

    @Test
    internal fun testInviteUserToOrg() {

    }
}
