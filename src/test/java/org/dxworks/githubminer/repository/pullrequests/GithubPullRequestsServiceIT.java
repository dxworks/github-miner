package org.dxworks.githubminer.repository.pullrequests;

import org.dxworks.githubminer.dto.response.repository.pullrequests.PullRequest;
import org.dxworks.githubminer.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GithubPullRequestsServiceIT {

    private GithubPullRequestsService service = new GithubPullRequestsService("andrei2699", "Music-Events-Application", TestUtils.getGithubCredentials());

    @Test
    void getAppPullRequests() {

        List<PullRequest> pullRequests = service.getAppPullRequests();

        assertEquals(144, pullRequests.size());
    }

    @Test
    void getPullRequestTitles() {
        List<String> pullRequestTitles = service.getPullRequestTitles();

        assertEquals("Mea 128", pullRequestTitles.get(0));
        assertEquals("Added Build.yml workflow to build the application on every push or pull request to Master branch", pullRequestTitles.get(pullRequestTitles.size()-1));
    }

    @Test
    void getPullRequestBodies() {
        List<String> pullRequestBodies = service.getPullRequestBodies();

        assertEquals("", pullRequestBodies.get(0));
        assertEquals("After you merge this Pull Request, a job will be run by Github every time you create a Pull Requestfor the 'master' branch, of every time commits get pushed to the master branch.\n" +
                "This way you will know if your application builds everytime you want to merge some changes to the holy 'master' branch.\n" +
                "If you look just know, you should have a job running, or already finished. Hopefully it didn't fail ;)\n" +
                "Anytime you want to see the result of these runs, you can find them under the Actions tab just on top of the repo page.\n" +
                "Now go ahead and merge this PR! Then make sure your build succeeded on the master branch. If it didn't, fix it, ASAP!!!", pullRequestBodies.get(pullRequestBodies.size()-1));
    }

    @Test
    void getPullRequestAuthors() {
        List<String> pullRequestAuthors = service.getPullRequestAuthors();

        assertEquals("taniatopciov", pullRequestAuthors.get(0));
        assertEquals("AndyMolin", pullRequestAuthors.get(pullRequestAuthors.size()-1));
    }

    @Test
    void getPullRequestCreationTimes() {
        List<String> pullRequestCreationTimes = service.getPullRequestCreationTimes();

        assertEquals("2020-06-08T09:23:01Z", pullRequestCreationTimes.get(0));
        assertEquals("2020-04-27T13:25:27Z", pullRequestCreationTimes.get(pullRequestCreationTimes.size()-1));
    }

    @Test
    void getPullRequestMergingTimes() {
        List<String> pullRequestMergingTimes = service.getPullRequestMergingTimes();

        assertEquals("2020-06-08T09:25:13Z", pullRequestMergingTimes.get(0));
        assertEquals("2020-04-27T14:50:08Z", pullRequestMergingTimes.get(pullRequestMergingTimes.size()-1));
    }

    @Test
    void getPullRequestUpdatingTimes() {
        List<String> pullRequestUpdatingTimes = service.getPullRequestUpdatingTimes();

        assertEquals("2020-06-08T09:25:13Z", pullRequestUpdatingTimes.get(0));
        assertEquals("2020-04-27T14:50:08Z", pullRequestUpdatingTimes.get(pullRequestUpdatingTimes.size()-1));
    }

    @Test
    void getPullRequestClosingTimes() {
        List<String> pullRequestClosingTimes = service.getPullRequestClosingTimes();

        assertEquals("2020-06-08T09:25:13Z", pullRequestClosingTimes.get(0));
        assertEquals("2020-04-27T14:50:08Z", pullRequestClosingTimes.get(pullRequestClosingTimes.size()-1));
    }

    @Test
    void getPullRequestHeadsLabels() {
        List<String> pullRequestHeadsLabels = service.getPullRequestHeadsLabels();

        assertEquals("andrei2699:MEA-128", pullRequestHeadsLabels.get(0));
        assertEquals("andrei2699:build-actions", pullRequestHeadsLabels.get(pullRequestHeadsLabels.size()-1));
    }

    @Test
    void getPullRequestHeadsRefs() {
        List<String> pullRequestHeadsRefs = service.getPullRequestHeadsRefs();

        assertEquals("MEA-128", pullRequestHeadsRefs.get(0));
        assertEquals("build-actions", pullRequestHeadsRefs.get(pullRequestHeadsRefs.size()-1));
    }

    @Test
    void getPullRequestBasesLabels() {
        List<String> pullRequestBasesLabels = service.getPullRequestBasesLabels();

        assertEquals("andrei2699:master", pullRequestBasesLabels.get(0));
        assertEquals("andrei2699:master", pullRequestBasesLabels.get(pullRequestBasesLabels.size()-1));
    }

    @Test
    void getPullRequestBasesRefs() {
        List<String> pullRequestBasesRefs = service.getPullRequestBasesRefs();

        assertEquals("master", pullRequestBasesRefs.get(0));
        assertEquals("master", pullRequestBasesRefs.get(pullRequestBasesRefs.size()-1));
    }
}