package org.dxworks.githubminer.dto.export

import com.google.api.client.util.Data
import com.google.api.client.util.Key
import org.dxworks.githubminer.dto.commons.User

class UserDTO {
    @Key
    var id: Long? = null

    @Key
    var login: String? = null

    @Key
    var url: String? = null

    @Key
    var email: String? = null

    @Key
    var name: String? = null

    @Key
    var avatarUrl: String? = null

    companion object {
        fun fromUser(user: User?): UserDTO? {
            return if (user == null || Data.isNull(user)) null else UserDTO().apply {
                    avatarUrl = user.avatarUrl
                    email = user.email
                    login = user.login
                    id = user.id
                    url = user.url
                    name = user.name
                    avatarUrl = user.avatarUrl
            }
        }
    }
}
