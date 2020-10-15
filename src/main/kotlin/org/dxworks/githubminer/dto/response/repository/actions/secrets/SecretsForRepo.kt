package org.dxworks.githubminer.dto.response.repository.actions.secrets

import com.google.api.client.util.Key

class SecretsForRepo {
    @Key
    var total_count = 0

    @Key
    var secrets: List<Secret>? = null
}
