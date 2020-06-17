package org.dxworks.githubminer.service.repository.commits;

import com.google.api.client.http.GenericUrl;
import com.google.common.reflect.TypeToken;
import org.dxworks.githubminer.dto.response.repository.commits.RepoCommit;
import org.dxworks.githubminer.service.repository.GithubRepositoryService;
import org.dxworks.utils.java.rest.client.providers.BasicAuthenticationProvider;

import java.lang.reflect.Type;
import java.util.List;

public class GithubCommitService extends GithubRepositoryService {
    private static final Type COMMIT_LIST_TYPE = new TypeToken<List<RepoCommit>>() {
    }.getType();
    public GithubCommitService(String owner, String repo) {
        super(owner, repo);
    }

    public GithubCommitService(String owner, String repo, BasicAuthenticationProvider authenticationProvider) {
        super(owner, repo, authenticationProvider);
    }

    public List<RepoCommit> getAllCommits() {
        GenericUrl commitsUrl = new GenericUrl(getApiPath("commits"));
        return getPaginationUtils().getAllElements(commitsUrl, COMMIT_LIST_TYPE);
    }
}
