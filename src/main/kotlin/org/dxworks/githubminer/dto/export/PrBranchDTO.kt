package org.dxworks.githubminer.dto.export

import com.google.api.client.util.Data
import com.google.api.client.util.Key
import org.dxworks.githubminer.dto.response.repository.pullrequests.PullRequestBranch

class PrBranchDTO {
    @Key
    var commit: String? = null

    @Key
    var label: String? = null

    @Key
    var ref: String? = null

    @Key
    var user: UserDTO? = null

    @Key
    var repo: RepoDTO? = null

    companion object {
        fun fromPullRequestBranch(branch: PullRequestBranch?): PrBranchDTO? {
            return if (branch == null || Data.isNull(branch)) null else PrBranchDTO().apply {
                    commit = branch.sha
                    label = branch.label
                    ref = branch.ref
                    user = UserDTO.fromUser(branch.user)
                    repo = RepoDTO.fromRepository(branch.repo)
            }
        }
    }
}
