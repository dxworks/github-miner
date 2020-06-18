package org.dxworks.githubminer.dto.response.repository.pullrequests;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import lombok.Data;
import org.dxworks.githubminer.dto.commons.User;
import org.dxworks.githubminer.dto.response.repository.Repository;

@Data
public class PullRequestBranch extends GenericJson {
    @Key
    private String label;
    @Key
    private String ref;
    @Key
    private String sha;
    @Key
    private User user;
    @Key
    private Repository repo;
}
