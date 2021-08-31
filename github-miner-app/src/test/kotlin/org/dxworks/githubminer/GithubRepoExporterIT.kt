package org.dxworks.githubminer

import com.google.api.client.http.factory.CachingGithubHttpClientFactory
import org.dizitart.no2.Nitrite
import org.dxworks.githubminer.service.repository.releases.GithubReleasesService
import org.dxworks.utils.java.rest.client.utils.JsonMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.charset.Charset
import java.nio.file.Paths

internal class GithubRepoExporterIT {
    val database: Nitrite = Nitrite.builder()
        .filePath(Paths.get(System.getProperty("java.io.tmpdir")).resolve("github-miner-test.db").toFile())
        .openOrCreate("test", "test")

    private val githubRepoExporter =
        GithubRepoExporter(
            "apache",
            "kafka",
            githubTokens = listOf("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"),
            clientFactory = CachingGithubHttpClientFactory(database.getRepository(GithubResponseCache::class.java))
        )

    @Test
    fun export() {
        try {

            val export = githubRepoExporter.export()
            Assertions.assertNotNull(export)
            val file = Paths.get("../remote-export.json").toFile()
            JsonMapper().writePrettyJSONtoFile(file, export, Charset.defaultCharset())
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            database.close()
        }
    }

    @Test
    internal fun name() {
        val allAssets = GithubReleasesService("indygreg", "python-build-standalone").getReleases().flatMap {
            it.assets.orEmpty()
        }
        println(allAssets)
    }
}
