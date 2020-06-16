package org.dxworks.githubminer.repository.pullrequests;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import lombok.SneakyThrows;
import org.dxworks.githubminer.dto.request.repository.pullrequests.CreatePullRequestBody;
import org.dxworks.githubminer.dto.response.repository.pullrequests.PullRequest;
import org.dxworks.githubminer.repository.GithubRepositoryService;
import org.dxworks.utils.java.rest.client.providers.BasicAuthenticationProvider;

public class GithubPullRequestsService extends GithubRepositoryService {

    public GithubPullRequestsService(String owner, String repo) {
        super(owner, repo);
    }

    public GithubPullRequestsService(String owner, String repo, BasicAuthenticationProvider authenticationProvider) {
        super(owner, repo, authenticationProvider);
    }

    @SneakyThrows
    public PullRequest createPullRequest(CreatePullRequestBody body) {
        String apiPath = getApiPath("pulls");

        HttpResponse httpResponse = httpClient.post(new GenericUrl(apiPath), body);

        return httpResponse.parseAs(PullRequest.class);
    }
}
