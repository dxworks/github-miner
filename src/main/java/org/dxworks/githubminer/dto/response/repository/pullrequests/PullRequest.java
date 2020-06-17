package org.dxworks.githubminer.dto.response.repository.pullrequests;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import lombok.Data;
import org.dxworks.githubminer.dto.commons.User;

@Data
public class PullRequest extends GenericJson {
    @Key
    private String state;
    @Key
    private String title;
    @Key
    private String body;
    @Key
    private User user;
    @Key
    private String created_at;
    @Key
    private String merged_at;
    @Key
    private String updated_at;
    @Key
    private String closed_at;
    @Key
    private PullRequestBranch head;
    @Key
    private PullRequestBranch base;
    @Key
    private int number;
}
