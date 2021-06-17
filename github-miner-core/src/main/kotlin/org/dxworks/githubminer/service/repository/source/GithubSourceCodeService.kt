package org.dxworks.githubminer.service.repository.source

import org.dxworks.githubminer.constants.GITHUB_PATH
import java.net.HttpURLConnection
import java.net.URL
import java.util.zip.ZipInputStream

class GithubSourceCodeService(
    val owner: String,
    val repo: String,
    val githubBasePath: String = GITHUB_PATH,
) {
    fun downloadSourceCode(tagName: String): ZipInputStream {
        val url = URL("$githubBasePath/$owner/$repo/archive/refs/tags/${tagName}.zip")
        val inputStream = (url.openConnection() as HttpURLConnection)
            .apply { requestMethod = "GET" }
            .inputStream
        return ZipInputStream(inputStream)
    }
}
