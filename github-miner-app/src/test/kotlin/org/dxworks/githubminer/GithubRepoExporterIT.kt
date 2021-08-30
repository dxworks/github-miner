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
            githubTokens = listOf("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", "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"),
            clientFactory = CachingGithubHttpClientFactory(database.getRepository(GithubResponseCache::class.java))
        )

    @Test
    fun export() {
        val export = githubRepoExporter.export()
        Assertions.assertNotNull(export)
        val file = Paths.get("../remote-export.json").toFile()
        JsonMapper().writePrettyJSONtoFile(file, export, Charset.defaultCharset())
    }

    @Test
    internal fun name() {
        val allAssets = GithubReleasesService("indygreg", "python-build-standalone").getReleases().flatMap {
            it.assets.orEmpty()
        }
        println(allAssets)
    }
}
