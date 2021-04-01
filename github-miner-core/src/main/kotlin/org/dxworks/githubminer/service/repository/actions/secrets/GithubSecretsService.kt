package org.dxworks.githubminer.service.repository.actions.secrets

import com.google.api.client.http.GenericUrl
import com.google.common.collect.ImmutableMap
import com.goterl.lazycode.lazysodium.LazySodiumJava
import com.goterl.lazycode.lazysodium.SodiumJava
import com.goterl.lazycode.lazysodium.exceptions.SodiumException
import com.goterl.lazycode.lazysodium.utils.Base64MessageEncoder
import com.goterl.lazycode.lazysodium.utils.Key
import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.dto.request.repository.actions.secrets.SecretRequestBody
import org.dxworks.githubminer.dto.response.repository.actions.secrets.Secret
import org.dxworks.githubminer.dto.response.repository.actions.secrets.SecretsForRepo
import org.dxworks.githubminer.dto.response.repository.actions.secrets.SecretsPublicKey
import org.dxworks.githubminer.service.repository.GithubRepositoryService
import org.slf4j.LoggerFactory

class GithubSecretsService(
        owner: String,
        repo: String,
        githubBasePath: String = GITHUB_API_PATH,
        githubTokens: List<String> = listOf(ANONYMOUS)
) : GithubRepositoryService(owner, repo, githubBasePath, githubTokens) {

    fun listAllSecretsForRepo(): List<Secret> {
        val apiPath = getApiPath("actions", "secrets")
        val httpResponse = httpClient.get(GenericUrl(apiPath))
        val secretsForRepo = httpResponse.parseAs(SecretsForRepo::class.java)
        return secretsForRepo.secrets ?: emptyList()
    }

    val publicKey: SecretsPublicKey
        get() {
            val apiPath = getApiPath("actions", "secrets", "public-key")
            val httpResponse = httpClient.get(GenericUrl(apiPath))
            return httpResponse.parseAs(SecretsPublicKey::class.java)
        }

    fun createSecret(name: String, value: String): Boolean {
        val apiPath = getApiPath(ImmutableMap.of("name", name), "actions", "secrets", ":name")
        val body = createNewSecret(value)
        val httpResponse = httpClient.put(GenericUrl(apiPath), body)
        val statusCode = httpResponse.statusCode
        return statusCode == 201 || statusCode == 204
    }

    private fun createNewSecret(value: String): SecretRequestBody? {
        val publicKey = publicKey
        val lazySodiumJava = LazySodiumJava(SodiumJava(), Base64MessageEncoder())
        val encryptedSecret: String
        encryptedSecret = try {
            lazySodiumJava.cryptoBoxSealEasy(value, Key.fromBase64String(publicKey.key))
        } catch (e: SodiumException) {
            GithubSecretsService.log.error("Secret encryption failed!", e)
            return null
        }
        return SecretRequestBody(encryptedSecret, publicKey.key_id)
    }

    companion object {
        private val log = LoggerFactory.getLogger(GithubSecretsService::class.java)
    }
}
