package org.dxworks.githubminer.service.repository.refs

import com.google.api.client.http.GenericUrl
import com.google.common.collect.ImmutableMap
import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.dto.request.repository.refs.RefRequestBody
import org.dxworks.githubminer.dto.response.repository.refs.Ref
import org.dxworks.githubminer.service.repository.GithubRepositoryService
import org.dxworks.utils.java.rest.client.providers.BasicAuthenticationProvider

class GithubRefsService(
        owner: String,
        repo: String,
        githubBasePath: String = GITHUB_API_PATH,
        githubTokens: List<String> = listOf(ANONYMOUS)
) : GithubRepositoryService(owner, repo, githubBasePath, githubTokens) {

    fun getRef(refName: String): Ref {
        val apiPath = getApiPath(ImmutableMap.of("ref", refName), "git", "ref", ":ref")
        val httpResponse = httpClient.get(GenericUrl(apiPath))
        return httpResponse.parseAs(Ref::class.java)
    }

    fun getBranch(branchName: String): Ref {
        return getRef("heads/$branchName")
    }

    fun getTag(tagName: String): Ref {
        return getRef("tags/$tagName")
    }

    fun createRef(refName: String?, sha: String?): Ref {
        val apiPath = getApiPath("git", "refs")
        val body = RefRequestBody(refName, sha)
        val httpResponse = httpClient.post(GenericUrl(apiPath), body)
        return httpResponse.parseAs(Ref::class.java)
    }

    fun createBranch(branchName: String, sha: String?): Ref {
        return createRef("refs/heads/$branchName", sha)
    }

    fun createTag(tagName: String, sha: String?): Ref {
        return createRef("refs/tags/$tagName", sha)
    }
}
