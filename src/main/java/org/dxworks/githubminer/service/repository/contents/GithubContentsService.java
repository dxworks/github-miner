package org.dxworks.githubminer.service.repository.contents;

import com.google.api.client.http.GenericUrl;
import com.google.common.collect.ImmutableMap;
import org.dxworks.githubminer.dto.request.repository.contents.CreateFileRequestBody;
import org.dxworks.githubminer.service.repository.GithubRepositoryService;
import org.dxworks.utils.java.rest.client.providers.BasicAuthenticationProvider;
import org.dxworks.utils.java.rest.client.response.HttpResponse;

public class GithubContentsService extends GithubRepositoryService {

    public GithubContentsService(String owner, String repo) {
        super(owner, repo);
    }

    public GithubContentsService(String githubRootUrl, String owner, String repo) {
        super(githubRootUrl, owner, repo);
    }

    public GithubContentsService(String owner, String repo, BasicAuthenticationProvider authenticationProvider) {
        super(owner, repo, authenticationProvider);
    }

    public GithubContentsService(String githubRootUrl, String owner, String repo, BasicAuthenticationProvider authenticationProvider) {
        super(githubRootUrl, owner, repo, authenticationProvider);
    }

    public boolean addFileToRepo(String path, CreateFileRequestBody body) {
        String apiPath = getApiPath(ImmutableMap.of("path", path), "contents", ":path");

        HttpResponse httpResponse = httpClient.put(new GenericUrl(apiPath), body);

        int statusCode = httpResponse.getStatusCode();
        return statusCode == 200 || statusCode == 201;
    }

}
