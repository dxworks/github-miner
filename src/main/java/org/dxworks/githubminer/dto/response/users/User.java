package org.dxworks.githubminer.dto.response.users;

import com.google.api.client.util.Key;
import lombok.Data;

@Data
public class User {
    @Key
    private String login;
    @Key
    private String id;
    @Key
    private String avatarUrl;
}
