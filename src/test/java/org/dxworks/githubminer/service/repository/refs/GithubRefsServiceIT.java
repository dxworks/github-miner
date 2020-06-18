package org.dxworks.githubminer.service.repository.refs;

import org.dxworks.githubminer.dto.response.repository.refs.Ref;
import org.dxworks.githubminer.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GithubRefsServiceIT {

    private GithubRefsService service = new GithubRefsService("MarioRivis", "SimpleRegistrationExample", TestUtils.getGithubCredentials());

    @Test
    void getRef() {
        Ref ref = service.getRef("heads/master");
        assertEquals("refs/heads/master", ref.getRef());
        assertEquals("commit", ref.getObject().getType());
        assertNotNull(ref.getObject().getSha());
    }

}
