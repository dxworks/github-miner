package org.dxworks.githubminer.dto.response.organizations.team

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key

class GithubTeam : GenericJson() {
    @Key
    var id: Long? = null

    @Key
    var name: String? = null

    @Key
    var slug: String? = null
}
