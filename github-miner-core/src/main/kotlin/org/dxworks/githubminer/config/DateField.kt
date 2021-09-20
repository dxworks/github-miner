package org.dxworks.githubminer.config

import org.dxworks.argumenthor.config.fields.FieldConfig
import java.time.LocalDate

class DateField(name: String, defaultValue: LocalDate? = null) : FieldConfig<LocalDate>(name, defaultValue) {
    override fun parse(value: String?): LocalDate? {
        return value?.let {
            val dateParts = it.split("-")
            when (dateParts.size) {
                1 -> LocalDate.of(dateParts[0].toInt(), 1, 1)
                2 -> LocalDate.of(dateParts[0].toInt(), dateParts[1].toInt(), 1)
                3 -> LocalDate.of(dateParts[0].toInt(), dateParts[1].toInt(), dateParts[2].toInt())
                else -> null
            }
        }
    }
}
