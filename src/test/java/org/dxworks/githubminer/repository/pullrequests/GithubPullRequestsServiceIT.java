package org.dxworks.githubminer.repository.pullrequests;

import org.dxworks.githubminer.dto.response.repository.pullrequests.PullRequest;
import org.dxworks.githubminer.service.repository.pullrequests.GithubPullRequestsService;
import org.dxworks.githubminer.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GithubPullRequestsServiceIT {

    private GithubPullRequestsService service = new GithubPullRequestsService("andrei2699", "Music-Events-Application", TestUtils.getGithubCredentials());
    private List<PullRequest> pullRequests;

    @BeforeEach
    void setUp() {
        pullRequests = service.getAppPullRequests();
    }

    @AfterEach
    void tearDown() {
        pullRequests = null;
    }

    @Test
    void getAppPullRequests() {
        assertEquals(144, pullRequests.size());
    }

    @Test
    void getPullRequestTitles() {
        assertEquals("Mea 128", service.getPullRequestTitle(pullRequests.get(0)));
        assertEquals("Added Build.yml workflow to build the application on every push or pull request to Master branch", service.getPullRequestTitle(pullRequests.get(pullRequests.size() - 1)));
    }

    @Test
    void getPullRequestBodies() {
        assertEquals("", service.getPullRequestBody(pullRequests.get(0)));
        assertEquals("After you merge this Pull Request, a job will be run by Github every time you create a Pull Requestfor the 'master' branch, of every time commits get pushed to the master branch.\n" +
                "This way you will know if your application builds everytime you want to merge some changes to the holy 'master' branch.\n" +
                "If you look just know, you should have a job running, or already finished. Hopefully it didn't fail ;)\n" +
                "Anytime you want to see the result of these runs, you can find them under the Actions tab just on top of the repo page.\n" +
                "Now go ahead and merge this PR! Then make sure your build succeeded on the master branch. If it didn't, fix it, ASAP!!!", service.getPullRequestBody(pullRequests.get(pullRequests.size() - 1)));
    }

    @Test
    void getPullRequestAuthors() {
        assertEquals("taniatopciov", service.getPullRequestAuthor(pullRequests.get(0)));
        assertEquals("AndyMolin", service.getPullRequestAuthor(pullRequests.get(pullRequests.size() - 1)));
    }

    @Test
    void getPullRequestCreationTimes() {
        assertEquals("2020-06-08T09:23:01Z", service.getPullRequestCreationTime(pullRequests.get(0)));
        assertEquals("2020-04-27T13:25:27Z", service.getPullRequestCreationTime(pullRequests.get(pullRequests.size() - 1)));
    }

    @Test
    void getPullRequestMergingTimes() {
        assertEquals("2020-06-08T09:25:13Z", service.getPullRequestMergingTime(pullRequests.get(0)));
        assertEquals("2020-04-27T14:50:08Z", service.getPullRequestMergingTime(pullRequests.get(pullRequests.size() - 1)));
    }

    @Test
    void getPullRequestUpdatingTimes() {
        assertEquals("2020-06-08T09:25:13Z", service.getPullRequestUpdatingTime(pullRequests.get(0)));
        assertEquals("2020-04-27T14:50:08Z", service.getPullRequestUpdatingTime(pullRequests.get(pullRequests.size() - 1)));
    }

    @Test
    void getPullRequestClosingTimes() {
        assertEquals("2020-06-08T09:25:13Z", service.getPullRequestClosingTime(pullRequests.get(0)));
        assertEquals("2020-04-27T14:50:08Z", service.getPullRequestClosingTime(pullRequests.get(pullRequests.size() - 1)));
    }

    @Test
    void getPullRequestHeadsLabels() {
        String sha = pullRequests.get(0).getHead().getSha();
        System.out.println(sha);
        assertEquals("andrei2699:MEA-128", service.getPullRequestHeadLabel(pullRequests.get(0)));
        assertEquals("andrei2699:build-actions", service.getPullRequestHeadLabel(pullRequests.get(pullRequests.size() - 1)));
    }

    @Test
    void getPullRequestHeadsRefs() {
        assertEquals("MEA-128", service.getPullRequestHeadRef(pullRequests.get(0)));
        assertEquals("build-actions", service.getPullRequestHeadRef(pullRequests.get(pullRequests.size() - 1)));
    }

    @Test
    void getPullRequestBasesLabels() {
        assertEquals("andrei2699:master", service.getPullRequestBaseLabel(pullRequests.get(0)));
        assertEquals("andrei2699:master", service.getPullRequestBaseLabel(pullRequests.get(pullRequests.size() - 1)));
    }

    @Test
    void getPullRequestBasesRefs() {
        assertEquals("master", service.getPullRequestBaseRef(pullRequests.get(0)));
        assertEquals("master", service.getPullRequestBaseRef(pullRequests.get(pullRequests.size() - 1)));
    }

    @Test
    void getPullRequestCommitsSha() {
        assertEquals("97a53e06a0dae8a2f6a317cd15ae3edfb004838b", service.getPullRequestCommits(pullRequests.get(0).getNumber()).get(0).getSha());
        assertEquals("cc1cee578ac9d730bd11148d8c5f9371d80f5cd6", service.getPullRequestCommits(pullRequests.get(0).getNumber()).get(1).getSha());
    }

    @Test
    void getPullRequestSate() {
        assertEquals("APPROVED", service.getPullRequestReviews(pullRequests.get(0).getNumber()).get(0).getState());
        assertEquals("andrei2699", service.getPullRequestReviews(pullRequests.get(0).getNumber()).get(0).getUser().getLogin());
    }
}