package org.dxworks.githubminer.service.oraganizations

import com.google.api.client.http.GenericUrl
import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.dto.request.organizations.invitations.OrganizationInvitationBody
import org.dxworks.githubminer.dto.response.organizations.inivitations.OrganizationInvitation

class GithubOrganizationInvitationService(
    org: String,
    githubBasePath: String = GITHUB_API_PATH,
    githubTokens: List<String> = listOf(ANONYMOUS)
) : GithubOrganizationService(org, githubBasePath, githubTokens) {


    fun createOrganizationInvitation(invite: OrganizationInvitationBody): OrganizationInvitation =
        httpClient.post(GenericUrl(getApiPath("invitations")), invite)
            .parseAs(OrganizationInvitation::class.java)

}
