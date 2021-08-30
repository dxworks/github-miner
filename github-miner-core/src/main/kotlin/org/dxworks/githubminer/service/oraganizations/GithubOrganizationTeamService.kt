package org.dxworks.githubminer.service.oraganizations

import com.google.api.client.http.GenericUrl
import com.google.common.reflect.TypeToken
import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.dto.response.organizations.team.GithubTeam
import org.dxworks.githubminer.http.parseIfOk

class GithubOrganizationTeamService(
    org: String,
    githubBasePath: String = GITHUB_API_PATH,
    githubTokens: List<String> = listOf(ANONYMOUS)
) : GithubOrganizationService(org, githubBasePath, githubTokens) {

    fun listTeams(): List<GithubTeam> =
        (httpClient.get(GenericUrl(getApiPath("teams"))).parseIfOk(TEAM_LIST_TYPE)
            ?: emptyList<GithubTeam>()) as List<GithubTeam>


    fun getTeamBySlug(teamSlug: String): GithubTeam? {
        val teamUrl = GenericUrl(getApiPath("teams", teamSlug))
        return httpClient.get(teamUrl).parseIfOk(GithubTeam::class.java)
    }

    companion object {
        private val TEAM_LIST_TYPE = object : TypeToken<List<GithubTeam>>() {}.type

    }
}
