package org.dxworks.githubminer.service.repository.releases

import com.google.api.client.http.GenericUrl
import com.google.common.reflect.TypeToken
import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.constants.GITHUB_PATH
import org.dxworks.githubminer.dto.response.repository.Release
import org.dxworks.githubminer.service.repository.GithubRepositoryService
import org.dxworks.githubminer.service.repository.pullrequests.GithubPullRequestsService
import org.slf4j.LoggerFactory
import java.util.zip.ZipInputStream


class GithubReleasesService(
    owner: String,
    repo: String,
    githubBaseApiPath: String = GITHUB_API_PATH,
    val githubBasePath: String = GITHUB_PATH,
    githubTokens: List<String> = listOf(ANONYMOUS)
) : GithubRepositoryService(owner, repo, githubBaseApiPath, githubTokens) {

    fun getReleases(): List<Release> {
        val apiPath = getApiPath("releases")
        log.info("Retrieving $apiPath")
        val httpResponse = httpClient.get(GenericUrl(apiPath))
        return httpResponse.parseAs(RELEASE_LIST_TYPE) as List<Release>
    }

    fun downloadReleaseAsset(tagName: String, assetName: String): ZipInputStream {
        // Cannot use getApiPath because the request is on github base path (not the api one)
        val response =
            httpClient.get(GenericUrl("$githubBasePath/$owner/$repo/releases/download/${tagName}/${assetName}"))
        return ZipInputStream(response.content)
    }

    companion object {
        private val RELEASE_LIST_TYPE = object : TypeToken<List<Release?>?>() {}.type
        private val log = LoggerFactory.getLogger(GithubPullRequestsService::class.java)
    }
}
