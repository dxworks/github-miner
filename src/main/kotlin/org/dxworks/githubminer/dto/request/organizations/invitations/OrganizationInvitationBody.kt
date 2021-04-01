package org.dxworks.githubminer.dto.request.organizations.invitations

import com.google.api.client.util.Key

const val ROLE_ADMIN = "admin"
const val ROLE_MEMBER = "direct_member"
const val ROLE_BILLING_MANAGER = "billing_manager"

class OrganizationInvitationBody(
    @Key
    var invitee_id: Long? = null,
    @Key
    var email: String? = null,
    @Key
    var role: String = ROLE_MEMBER,
    @Key
    var team_ids: List<Long> = emptyList()
)
