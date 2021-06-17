package org.dxworks.githubminer.dto.response.repository

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key

class ReleaseAsset : GenericJson() {
    @Key
    var url: String? = null

    @Key
    var name: String? = null

    @Key("browser_download_url")
    var browserDownloadUrl: String? = null
}
