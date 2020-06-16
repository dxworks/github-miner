package org.dxworks.githubminer.http;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestInitializer;
import org.dxworks.utils.java.rest.client.HttpClient;
import org.dxworks.utils.java.rest.client.response.HttpResponse;

public class GithubHttpClient extends HttpClient {

    public GithubHttpClient(HttpRequestInitializer httpRequestInitializer) {
        super(httpRequestInitializer);
    }

    public GithubHttpClient() {
        super();
    }

    @Override
    public HttpResponse get(GenericUrl url) {
        return new GithubHttpResponse(super.get(url));
    }

    @Override
    public HttpResponse patch(GenericUrl url, Object body) {
        return super.patch(url, body);
    }

    @Override
    public HttpResponse post(GenericUrl url) {
        return super.post(url);
    }

    @Override
    public HttpResponse post(GenericUrl url, Object body) {
        return super.post(url, body);
    }

    @Override
    public HttpResponse put(GenericUrl url, Object body) {
        return super.put(url, body);
    }
}
