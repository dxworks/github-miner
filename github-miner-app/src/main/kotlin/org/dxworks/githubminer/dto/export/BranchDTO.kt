package org.dxworks.githubminer.dto.export

import com.google.api.client.util.Data
import com.google.api.client.util.Key
import org.dxworks.githubminer.dto.response.repository.branches.Branch

class BranchDTO {
    @Key
    var name: String? = null

    @Key
    var commit: String? = null

    companion object {
        fun fromBranch(branch: Branch?): BranchDTO? {
            return if (branch == null || Data.isNull(branch)) null else BranchDTO().apply {
                name = branch.name
                commit = branch.sha
            }
        }
    }
}
