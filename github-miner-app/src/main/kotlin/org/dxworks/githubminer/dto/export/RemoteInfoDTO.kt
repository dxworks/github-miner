package org.dxworks.githubminer.dto.export

import com.google.api.client.util.Key

class RemoteInfoDTO {
    @Key
    var pullRequests: List<PullRequestDTO>? = null

    @Key
    var commitInfos: List<CommitInfoDTO>? = null

    @Key
    var branches: List<BranchDTO>? = null
}
