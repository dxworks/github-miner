package org.dxworks.githubminer.dto.response.repository.pullrequests;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import lombok.Data;

@Data
public class User extends GenericJson {
    @Key
    private String login;
}
