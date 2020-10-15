package org.dxworks.githubminer.utils

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
        properties!!.load(FileInputStream(Paths.get("config", "test.properties").toFile()))
    }

    val githubCredentials: List<String>
        get() = properties?.entries?.filter { (it.key as String).startsWith("test.github.token") }?.map { it.value as String }
                ?: listOf(ANONYMOUS)

}
