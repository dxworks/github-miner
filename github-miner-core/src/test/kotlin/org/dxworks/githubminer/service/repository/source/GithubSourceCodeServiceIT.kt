package org.dxworks.githubminer.service.repository.source

import org.junit.jupiter.api.Test

internal class GithubSourceCodeServiceIT {
    @Test
    fun downloadSourceCode() {
        val githubSourceCodeService = GithubSourceCodeService("dxworks", "inspector-git")

        val sourceCodeZip = githubSourceCodeService.downloadSourceCode("1.0.0-voyager")
        print("Download successful")
    }
}