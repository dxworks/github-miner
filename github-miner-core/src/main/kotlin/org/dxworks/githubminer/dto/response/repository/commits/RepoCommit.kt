package org.dxworks.githubminer.dto.response.repository.commits

import com.google.api.client.json.GenericJson
import com.google.api.client.util.Key
import org.dxworks.githubminer.dto.commons.User

class RepoCommit : GenericJson() {
    @Key
    var sha: String? = null

    @Key
    var commit: Commit? = null

    @Key
    var author: User? = null

    @Key
    var committer: User? = null
}
