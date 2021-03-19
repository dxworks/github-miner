package org.dxworks.githubminer.service.repository.actions.run

import com.google.api.client.http.GenericUrl
import com.google.common.reflect.TypeToken
import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.dto.response.repository.actions.run.GithubJob
import org.dxworks.githubminer.dto.response.repository.actions.run.GithubJobsResponseDTO
import org.dxworks.githubminer.dto.response.repository.actions.run.GithubRun
import org.dxworks.githubminer.dto.response.repository.actions.run.GithubRunsResponseDTO
import org.dxworks.githubminer.service.repository.GithubRepositoryService

class GithubRunService(
        owner: String,
        repo: String,
        githubBasePath: String = GITHUB_API_PATH,
        githubTokens: List<String> = listOf(ANONYMOUS)
) : GithubRepositoryService(owner, repo, githubBasePath, githubTokens) {

    fun getAllRuns(): List<GithubRun> =
            paginationUtils.getAllElements<GithubRun>(GenericUrl(getApiPath("actions", "runs"))) {
                it.parseAs(GithubRunsResponseDTO::class.java).runs
            }.onEach {
                it.jobs = getJobs(it.id)
            }

    fun getJobs(runId: Number): List<GithubJob> {
        return paginationUtils.getAllElements(GenericUrl(getApiPath(mapOf("runId" to runId.toString()), "actions", "runs", ":runId", "jobs"))){
            it.parseAs(GithubJobsResponseDTO::class.java).jobs
        }
    }
}
