package org.dxworks.githubminer.utils

import com.google.api.client.http.HttpRequest
import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.utils.java.rest.client.providers.BearerTokenAuthenticationProvider

class GithubBearerAuthenticationProvider(token: String) : BearerTokenAuthenticationProvider(token) {
    override fun initialize(request: HttpRequest) {
        if (ANONYMOUS != token)
            super.initialize(request)
    }
}
