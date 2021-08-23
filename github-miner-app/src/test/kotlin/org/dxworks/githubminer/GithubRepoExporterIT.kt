package org.dxworks.githubminer

import org.dxworks.githubminer.service.repository.releases.GithubReleasesService
import org.dxworks.utils.java.rest.client.utils.JsonMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.charset.Charset
import java.nio.file.Paths

internal class GithubRepoExporterIT {
    private val githubRepoExporter = GithubRepoExporter("andrei2699", "Music-Events-Application", )

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
