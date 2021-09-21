package org.dxworks.githubminer.runs

import org.dizitart.no2.objects.Id
import org.dxworks.githubminer.utils.githubDateFormatter
import java.time.LocalDate

class RunData(
    @Id
    var repo: String,
    var since: String = LocalDate.now().format(githubDateFormatter)
) {
    constructor() : this("")
}
