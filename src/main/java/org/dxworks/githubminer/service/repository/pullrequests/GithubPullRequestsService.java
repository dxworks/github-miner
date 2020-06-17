package org.dxworks.githubminer.service.repository.pullrequests;

import com.google.api.client.http.GenericUrl;
import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;
import lombok.SneakyThrows;
import org.dxworks.githubminer.dto.request.repository.pullrequests.CreatePullRequestBody;
import org.dxworks.githubminer.dto.response.repository.commits.RepoCommit;
import org.dxworks.githubminer.dto.response.repository.pullrequests.PullRequest;
import org.dxworks.githubminer.dto.response.repository.pullrequests.PullRequestReview;
import org.dxworks.githubminer.http.GithubHttpResponse;
import org.dxworks.githubminer.service.repository.GithubRepositoryService;
import org.dxworks.utils.java.rest.client.providers.BasicAuthenticationProvider;
import org.dxworks.utils.java.rest.client.response.HttpResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GithubPullRequestsService extends GithubRepositoryService {
    private static final Type PULL_REQUESTS_LIST_TYPE = new TypeToken<List<PullRequest>>() {
    }.getType();
    private static final Type PULL_REQUESTS_COMMITS_LIST_TYPE = new TypeToken<List<RepoCommit>>() {
    }.getType();
    private static final Type PULL_REQUESTS_REVIEW_LIST_TYPE = new TypeToken<List<PullRequestReview>>() {
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

        GithubHttpResponse httpResponse = (GithubHttpResponse) httpClient.get(new PullRequestUrl(apiPath, "all"));

        List<PullRequest> pullRequests = new ArrayList<>();

        while (httpResponse != null && httpResponse.getPageLinks().getNext() != null) {
            pullRequests.addAll((Collection<? extends PullRequest>) httpResponse.parseAs(PULL_REQUESTS_LIST_TYPE));
            httpResponse = (GithubHttpResponse) httpClient.get(new GenericUrl(httpResponse.getPageLinks().getNext()));
        }

        if (httpResponse != null && httpResponse.getPageLinks().getNext() == null)
            pullRequests.addAll((Collection<? extends PullRequest>) httpResponse.parseAs(PULL_REQUESTS_LIST_TYPE));

        return pullRequests;
    }

    public String getPullRequestTitle(PullRequest pullRequest) {
        return pullRequest.getTitle();
    }

    public String getPullRequestBody(PullRequest pullRequest) {
        return pullRequest.getBody();
    }

    public String getPullRequestAuthor(PullRequest pullRequest) {
        return pullRequest.getUser().getLogin();
    }

    public String getPullRequestCreationTime(PullRequest pullRequest) {
        return pullRequest.getCreated_at();
    }

    public String getPullRequestMergingTime(PullRequest pullRequest) {
        return pullRequest.getMerged_at();
    }

    public String getPullRequestUpdatingTime(PullRequest pullRequest) {
        return pullRequest.getUpdated_at();
    }

    public String getPullRequestClosingTime(PullRequest pullRequest) {
        return pullRequest.getClosed_at();
    }

    public String getPullRequestHeadLabel(PullRequest pullRequest) {
        return pullRequest.getHead().getLabel();
    }

    public String getPullRequestHeadRef(PullRequest pullRequest) {
        return pullRequest.getHead().getRef();
    }

    public String getPullRequestBaseLabel(PullRequest pullRequest) {
        return pullRequest.getBase().getLabel();
    }

    public String getPullRequestBaseRef(PullRequest pullRequest) {
        return pullRequest.getBase().getRef();
    }

    @SneakyThrows
    public List<RepoCommit> getPullRequestCommits(int pullRequestNumber) {
        String apiPath = getApiPath(ImmutableMap.of("pull_number", String.valueOf(pullRequestNumber)), "pulls", ":pull_number", "commits");

        GithubHttpResponse httpResponse = (GithubHttpResponse) httpClient.get(new GenericUrl(apiPath));

        List<RepoCommit> pullRequestCommits = new ArrayList<>();
        pullRequestCommits.addAll((Collection<? extends RepoCommit>) httpResponse.parseAs(PULL_REQUESTS_COMMITS_LIST_TYPE));

        return pullRequestCommits;
    }

    @SneakyThrows
    public List<PullRequestReview> getPullRequestReviews(int pullRequestNumber) {
        String apiPath = getApiPath(ImmutableMap.of("pull_number", String.valueOf(pullRequestNumber)), "pulls", ":pull_number", "reviews");

        GithubHttpResponse httpResponse = (GithubHttpResponse) httpClient.get(new GenericUrl(apiPath));

        List<PullRequestReview> pullRequestReviews = new ArrayList<>();

        pullRequestReviews.addAll((Collection<? extends PullRequestReview>) httpResponse.parseAs(PULL_REQUESTS_REVIEW_LIST_TYPE));

        return pullRequestReviews;
    }
}