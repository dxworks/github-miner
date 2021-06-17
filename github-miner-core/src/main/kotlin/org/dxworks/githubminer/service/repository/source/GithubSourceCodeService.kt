package org.dxworks.githubminer.service.repository.source

import com.google.api.client.http.GenericUrl
import org.dxworks.githubminer.constants.GITHUB_PATH
import org.dxworks.utils.java.rest.client.HttpClient
import org.dxworks.utils.java.rest.client.providers.BearerTokenAuthenticationProvider
import java.util.zip.ZipInputStream

class GithubSourceCodeService(
    val owner: String,
    val repo: String,
    val githubBasePath: String = GITHUB_PATH,
    token: String? = null
) {
    private val httpClient = token?.let { HttpClient(BearerTokenAuthenticationProvider(token)) } ?: HttpClient()

    fun downloadSourceCode(tagName: String): ZipInputStream =
        ZipInputStream(httpClient.get(GenericUrl("$githubBasePath/$owner/$repo/archive/refs/tags/${tagName}.zip")).content)
}
