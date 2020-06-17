package org.dxworks.githubminer.service;

import com.google.api.client.http.HttpRequestInitializer;
import org.dxworks.githubminer.http.GithubHttpClient;
import org.dxworks.utils.java.rest.client.RestClient;

public class GithubApiService extends RestClient {
    private static final String GITHUB_API_PATH = "https://api.github.com";

    public GithubApiService() {
        super(GITHUB_API_PATH, new GithubHttpClient());
    }

    public GithubApiService(HttpRequestInitializer httpRequestInitializer) {
        super(GITHUB_API_PATH, new GithubHttpClient(getHttpRequestInitializer(httpRequestInitializer)));
    }

    private static HttpRequestInitializer getHttpRequestInitializer(HttpRequestInitializer httpRequestInitializer) {

        return httpRequest -> {
            httpRequest.setReadTimeout(0);
            httpRequestInitializer.initialize(httpRequest);
        };
    }
}
