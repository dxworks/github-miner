package org.dxworks.githubminer.dto.response.repository

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key
import org.dxworks.githubminer.dto.commons.User

class Repository : GenericJson() {
    @Key
    var id: Long? = null

    @Key
    var name: String? = null

    @Key("full_name")
    var fullName: String? = null

    @Key
    var owner: User? = null

    @Key("created_at")
    var createdAt: String? = null

    @Key("updated_at")
    var updatedAt: String? = null

    @Key
    var language: String? = null

    @Key
    var description: String? = null
}
