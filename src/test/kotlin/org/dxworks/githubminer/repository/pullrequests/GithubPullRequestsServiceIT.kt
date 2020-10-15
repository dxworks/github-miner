package org.dxworks.githubminer.repository.pullrequests

import org.dxworks.githubminer.dto.response.repository.pullrequests.PullRequest
import org.dxworks.githubminer.service.repository.pullrequests.GithubPullRequestsService
import org.dxworks.githubminer.utils.TestUtils.githubCredentials
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

internal class GithubPullRequestsServiceIT {
    @Test
    fun testGetAllPullRequests() {
        Assertions.assertEquals(144, pullRequests!!.size)
    }

    @Test
    fun pullRequestTitles() {
        Assertions.assertEquals("Added Build.yml workflow to build the application on every push or pull request to Master branch", pullRequests!![pullRequests!!.size - 1]!!.title)
    }

    @Test
    fun pullRequestBodies() {
        Assertions.assertEquals("""
    After you merge this Pull Request, a job will be run by Github every time you create a Pull Requestfor the 'master' branch, of every time commits get pushed to the master branch.
    This way you will know if your application builds everytime you want to merge some changes to the holy 'master' branch.
    If you look just know, you should have a job running, or already finished. Hopefully it didn't fail ;)
    Anytime you want to see the result of these runs, you can find them under the Actions tab just on top of the repo page.
    Now go ahead and merge this PR! Then make sure your build succeeded on the master branch. If it didn't, fix it, ASAP!!!
    """.trimIndent(), pullRequests!![pullRequests!!.size - 1]!!.body)
    }

    @Test
    fun pullRequestAuthors() {
        Assertions.assertEquals("AndyMolin", pullRequests!![pullRequests!!.size - 1]!!.user!!.login)
    }

    @Test
    fun pullRequestCreationTimes() {
        Assertions.assertEquals("2020-04-27T13:25:27Z", pullRequests!![pullRequests!!.size - 1]!!.createdAt)
    }

    @Test
    fun pullRequestMergingTimes() {
        Assertions.assertEquals("2020-04-27T14:50:08Z", pullRequests!![pullRequests!!.size - 1]!!.mergedAt)
    }

    @Test
    fun pullRequestUpdatingTimes() {
        Assertions.assertEquals("2020-04-27T14:50:08Z", pullRequests!![pullRequests!!.size - 1]!!.updatedAt)
    }

    @Test
    fun pullRequestClosingTimes() {
        Assertions.assertEquals("2020-04-27T14:50:08Z", pullRequests!![pullRequests!!.size - 1]!!.closedAt)
    }

    @Test
    fun pullRequestHeadsLabels() {
        Assertions.assertEquals("andrei2699:build-actions", pullRequests!![pullRequests!!.size - 1]!!.head!!.label)
    }

    @Test
    fun pullRequestHeadsRefs() {
        Assertions.assertEquals("build-actions", pullRequests!![pullRequests!!.size - 1]!!.head!!.ref)
    }

    @Test
    fun pullRequestBasesLabels() {
        Assertions.assertEquals("andrei2699:master", pullRequests!![pullRequests!!.size - 1]!!.base!!.label)
    }

    @Test
    fun pullRequestBasesRefs() {
        Assertions.assertEquals("master", pullRequests!![pullRequests!!.size - 1]!!.base!!.ref)
    }

    @Test
    fun pullRequestMergedBy() {
        Assertions.assertEquals("AndyMolin", pullRequests!![pullRequests!!.size - 1]!!.mergedBy!!.login)
    }

    @Test
    fun pullRequestCommitsSha() {
        Assertions.assertEquals("ec44981f2b7ee9e4191bd3d91679cb8a000e1466", service.getPullRequestCommits(pullRequests!![pullRequests!!.size - 1]!!.number!!)[0].sha)
        Assertions.assertEquals("c5eae64bb0e14c1852fd0dca686d3ddcbb47b3fa", service.getPullRequestCommits(pullRequests!![pullRequests!!.size - 1]!!)[1].sha)
    }

    @Test
    fun pullRequestSate() {
        Assertions.assertEquals("APPROVED", service.getPullRequestReviews(123)[0].state)
        Assertions.assertEquals("andrei2699", service.getPullRequestReviews(123)[0].user!!.login)
    }

    companion object {
        private val service = GithubPullRequestsService("andrei2699", "Music-Events-Application", githubTokens = githubCredentials)
        private var pullRequests: List<PullRequest?>? = null

        @BeforeAll
        fun setUpAll() {
            pullRequests = service.allPullRequests
        }
    }
}
