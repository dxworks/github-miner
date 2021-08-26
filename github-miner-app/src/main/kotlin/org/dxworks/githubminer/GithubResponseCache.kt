package org.dxworks.githubminer

import com.google.api.client.http.GenericUrl
import com.google.api.client.http.HttpHeaders
import org.dizitart.no2.objects.Id

class GithubResponseCache(
    @Id
    val url: GenericUrl,
    val body: String,
    val headers: HttpHeaders
)
