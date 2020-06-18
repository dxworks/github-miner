package org.dxworks.githubminer.service.repository.pullrequests;

import com.google.api.client.http.GenericUrl;
import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dxworks.githubminer.dto.request.repository.pullrequests.CreatePullRequestBody;
import org.dxworks.githubminer.dto.response.repository.commits.RepoCommit;
import org.dxworks.githubminer.dto.response.repository.pullrequests.PullRequest;
import org.dxworks.githubminer.dto.response.repository.pullrequests.PullRequestReview;
import org.dxworks.githubminer.service.repository.GithubRepositoryService;
import org.dxworks.utils.java.rest.client.providers.BasicAuthenticationProvider;
import org.dxworks.utils.java.rest.client.response.HttpResponse;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
    public List<PullRequest> getAllPullRequests() {
        PullRequestUrl pullRequestUrl = new PullRequestUrl(getApiPath("pulls"), "all");
        return getPaginationUtils().<PullRequest>getAllElements(pullRequestUrl, PULL_REQUESTS_LIST_TYPE).stream()
                .map(pullRequest -> getPullRequest(pullRequest.getNumber()))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public PullRequest getPullRequest(long pullRequestNumber) {
        String apiPath = getApiPath(ImmutableMap.of("pull_number", String.valueOf(pullRequestNumber)), "pulls", ":pull_number");

        log.info("Retrieving " + apiPath);
        HttpResponse httpResponse = httpClient.get(new GenericUrl(apiPath));

        return httpResponse.parseAs(PullRequest.class);
    }

    @SneakyThrows
    public List<RepoCommit> getPullRequestCommits(PullRequest pullRequest) {
        return getPullRequestCommits(pullRequest.getNumber());
    }

    @SneakyThrows
    public List<RepoCommit> getPullRequestCommits(long pullRequestNumber) {
        String apiPath = getApiPath(ImmutableMap.of("pull_number", String.valueOf(pullRequestNumber)), "pulls", ":pull_number", "commits");
        GenericUrl pullRequestCommitsUrl = new GenericUrl(apiPath);

        return getPaginationUtils().getAllElements(pullRequestCommitsUrl, PULL_REQUESTS_COMMITS_LIST_TYPE);
    }

    @SneakyThrows
    public List<PullRequestReview> getPullRequestReviews(PullRequest pullRequest) {
        return getPullRequestReviews(pullRequest.getNumber());
    }

    @SneakyThrows
    public List<PullRequestReview> getPullRequestReviews(long pullRequestNumber) {
        String apiPath = getApiPath(ImmutableMap.of("pull_number", String.valueOf(pullRequestNumber)), "pulls", ":pull_number", "reviews");
        GenericUrl pullRequestReviewsUrl = new GenericUrl(apiPath);

        return getPaginationUtils().getAllElements(pullRequestReviewsUrl, PULL_REQUESTS_REVIEW_LIST_TYPE);
    }
}
