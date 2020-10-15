package org.dxworks.githubminer.dto.request.repository.actions.secrets

import com.google.api.client.util.Key

class SecretRequestBody(
        @Key
        var encrypted_value: String? = null,

        @Key
        var key_id: String? = null
)

