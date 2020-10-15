package org.dxworks.githubminer.dto.request.repository.refs

import com.google.api.client.util.Key

class RefRequestBody(
        @Key
        val ref: String? = null,
        @Key
        val sha: String? = null
)
