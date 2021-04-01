package org.dxworks.githubminer.dto.response.repository.refs

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key

class Ref : GenericJson() {
    @Key
    var ref: String? = null

    @Key
    var `object`: RefObject? = null
}
