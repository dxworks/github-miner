package org.dxworks.githubminer.dto.export

import com.google.api.client.util.Data
import com.google.api.client.util.Key
import org.dxworks.githubminer.dto.response.repository.commits.RepoCommit

class CommitInfoDTO {
    @Key
    var id: String? = null

    @Key
    var author: UserDTO? = null

    @Key
    var committer: UserDTO? = null

    companion object {
        fun fromCommit(repoCommit: RepoCommit?): CommitInfoDTO? {
            return if (repoCommit == null || Data.isNull(repoCommit)) null else CommitInfoDTO().apply {
                id = repoCommit.sha
                author = UserDTO.fromUser(repoCommit.author)
                committer = UserDTO.fromUser(repoCommit.committer)
            }
        }
    }
}
