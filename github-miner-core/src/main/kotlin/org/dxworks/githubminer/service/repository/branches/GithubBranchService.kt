package org.dxworks.githubminer.service.repository.branches

import com.google.api.client.http.GenericUrl
import com.google.common.reflect.TypeToken
import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.dto.response.repository.branches.Branch
import org.dxworks.githubminer.service.repository.GithubRepositoryService

class GithubBranchService(
        owner: String,
        repo: String,
        githubBasePath: String = GITHUB_API_PATH,
        githubTokens: List<String> = listOf(ANONYMOUS)
) : GithubRepositoryService(owner, repo, githubBasePath, githubTokens) {

    val allBranches: List<Branch?>
        get() {
            val commitsUrl = GenericUrl(getApiPath("branches"))
            return paginationUtils.getAllElements(commitsUrl) { it.parseAs(BRANCH_LIST_TYPE) as List<Branch> }
        }

    companion object {
        private val BRANCH_LIST_TYPE = object : TypeToken<List<Branch?>?>() {}.type
    }
}
