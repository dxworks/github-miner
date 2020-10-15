package org.dxworks.githubminer.dto.response.repository.actions.secrets

import com.google.api.client.util.Key

class SecretsPublicKey {
    @Key
    var key: String? = null

    @Key
    var key_id: String? = null
}
