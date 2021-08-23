package org.dxworks.githubminer

import com.google.api.client.http.HttpHeaders
import org.dizitart.no2.objects.Id

class GithubResponseCache(
    @Id
    val url: String,
    val body: String,
    val headers: HttpHeaders,
    val eTag: String,
    val lastModified: String?
)
