package org.dxworks.githubminer.repository.pullrequests;

import org.dxworks.githubminer.dto.response.repository.pullrequests.PullRequest;
import org.dxworks.githubminer.service.repository.pullrequests.GithubPullRequestsService;
import org.dxworks.githubminer.utils.TestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GithubPullRequestsServiceIT {

    private static final GithubPullRequestsService service = new GithubPullRequestsService("andrei2699", "Music-Events-Application", TestUtils.getGithubCredentials());
    private static List<PullRequest> pullRequests;

    @BeforeAll
    static void setUpAll() {
        pullRequests = service.getAllPullRequests();
    }

    @Test
    void testGetAllPullRequests() {
        assertEquals(144, pullRequests.size());
    }

    @Test
    void getPullRequestTitles() {
        assertEquals("Added Build.yml workflow to build the application on every push or pull request to Master branch", pullRequests.get(pullRequests.size() - 1).getTitle());
    }

    @Test
    void getPullRequestBodies() {
        assertEquals("After you merge this Pull Request, a job will be run by Github every time you create a Pull Requestfor the 'master' branch, of every time commits get pushed to the master branch.\n" +
                "This way you will know if your application builds everytime you want to merge some changes to the holy 'master' branch.\n" +
                "If you look just know, you should have a job running, or already finished. Hopefully it didn't fail ;)\n" +
                "Anytime you want to see the result of these runs, you can find them under the Actions tab just on top of the repo page.\n" +
                "Now go ahead and merge this PR! Then make sure your build succeeded on the master branch. If it didn't, fix it, ASAP!!!", pullRequests.get(pullRequests.size() - 1).getBody());
    }

    @Test
    void getPullRequestAuthors() {
        assertEquals("AndyMolin", pullRequests.get(pullRequests.size() - 1).getUser().getLogin());
    }

    @Test
    void getPullRequestCreationTimes() {
        assertEquals("2020-04-27T13:25:27Z", pullRequests.get(pullRequests.size() - 1).getCreatedAt());
    }

    @Test
    void getPullRequestMergingTimes() {
        assertEquals("2020-04-27T14:50:08Z", pullRequests.get(pullRequests.size() - 1).getMergedAt());
    }

    @Test
    void getPullRequestUpdatingTimes() {
        assertEquals("2020-04-27T14:50:08Z", pullRequests.get(pullRequests.size() - 1).getUpdatedAt());
    }

    @Test
    void getPullRequestClosingTimes() {
        assertEquals("2020-04-27T14:50:08Z", pullRequests.get(pullRequests.size() - 1).getClosedAt());
    }

    @Test
    void getPullRequestHeadsLabels() {
        assertEquals("andrei2699:build-actions", pullRequests.get(pullRequests.size() - 1).getHead().getLabel());
    }

    @Test
    void getPullRequestHeadsRefs() {
        assertEquals("build-actions", pullRequests.get(pullRequests.size() - 1).getHead().getRef());
    }

    @Test
    void getPullRequestBasesLabels() {
        assertEquals("andrei2699:master", pullRequests.get(pullRequests.size() - 1).getBase().getLabel());
    }

    @Test
    void getPullRequestBasesRefs() {
        assertEquals("master", pullRequests.get(pullRequests.size() - 1).getBase().getRef());
    }

    @Test
    void getPullRequestMergedBy() {
        assertEquals("AndyMolin", pullRequests.get(pullRequests.size() - 1).getMergedBy().getLogin());
    }

    @Test
    void getPullRequestCommitsSha() {
        assertEquals("ec44981f2b7ee9e4191bd3d91679cb8a000e1466", service.getPullRequestCommits(pullRequests.get(pullRequests.size() - 1).getNumber()).get(0).getSha());
        assertEquals("c5eae64bb0e14c1852fd0dca686d3ddcbb47b3fa", service.getPullRequestCommits(pullRequests.get(pullRequests.size() - 1)).get(1).getSha());
    }

    @Test
    void getPullRequestSate() {
        assertEquals("APPROVED", service.getPullRequestReviews(123).get(0).getState());
        assertEquals("andrei2699", service.getPullRequestReviews(123).get(0).getUser().getLogin());
    }
}
