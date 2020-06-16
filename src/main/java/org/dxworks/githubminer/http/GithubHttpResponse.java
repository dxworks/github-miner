package org.dxworks.githubminer.http;

import lombok.Getter;
import org.dxworks.githubminer.pagination.PageLinks;
import org.dxworks.utils.java.rest.client.response.HttpResponse;

@Getter
public class GithubHttpResponse extends HttpResponse {

    private PageLinks pageLinks;

    public GithubHttpResponse(HttpResponse response) {
        super(response.getResponse());
        this.pageLinks = new PageLinks(this);
    }
}
