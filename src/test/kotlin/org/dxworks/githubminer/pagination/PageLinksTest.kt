package org.dxworks.githubminer.pagination;

import com.google.api.client.http.HttpHeaders;
import org.dxworks.githubminer.http.GithubHttpResponse;
import org.dxworks.utils.java.rest.client.response.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.dxworks.githubminer.pagination.PageLinks.HEADER_LINK;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PageLinksTest {

    private GithubHttpResponse githubResponse;

    @BeforeEach
    void setUp() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HEADER_LINK, "<https://api.github.com/search/code?q=addClass+user%3Amozilla&page=15>; rel=\"next\",\n" +
                "  <https://api.github.com/search/code?q=addClass+user%3Amozilla&page=34>; rel=\"last\",\n" +
                "  <https://api.github.com/search/code?q=addClass+user%3Amozilla&page=1>; rel=\"first\",\n" +
                "  <https://api.github.com/search/code?q=addClass+user%3Amozilla&page=13>; rel=\"prev\"");
        githubResponse = new GithubHttpResponse(new HttpResponse(null)) {
            @Override
            public HttpHeaders getHeaders() {
                return httpHeaders;
            }
        };
    }

    @Test
    void testConstructorWitAllLinks() {
        PageLinks pageLinks = new PageLinks(githubResponse);

        assertEquals("https://api.github.com/search/code?q=addClass+user%3Amozilla&page=1", pageLinks.getFirst());
        assertEquals("https://api.github.com/search/code?q=addClass+user%3Amozilla&page=34", pageLinks.getLast());
        assertEquals("https://api.github.com/search/code?q=addClass+user%3Amozilla&page=13", pageLinks.getPrev());
        assertEquals("https://api.github.com/search/code?q=addClass+user%3Amozilla&page=15", pageLinks.getNext());
    }
}
