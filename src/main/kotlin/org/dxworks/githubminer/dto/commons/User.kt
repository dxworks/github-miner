package org.dxworks.githubminer.dto.commons

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key

class User(
        @Key
        var id: Long? = null,

        @Key
        var login: String? = null,

        @Key
        var url: String? = null,

        @Key
        var email: String? = null,

        @Key
        var name: String? = null,

        @Key
        var avatarUrl: String? = null
) : GenericJson()
