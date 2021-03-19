package org.dxworks.githubminer.service.repository.commits

import com.google.api.client.http.GenericUrl
import com.google.common.reflect.TypeToken
import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.dto.response.repository.commits.RepoCommit
import org.dxworks.githubminer.service.repository.GithubRepositoryService

class GithubCommitService(
        owner: String,
        repo: String,
        githubBasePath: String = GITHUB_API_PATH,
        githubTokens: List<String> = listOf(ANONYMOUS)
) : GithubRepositoryService(owner, repo, githubBasePath, githubTokens) {

    val allCommits: List<RepoCommit?>
        get() {
            val commitsUrl = GenericUrl(getApiPath("commits"))
            return paginationUtils.getAllElements(commitsUrl) { it.parseAs(COMMIT_LIST_TYPE) as List<RepoCommit> }
        }

    companion object {
        private val COMMIT_LIST_TYPE = object : TypeToken<List<RepoCommit?>?>() {}.type
    }
}
