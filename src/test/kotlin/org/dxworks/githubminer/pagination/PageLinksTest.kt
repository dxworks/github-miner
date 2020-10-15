package org.dxworks.githubminer.pagination

import com.google.api.client.http.HttpHeaders
import org.dxworks.githubminer.http.GithubHttpResponse
import org.dxworks.utils.java.rest.client.response.HttpResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

internal class PageLinksTest {
    private var githubResponse: GithubHttpResponse? = null

    @BeforeEach
    fun setUp() {
        val headers = HttpHeaders()
        headers[PageLinks.HEADER_LINK] = """<https://api.github.com/search/code?q=addClass+user%3Amozilla&page=15>; rel="next",
  <https://api.github.com/search/code?q=addClass+user%3Amozilla&page=34>; rel="last",
  <https://api.github.com/search/code?q=addClass+user%3Amozilla&page=1>; rel="first",
  <https://api.github.com/search/code?q=addClass+user%3Amozilla&page=13>; rel="prev""""
        val response = mock(com.google.api.client.http.HttpResponse::class.java)
        `when`(response.headers).thenReturn(headers)
        githubResponse = GithubHttpResponse(HttpResponse(response))
    }

    @Test
    fun testConstructorWitAllLinks() {
        val pageLinks = PageLinks(githubResponse!!)
        Assertions.assertEquals("https://api.github.com/search/code?q=addClass+user%3Amozilla&page=1", pageLinks.first)
        Assertions.assertEquals("https://api.github.com/search/code?q=addClass+user%3Amozilla&page=34", pageLinks.last)
        Assertions.assertEquals("https://api.github.com/search/code?q=addClass+user%3Amozilla&page=13", pageLinks.prev)
        Assertions.assertEquals("https://api.github.com/search/code?q=addClass+user%3Amozilla&page=15", pageLinks.next)
    }
}
