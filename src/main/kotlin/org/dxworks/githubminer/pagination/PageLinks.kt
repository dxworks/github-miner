package org.dxworks.githubminer.pagination

import org.dxworks.githubminer.http.GithubHttpResponse
import org.dxworks.githubminer.utils.GithubResponseHeaderExtractor
import java.util.*
import java.util.regex.Pattern

class PageLinks(response: GithubHttpResponse) : GithubResponseHeaderExtractor(response) {
    var first: String? = null
        private set
    var last: String? = null
        private set
    var next: String? = null
        private set
    var prev: String? = null
        private set

    companion object {
        protected const val COMMA = ","
        protected const val SEMICOLON = ";"
        const val HEADER_LINK = "Link"
        protected const val HEADER_NEXT = "X-Next"
        protected const val HEADER_LAST = "X-Last"
        protected const val REL = "rel"
        protected const val LAST = "last"
        protected const val NEXT = "next"
        protected const val FIRST = "first"
        protected const val PREV = "prev"
        protected const val URL = "url"
        private const val LINK_REGEX = "<(?<url>[^\\s]*)>;\\s*rel=\\\"(?<rel>next|last|first|prev)\\\""
        private val LINK_PATTERN = Pattern.compile(LINK_REGEX)
    }

    init {
        val linkHeader = getHeaderValue(HEADER_LINK)
        if (linkHeader.isNotBlank()) {
            Arrays.stream(linkHeader.split(COMMA).toTypedArray()).forEach { link: String? ->
                val matcher = LINK_PATTERN.matcher(link)
                if (matcher.find()) {
                    val url = matcher.group(URL)
                    val rel = matcher.group(REL)
                    setValue(rel, url)
                }
            }
        } else {
            next = getHeaderValue(HEADER_NEXT)
            last = getHeaderValue(HEADER_LAST)
        }
    }

    private fun setValue(rel: String, url: String) {
        when (rel) {
            NEXT -> next = url
            PREV -> prev = url
            LAST -> last = url
            FIRST -> first = url
        }
    }
}
