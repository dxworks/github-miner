package org.dxworks.githubminer.dto.response.organizations.inivitations

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key
import org.dxworks.githubminer.dto.request.organizations.invitations.ROLE_MEMBER

class OrganizationInvitation: GenericJson() {
    @Key
    var id: Int = 0
    @Key
    var email: String? = null
    @Key
    var role: String = ROLE_MEMBER
    @Key
    var login: String? = null
}
