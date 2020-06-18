package org.dxworks.githubminer.dto.response.repository.pullrequests;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import lombok.Data;
import org.dxworks.githubminer.dto.commons.User;

@Data
public class PullRequest extends GenericJson {
    @Key
    private Long id;
    @Key
    private String state;
    @Key
    private String title;
    @Key
    private String body;
    @Key
    private User user;
    @Key
    private User assignee;
    @Key("merged_by")
    private User mergedBy;
    @Key("created_at")
    private String createdAt;
    @Key("merged_at")
    private String mergedAt;
    @Key("updated_at")
    private String updatedAt;
    @Key("closed_at")
    private String closedAt;
    @Key
    private PullRequestBranch head;
    @Key
    private PullRequestBranch base;
    @Key
    private Long number;
}
