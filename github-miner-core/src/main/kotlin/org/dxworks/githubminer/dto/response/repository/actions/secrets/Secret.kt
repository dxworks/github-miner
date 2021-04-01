package org.dxworks.githubminer.dto.response.repository.actions.secrets

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key

class Secret : GenericJson() {
    @Key
    var name: String? = null
}
