package org.dxworks.githubminer.dto.commons

import com.google.api.client.util.Key

open class Author(
        @Key
        var name: String? = null,

        @Key
        var email: String? = null
)
