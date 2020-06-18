package org.dxworks.githubminer.dto.commons;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import lombok.Data;

@Data
public class User extends GenericJson {
    @Key
    private Long id;
    @Key
    private String login;
    @Key
    private String url;
    @Key
    private String email;
    @Key
    private String name;
    @Key
    private String avatarUrl;
}
