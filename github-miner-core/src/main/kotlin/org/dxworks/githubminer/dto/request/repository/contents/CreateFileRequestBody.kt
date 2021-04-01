package org.dxworks.githubminer.dto.request.repository.contents

import com.google.api.client.util.Key
import org.dxworks.githubminer.dto.commons.Author

class CreateFileRequestBody {
    @Key
    var message: String? = null

    @Key
    var content: String? = null

    @Key
    var branch: String? = null

    @Key
    var author: Author? = null

    @Key
    var committer: Author? = null
}
