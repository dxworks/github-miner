package org.dxworks.githubminer.service.repository.branches;

import com.google.api.client.http.GenericUrl;
import com.google.common.reflect.TypeToken;
import lombok.SneakyThrows;
import org.dxworks.githubminer.dto.response.repository.branches.Branch;
import org.dxworks.githubminer.service.repository.GithubRepositoryService;
import org.dxworks.utils.java.rest.client.providers.BasicAuthenticationProvider;
import org.dxworks.utils.java.rest.client.response.HttpResponse;

import java.lang.reflect.Type;
import java.util.List;

public class GithubBranchService extends GithubRepositoryService {
    private static final Type BRANCH_LIST_TYPE = new TypeToken<List<Branch>>() {
    }.getType();

    public GithubBranchService(String owner, String repo) {
        super(owner, repo);
    }

    public GithubBranchService(String owner, String repo, BasicAuthenticationProvider authenticationProvider) {
        super(owner, repo, authenticationProvider);
    }

    @SneakyThrows
    public List<Branch> getAllBranches() {
        String apiPath = getApiPath("branches");

        HttpResponse httpResponse = httpClient.get(new GenericUrl(apiPath));

        return (List<Branch>) httpResponse.parseAs(BRANCH_LIST_TYPE);
    }
}
