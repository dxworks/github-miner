package org.dxworks.githubminer.service.repository.releases

import org.dxworks.githubminer.dto.response.repository.ReleaseAsset
import org.junit.jupiter.api.Test
import kotlin.test.fail

internal class GithubReleasesServiceIT {

    @Test
    fun getInspectorGitReleases() {
        val releases = GithubReleasesService("dxworks", "inspector-git")
            .getReleases()
        print("Releases requested successfully")
    }

    @Test
    fun downloadInspectorGitVoyagerReleaseIT() {
        val githubReleasesService = GithubReleasesService("dxworks", "inspector-git")
        val releases = githubReleasesService
            .getReleases()
        val release = releases[0]
        val asset: ReleaseAsset = release.assets?.get(0) ?: fail("Did not find the asset")

        val downloadReleaseAsset = githubReleasesService.downloadReleaseAsset(release.tagName!!, asset.name!!)
        print("Download successful")
    }
}
