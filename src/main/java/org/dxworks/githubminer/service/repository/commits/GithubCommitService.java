package org.dxworks.githubminer.service.repository.commits;

import com.google.common.reflect.TypeToken;
import org.dxworks.githubminer.dto.response.repository.commits.Commit;
import org.dxworks.githubminer.service.repository.GithubRepositoryService;
import org.dxworks.utils.java.rest.client.providers.BasicAuthenticationProvider;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class GithubCommitService extends GithubRepositoryService {
    private static final Type COMMIT_LIST_TYPE = new TypeToken<List<Commit>>() {
    }.getType();

    public GithubCommitService(String owner, String repo) {
        super(owner, repo);
    }

    public GithubCommitService(String owner, String repo, BasicAuthenticationProvider authenticationProvider) {
        super(owner, repo, authenticationProvider);
    }

    public List<Commit> getAllCommits() {
        return Collections.emptyList();
    }
}
