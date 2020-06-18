package org.dxworks.githubminer.service.repository;

import com.google.common.collect.ImmutableMap;
import org.dxworks.githubminer.service.GithubApiService;
import org.dxworks.utils.java.rest.client.providers.BasicAuthenticationProvider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class GithubRepositoryService extends GithubApiService {

    protected String owner;
    protected String repo;

    private ImmutableMap<String, String> map;

    public GithubRepositoryService(String owner, String repo) {
        super();
        this.owner = owner;
        this.repo = repo;

        initMap(owner, repo);
    }

    public GithubRepositoryService(String owner, String repo, BasicAuthenticationProvider authenticationProvider) {
        super(authenticationProvider);
        this.owner = owner;
        this.repo = repo;

        initMap(owner, repo);
    }

    private void initMap(String owner, String repo) {
        map = ImmutableMap.of("repo", repo, "owner", owner);
    }

    @Override
    public String getApiPath(Map<String, String> variablesValues, String... pathVariables) {
        Map<String, String> newMap = new HashMap<>(variablesValues);
        newMap.putAll(map);

        return super.getApiPath(newMap,
                Stream.concat(Stream.of("repos", ":owner", ":repo"), Arrays.stream(pathVariables)).toArray(String[]::new));
    }

    @Override
    public String getApiPath(String... pathVariables) {
        return super.getApiPath(map,
                Stream.concat(Stream.of("repos", ":owner", ":repo"), Arrays.stream(pathVariables)).toArray(String[]::new));
    }
}
