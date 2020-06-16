package org.dxworks.githubminer.dto.commons;

import com.google.api.client.util.Key;
import lombok.Data;

@Data
public class Author {
    @Key
    private String name;
    @Key
    private String email;
}
