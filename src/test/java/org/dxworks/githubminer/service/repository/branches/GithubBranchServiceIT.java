package org.dxworks.githubminer.service.repository.branches;

import org.dxworks.githubminer.dto.response.repository.branches.Branch;
import org.dxworks.githubminer.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GithubBranchServiceIT {

    private GithubBranchService githubBranchService = new GithubBranchService("MarioRivis", "SimpleRegistrationExample", TestUtils.getGithubCredentials());

    @Test
    void tetGetAllBranches() {
        List<Branch> allCommits = githubBranchService.getAllBranches();
        assertTrue(allCommits.stream().allMatch(repoCommit -> repoCommit instanceof Branch));
        assertEquals(8, allCommits.size());
    }
}
