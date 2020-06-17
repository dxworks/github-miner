package org.dxworks.githubminer.service.repository.pullrequests;

import com.google.api.client.http.GenericUrl;
import com.google.common.reflect.TypeToken;
import lombok.SneakyThrows;
import org.dxworks.githubminer.dto.request.repository.pullrequests.CreatePullRequestBody;
import org.dxworks.githubminer.dto.response.repository.pullrequests.PullRequest;
import org.dxworks.githubminer.service.repository.GithubRepositoryService;
import org.dxworks.utils.java.rest.client.providers.BasicAuthenticationProvider;
import org.dxworks.utils.java.rest.client.response.HttpResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
        PullRequestUrl pullRequestUrl = new PullRequestUrl(getApiPath("pulls"), "all");
        return getPaginationUtils().getAllElements(pullRequestUrl, PULL_REQUESTS_LIST_TYPE);
    }

    @SneakyThrows
    public List<String> getPullRequestTitles() {
        List<PullRequest> pullRequests = getAppPullRequests();

        List<String> pullRequestTitles = new ArrayList<>();

        for(PullRequest pullRequest : pullRequests)
            pullRequestTitles.add(pullRequest.getTitle());

        return pullRequestTitles;
    }

    @SneakyThrows
    public List<String> getPullRequestBodies() {
        List<PullRequest> pullRequests = getAppPullRequests();

        List<String> pullRequestBodies = new ArrayList<>();

        for(PullRequest pullRequest : pullRequests)
            pullRequestBodies.add(pullRequest.getBody());

        return pullRequestBodies;
    }

    @SneakyThrows
    public List<String> getPullRequestAuthors() {
        List<PullRequest> pullRequests = getAppPullRequests();

        List<String> pullRequestAuthors = new ArrayList<>();

        for(PullRequest pullRequest : pullRequests)
            pullRequestAuthors.add(pullRequest.getUser().getLogin());

        return pullRequestAuthors;
    }

    @SneakyThrows
    public List<String> getPullRequestCreationTimes() {
        List<PullRequest> pullRequests = getAppPullRequests();

        List<String> pullRequestCreationTimes = new ArrayList<>();

        for(PullRequest pullRequest : pullRequests)
            pullRequestCreationTimes.add(pullRequest.getCreated_at());

        return pullRequestCreationTimes;
    }

    @SneakyThrows
    public List<String> getPullRequestMergingTimes() {
        List<PullRequest> pullRequests = getAppPullRequests();

        List<String> pullRequestMergingTimes = new ArrayList<>();

        for(PullRequest pullRequest : pullRequests)
            pullRequestMergingTimes.add(pullRequest.getMerged_at());

        return pullRequestMergingTimes;
    }

    @SneakyThrows
    public List<String> getPullRequestUpdatingTimes() {
        List<PullRequest> pullRequests = getAppPullRequests();

        List<String> pullRequestUpdatingTimes = new ArrayList<>();

        for(PullRequest pullRequest : pullRequests)
            pullRequestUpdatingTimes.add(pullRequest.getUpdated_at());

        return pullRequestUpdatingTimes;
    }

    @SneakyThrows
    public List<String> getPullRequestClosingTimes() {
        List<PullRequest> pullRequests = getAppPullRequests();

        List<String> pullRequestClosingTimes = new ArrayList<>();

        for(PullRequest pullRequest : pullRequests)
            pullRequestClosingTimes.add(pullRequest.getClosed_at());

        return pullRequestClosingTimes;
    }

    @SneakyThrows
    public List<String> getPullRequestHeadsLabels() {
        List<PullRequest> pullRequests = getAppPullRequests();

        List<String> pullRequestHeadsLabels = new ArrayList<>();

        for(PullRequest pullRequest : pullRequests)
            pullRequestHeadsLabels.add(pullRequest.getHead().getLabel());

        return pullRequestHeadsLabels;
    }

    @SneakyThrows
    public List<String> getPullRequestHeadsRefs() {
        List<PullRequest> pullRequests = getAppPullRequests();

        List<String> pullRequestHeadsRefs = new ArrayList<>();

        for(PullRequest pullRequest : pullRequests)
            pullRequestHeadsRefs.add(pullRequest.getHead().getRef());

        return pullRequestHeadsRefs;
    }

    @SneakyThrows
    public List<String> getPullRequestBasesLabels() {
        List<PullRequest> pullRequests = getAppPullRequests();

        List<String> pullRequestBasesLabels = new ArrayList<>();

        for(PullRequest pullRequest : pullRequests)
            pullRequestBasesLabels.add(pullRequest.getBase().getLabel());

        return pullRequestBasesLabels;
    }

    @SneakyThrows
    public List<String> getPullRequestBasesRefs() {
        List<PullRequest> pullRequests = getAppPullRequests();

        List<String> pullRequestBasesRefs = new ArrayList<>();

        for(PullRequest pullRequest : pullRequests)
            pullRequestBasesRefs.add(pullRequest.getBase().getRef());

        return pullRequestBasesRefs;
    }
}
