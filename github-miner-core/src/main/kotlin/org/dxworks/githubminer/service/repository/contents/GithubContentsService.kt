package org.dxworks.githubminer.service.repository.contents

import com.google.api.client.http.GenericUrl
import com.google.common.collect.ImmutableMap
import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.dto.request.repository.contents.CreateFileRequestBody
import org.dxworks.githubminer.service.repository.GithubRepositoryService

class GithubContentsService(
        owner: String,
        repo: String,
        githubBasePath: String = GITHUB_API_PATH,
        githubTokens: List<String> = listOf(ANONYMOUS)
) : GithubRepositoryService(owner, repo, githubBasePath, githubTokens) {

    fun addFileToRepo(path: String, body: CreateFileRequestBody?): Boolean {
        val apiPath = getApiPath(ImmutableMap.of("path", path), "contents", ":path")
        val httpResponse = httpClient.put(GenericUrl(apiPath), body)
        val statusCode = httpResponse.statusCode
        return statusCode == 200 || statusCode == 201
    }
}
