package org.dxworks.githubminer.dto.response.repository.commits

import com.google.api.client.util.Key
import org.dxworks.githubminer.dto.commons.Author

class CommitAuthor : Author() {
    @Key
    var date: String? = null
}
