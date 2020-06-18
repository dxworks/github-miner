package org.dxworks.githubminer.service;

import com.google.api.client.http.HttpRequestInitializer;
import org.dxworks.githubminer.http.GithubHttpClient;
import org.dxworks.githubminer.pagination.GithubPaginationUtils;
import org.dxworks.utils.java.rest.client.RestClient;

public class GithubApiService extends RestClient {
    private static final String GITHUB_API_PATH = "https://api.github.com";
    protected HttpRequestInitializer httpRequestInitializer;

    public GithubApiService() {
        super(GITHUB_API_PATH, new GithubHttpClient());
    }

    public GithubApiService(HttpRequestInitializer httpRequestInitializer) {
        super(GITHUB_API_PATH, new GithubHttpClient(getHttpRequestInitializer(httpRequestInitializer)));
        this.httpRequestInitializer = httpRequestInitializer;
    }

    private static HttpRequestInitializer getHttpRequestInitializer(HttpRequestInitializer httpRequestInitializer) {

        return httpRequest -> {
            httpRequest.setReadTimeout(0);
            if (httpRequestInitializer != null)
                httpRequestInitializer.initialize(httpRequest);
        };
    }

    protected GithubPaginationUtils getPaginationUtils() {
        return new GithubPaginationUtils(httpRequestInitializer);
    }
}
