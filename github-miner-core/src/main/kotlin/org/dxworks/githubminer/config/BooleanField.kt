package org.dxworks.githubminer.config

import org.dxworks.argumenthor.config.fields.FieldConfig

class BooleanField(name: String, defaultValue: Boolean? = false) : FieldConfig<Boolean>(name, defaultValue) {
    override fun parse(value: String?): Boolean? {
        return try {
            value?.toBoolean() ?: defaultValue
        } catch (e: Exception) {
            defaultValue
        }
    }
}
