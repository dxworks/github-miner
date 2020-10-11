package org.dxworks.githubminer.service;

import com.google.api.client.http.HttpRequestInitializer;
import org.dxworks.githubminer.http.GithubHttpClient;
import org.dxworks.githubminer.pagination.GithubPaginationUtils;
import org.dxworks.utils.java.rest.client.RestClient;

public class GithubApiService extends RestClient {
    private static final String GITHUB_API_PATH = "https://api.github.com";
    protected HttpRequestInitializer httpRequestInitializer;
    protected String githubRootUrl;

    public GithubApiService() {
        super(GITHUB_API_PATH, new GithubHttpClient());
    }

    public GithubApiService(String githubRootUrl) {
        super(githubRootUrl, new GithubHttpClient());
        this.githubRootUrl = githubRootUrl;
    }

    public GithubApiService(HttpRequestInitializer httpRequestInitializer) {
        super(GITHUB_API_PATH, new GithubHttpClient(getHttpRequestInitializer(httpRequestInitializer)));
        this.httpRequestInitializer = httpRequestInitializer;
    }

    public GithubApiService(String githubRootUrl, HttpRequestInitializer httpRequestInitializer) {
        super(githubRootUrl, new GithubHttpClient(getHttpRequestInitializer(httpRequestInitializer)));
        this.httpRequestInitializer = httpRequestInitializer;
        this.githubRootUrl = githubRootUrl;
    }

    private static HttpRequestInitializer getHttpRequestInitializer(HttpRequestInitializer httpRequestInitializer) {

        return httpRequest -> {
            httpRequest.setReadTimeout(0);
            if (httpRequestInitializer != null)
                httpRequestInitializer.initialize(httpRequest);
        };
    }

    protected GithubPaginationUtils getPaginationUtils() {
        if(githubRootUrl != null)
            return new GithubPaginationUtils(githubRootUrl, httpRequestInitializer);
        return new GithubPaginationUtils(httpRequestInitializer);
    }
}
