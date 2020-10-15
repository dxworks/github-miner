package org.dxworks.githubminer.service.repository.commits;

import org.dxworks.githubminer.dto.response.repository.commits.RepoCommit;
import org.dxworks.githubminer.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GithubCommitServiceIT {

    private GithubCommitService githubCommitService = new GithubCommitService("MarioRivis", "SimpleRegistrationExample", TestUtils.getGithubCredentials());

    @Test
    void testGetAllCommits() {
        List<RepoCommit> allCommits = githubCommitService.getAllCommits();
        assertTrue(allCommits.stream().allMatch(repoCommit -> repoCommit instanceof RepoCommit));
        assertEquals(7, allCommits.size());
    }
}
