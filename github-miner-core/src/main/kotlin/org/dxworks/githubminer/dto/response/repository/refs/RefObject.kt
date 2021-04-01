package org.dxworks.githubminer.dto.response.repository.refs

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key

class RefObject : GenericJson() {
    @Key
    var type: String? = null

    @Key
    var sha: String? = null
}
