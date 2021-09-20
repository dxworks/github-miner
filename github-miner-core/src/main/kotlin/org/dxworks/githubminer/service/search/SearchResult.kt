package org.dxworks.githubminer.service.search

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key

class SearchResult : GenericJson() {
    @Key
    var items: List<Map<String, Any>> = emptyList()
}
