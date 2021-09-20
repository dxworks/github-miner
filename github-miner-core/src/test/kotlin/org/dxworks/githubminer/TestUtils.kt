package org.dxworks.githubminer

import org.dxworks.githubminer.constants.ANONYMOUS
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

object TestUtils {
    private var properties: Properties? = null
    fun getProperty(key: String?): String {
        if (properties == null) loadProperties()
        return properties!!.getProperty(key)
    }

    private fun loadProperties() {
        properties = Properties()
        Paths.get("config", "test.properties").toFile().takeIf { it.exists() }
            ?.let { properties!!.load(FileInputStream(it)) }
    }

    init {
        loadProperties()
    }

    val githubCredentials: List<String>
        get() = properties?.entries?.filter { (it.key as String).startsWith("test.github.token") }
            ?.map { it.value as String }?.takeIf { it.isNotEmpty() }
            ?: listOf(ANONYMOUS)

}
