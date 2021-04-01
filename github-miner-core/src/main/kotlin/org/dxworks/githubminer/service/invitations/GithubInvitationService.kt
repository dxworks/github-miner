package org.dxworks.githubminer.service.invitations

import com.google.api.client.http.GenericUrl
import com.google.common.collect.ImmutableMap
import com.google.common.reflect.TypeToken
import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.dto.response.invitations.Invitation
import org.dxworks.githubminer.service.GithubApiService
import java.math.BigDecimal

class GithubInvitationService(githubBasePath: String = GITHUB_API_PATH,
                              githubTokens: List<String> = listOf(ANONYMOUS))
    : GithubApiService(githubBasePath, githubTokens) {

    fun listInvitationsForUser(): List<Invitation> {
        val apiPath = getApiPath("user", "repository_invitations")
        val httpResponse = httpClient.get(GenericUrl(apiPath))
        val type = object : TypeToken<List<Invitation?>?>() {}.type
        return httpResponse.parseAs(type) as List<Invitation>
    }

    fun acceptInvitation(invitation: Invitation): Boolean {
        return acceptInvitation(invitation.id)
    }

    fun acceptInvitation(invitationId: BigDecimal): Boolean {
        val map = ImmutableMap.of("invitation_id", invitationId.toString())
        val apiPath = getApiPath(map, "user", "repository_invitations", ":invitation_id")
        val httpResponse = httpClient.patch(GenericUrl(apiPath), null)
        return httpResponse.statusCode == 204
    }
}
