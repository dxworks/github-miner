package org.dxworks.githubminer.dto.response.repository.pullrequests;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import lombok.Data;
import org.dxworks.githubminer.dto.commons.User;

@Data
public class PullRequestReview extends GenericJson {
    @Key
    private String state;
    @Key
    private User user;
    @Key
    private String body;
}
