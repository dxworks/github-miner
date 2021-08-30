package org.dxworks.githubminer

import com.google.api.client.http.HttpHeaders
import org.dizitart.no2.objects.Id

class GithubResponseCache(
    @Id
    var url: String,
    var body: String,
    var headers: HttpHeaders,
    var statusCode: Int
) {
    constructor() : this("", "", HttpHeaders(), 0)
}
