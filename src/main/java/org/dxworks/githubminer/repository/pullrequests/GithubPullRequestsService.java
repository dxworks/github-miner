package org.dxworks.githubminer.repository.pullrequests;

import com.google.api.client.http.GenericUrl;
import com.google.common.reflect.TypeToken;
import lombok.SneakyThrows;
import org.dxworks.githubminer.dto.request.repository.pullrequests.CreatePullRequestBody;
import org.dxworks.githubminer.dto.response.repository.pullrequests.PullRequest;
import org.dxworks.githubminer.http.GithubHttpResponse;
import org.dxworks.githubminer.repository.GithubRepositoryService;
import org.dxworks.utils.java.rest.client.providers.BasicAuthenticationProvider;
import org.dxworks.utils.java.rest.client.response.HttpResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GithubPullRequestsService extends GithubRepositoryService {
    private static final Type PULL_REQUESTS_LIST_TYPE = new TypeToken<List<PullRequest>>() {
    }.getType();

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

    @SneakyThrows
    public List<PullRequest> getAppPullRequests() {

        String apiPath = getApiPath("pulls");

        GithubHttpResponse httpResponse = (GithubHttpResponse) httpClient.get(new GenericUrl(apiPath));

        List<PullRequest> pullRequests = new ArrayList<>();

        while (httpResponse != null && httpResponse.getPageLinks().getNext() != null) {
            pullRequests.addAll((Collection<? extends PullRequest>) httpResponse.parseAs(PULL_REQUESTS_LIST_TYPE));
            httpResponse = (GithubHttpResponse) httpClient.get(new GenericUrl(httpResponse.getPageLinks().getNext()));
        }

        return pullRequests;
    }

//    public List<PullRequestFile> getFilesForPR(int prNumber) {
//        String apiPath = getApiPath(ImmutableMap.of("pull_number", String.valueOf(prNumber)), "pulls", ":pull_number", "files");
//    }
}
