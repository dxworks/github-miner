package org.dxworks.githubminer.service.repository.source

import org.dxworks.githubminer.constants.GITHUB_PATH
import org.dxworks.utils.java.rest.client.HttpClient
import org.dxworks.utils.java.rest.client.providers.BearerTokenAuthenticationProvider
import java.io.InputStream
import java.net.URL

class GithubSourceCodeService(
    val owner: String,
    val repo: String,
    val githubBasePath: String = GITHUB_PATH,
    token: String? = null
) {
    private val httpClient = token?.let { HttpClient(BearerTokenAuthenticationProvider(token)) } ?: HttpClient()

    fun downloadSourceCode(tagName: String): InputStream =
        URL("$githubBasePath/$owner/$repo/archive/refs/tags/${tagName}.zip").openStream()
}
