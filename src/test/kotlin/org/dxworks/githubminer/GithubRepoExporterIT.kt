package org.dxworks.githubminer

import org.dxworks.utils.java.rest.client.utils.JsonMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.IOException
import java.nio.charset.Charset
import java.nio.file.Paths

internal class GithubRepoExporterIT {
    private val githubRepoExporter = GithubRepoExporter("andrei2699", "Music-Events-Application")

    @Test
    fun export() {
        val export = githubRepoExporter.export()
        Assertions.assertNotNull(export)
        val file = Paths.get("../remote-export.json").toFile()
        JsonMapper().writePrettyJSONtoFile(file, export, Charset.defaultCharset())
    }
}
