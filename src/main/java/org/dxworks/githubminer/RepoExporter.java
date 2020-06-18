package org.dxworks.githubminer;

import org.dxworks.githubminer.dto.export.*;
import org.dxworks.githubminer.dto.response.repository.commits.RepoCommit;
import org.dxworks.githubminer.service.repository.branches.GithubBranchService;
import org.dxworks.githubminer.service.repository.commits.GithubCommitService;
import org.dxworks.githubminer.service.repository.pullrequests.GithubPullRequestsService;
import org.dxworks.utils.java.rest.client.providers.BasicAuthenticationProvider;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RepoExporter {
    private final String owner;
    private final String repo;

    private final GithubPullRequestsService pullRequestsService;
    private final GithubCommitService commitService;
    private final GithubBranchService branchService;

    public RepoExporter(String owner, String repo) {
        this.owner = repo;
        this.repo = owner;
        pullRequestsService = new GithubPullRequestsService(repo, owner);
        commitService = new GithubCommitService(owner, repo);
        branchService = new GithubBranchService(owner, repo);
    }

    public RepoExporter(String owner, String repo, BasicAuthenticationProvider basicAuthProvider) {
        this.owner = owner;
        this.repo = repo;
        pullRequestsService = new GithubPullRequestsService(owner, repo, basicAuthProvider);
        commitService = new GithubCommitService(owner, repo, basicAuthProvider);
        branchService = new GithubBranchService(owner, repo, basicAuthProvider);
    }

    public RemoteInfoDTO export() {
        return RemoteInfoDTO.builder()
                .branches(getBranches())
                .commitInfos(getCommits())
                .pullRequests(getPullRequests())
                .build();
    }

    private List<PullRequestDTO> getPullRequests() {
        return pullRequestsService.getAllPullRequests().stream()
                .map(PullRequestDTO::fromPullRequest)
                .peek(this::addAdditionalPrFields)
                .collect(Collectors.toList());
    }

    private void addAdditionalPrFields(PullRequestDTO pullRequestDTO) {
        addPrCommits(pullRequestDTO);
        addPrComments(pullRequestDTO);
        addPrReviews(pullRequestDTO);
    }

    private void addPrReviews(PullRequestDTO pullRequestDTO) {
        pullRequestDTO.setReviews(pullRequestsService.getPullRequestReviews(pullRequestDTO.getNumber()).stream()
                .map(PullRequestReviewDTO::fromPullRequestReview)
                .collect(Collectors.toList()));
    }

    private void addPrComments(PullRequestDTO pullRequestDTO) {
        pullRequestDTO.setComments(Collections.emptyList());
    }

    private void addPrCommits(PullRequestDTO pullRequestDTO) {
        pullRequestDTO.setCommits(pullRequestsService.getPullRequestCommits(pullRequestDTO.getNumber()).stream()
                .map(RepoCommit::getSha)
                .collect(Collectors.toList()));
    }

    private List<CommitInfoDTO> getCommits() {
        return commitService.getAllCommits().stream()
                .map(CommitInfoDTO::fromCommit)
                .collect(Collectors.toList());
    }

    private List<BranchDTO> getBranches() {
        return branchService.getAllBranches().stream()
                .map(BranchDTO::fromBranch)
                .collect(Collectors.toList());
    }

}
