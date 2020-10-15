package org.dxworks.githubminer.dto.response.repository.commits

import com.google.api.client.util.Key

class Commit {
    @Key
    var author: CommitAuthor? = null

    @Key
    var committer: CommitAuthor? = null

    @Key
    var message: String? = null

    @Key
    var url: String? = null
}
