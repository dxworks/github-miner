package org.dxworks.githubminer.config

import org.dxworks.argumenthor.config.fields.FieldConfig

class RepoListField(name: String, defaultValue: List<Repo>?) : FieldConfig<List<Repo>>(name, defaultValue) {
    override fun parse(value: String?): List<Repo>? {
        return value?.split(",")
                ?.map { it.trim() }
                ?.mapNotNull {
                    val split = it.split("/")
                    if (split.size == 2) Repo(split[0], split[1]) else null
                } ?: emptyList()
    }
}
