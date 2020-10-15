package org.dxworks.githubminer.dto.response.repository.branches

import com.google.api.client.util.Key

class Branch {
    @Key
    var name: String? = null

    @Key
    var commit: BranchCommit? = null

    @Key("protected")
    var isProtected = false

    val sha
        get() = commit?.sha
}
