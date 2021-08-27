package org.dxworks.githubminer.service.repository

import com.google.common.collect.ImmutableMap
import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.http.factory.GithubHttpClientFactory
import org.dxworks.githubminer.service.GithubApiService

open class GithubRepositoryService(
    protected var owner: String,
    protected var repo: String,
    githubBasePath: String = GITHUB_API_PATH,
    githubTokens: List<String> = listOf(ANONYMOUS),
    clientFactory: GithubHttpClientFactory? = null
) : GithubApiService(githubBasePath, githubTokens, clientFactory) {

    private var map: ImmutableMap<String, String> = ImmutableMap.of("repo", repo, "owner", owner)

    override fun getApiPath(variableValues: Map<String, String>, vararg pathVariables: String): String {
        val newMap: MutableMap<String, String> = HashMap(variableValues)
        newMap.putAll(map)
        return super.getApiPath(newMap, *(arrayOf("repos", ":owner", ":repo") + pathVariables))
    }

    override fun getApiPath(vararg pathVariables: String): String {
        return super.getApiPath(map, *(arrayOf("repos", ":owner", ":repo") + pathVariables))
    }
}
