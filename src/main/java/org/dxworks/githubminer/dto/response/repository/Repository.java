package org.dxworks.githubminer.dto.response.repository;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import lombok.Data;
import org.dxworks.githubminer.dto.commons.User;

@Data
public class Repository extends GenericJson {
    @Key
    private Long id;
    @Key
    private String name;
    @Key("full_name")
    private String fullName;
    @Key
    private User owner;
    @Key("created_at")
    private String createdAt;
    @Key("updated_at")
    private String updatedAt;
    @Key
    private String language;
    @Key
    private String description;
}
