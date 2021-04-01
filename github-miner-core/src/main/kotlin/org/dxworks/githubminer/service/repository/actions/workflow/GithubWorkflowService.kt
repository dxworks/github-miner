package org.dxworks.githubminer.service.repository.actions.workflow

import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.service.repository.GithubRepositoryService

class GithubWorkflowService(
        owner: String,
        repo: String,
        githubBasePath: String = GITHUB_API_PATH,
        githubTokens: List<String> = listOf(ANONYMOUS)
) : GithubRepositoryService(owner, repo, githubBasePath, githubTokens) {

    fun getAllWorkflows() {
        val apiPath = getApiPath("actions", "workflows")
    }
}
