package org.dxworks.githubminer.dto.response.invitations

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key
import java.math.BigDecimal

class Invitation : GenericJson() {
    @Key
    lateinit var id: BigDecimal

    @Key
    var repository: GenericJson? = null

    @Key
    var permissions: String? = null

    val repoUrl: String?
        get() = repository?.get("html_url") as String?
}
