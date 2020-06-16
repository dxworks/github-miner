package org.dxworks.githubminer;

import com.google.api.client.http.HttpRequestInitializer;
import org.dxworks.utils.java.rest.client.RestClient;

public class GithubApiService extends RestClient {
    private static final String GITHUB_API_PATH = "https://api.github.com";

    public GithubApiService() {
        super(GITHUB_API_PATH);
    }

    public GithubApiService(HttpRequestInitializer httpRequestInitializer) {
        super(GITHUB_API_PATH, getHttpRequestInitializer(httpRequestInitializer));
    }

    private static HttpRequestInitializer getHttpRequestInitializer(HttpRequestInitializer httpRequestInitializer) {

        return httpRequest -> {
            httpRequest.setReadTimeout(0);
            httpRequestInitializer.initialize(httpRequest);
        };
    }
}
