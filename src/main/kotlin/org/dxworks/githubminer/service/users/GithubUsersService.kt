package org.dxworks.githubminer.service.users

import com.google.api.client.http.GenericUrl
import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.dto.commons.User
import org.dxworks.githubminer.service.GithubApiService

class GithubUsersService(
    githubBasePath: String = GITHUB_API_PATH,
    githubTokens: List<String> = listOf(ANONYMOUS)
) : GithubApiService(githubBasePath, githubTokens) {

    fun getUser(username: String): User {
        val userUrl = GenericUrl(getApiPath(mapOf("username" to username), "users", ":username"))

        return httpClient.get(userUrl).parseAs(User::class.java)
    }
}
