package org.dxworks.githubminer.dto.export

import com.google.api.client.util.Data
import com.google.api.client.util.Key
import org.dxworks.githubminer.dto.response.repository.Repository

class RepoDTO {
    @Key
    var id: Long? = null

    @Key
    var name: String? = null

    @Key
    var fullName: String? = null

    @Key
    var owner: UserDTO? = null

    companion object {
        fun fromRepository(repo: Repository?): RepoDTO? {
            return if (repo == null || Data.isNull(repo)) null else RepoDTO().apply {
                    id = repo.id
                    name = repo.name
                    fullName = repo.fullName
                    owner = UserDTO.fromUser(repo.owner)
            }
        }
    }
}
