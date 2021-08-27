package org.dxworks.githubminer.service.oraganizations

import com.google.common.collect.ImmutableMap
import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.service.GithubApiService

open class GithubOrganizationService(
    protected var org: String,
    githubBasePath: String = GITHUB_API_PATH,
    githubTokens: List<String> = listOf(ANONYMOUS)
) : GithubApiService(githubBasePath, githubTokens) {
    private var map: ImmutableMap<String, String> = ImmutableMap.of("org", org)

    override fun getApiPath(variableValues: Map<String, String>, vararg pathVariables: String): String {
        val newMap: MutableMap<String, String> = HashMap(variableValues)
        newMap.putAll(map)
        return super.getApiPath(newMap, *(arrayOf("orgs", ":org") + pathVariables))
    }

    override fun getApiPath(vararg pathVariables: String): String {
        return super.getApiPath(map, *(arrayOf("orgs", ":org") + pathVariables))
    }
}
