package org.dxworks.githubminer.dto.response.repository

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key

class Release : GenericJson() {
    @Key
    var name: String? = null

    @Key("tag_name")
    var tagName: String? = null

    @Key
    var assets: List<ReleaseAsset>? = null
}