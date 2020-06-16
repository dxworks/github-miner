package org.dxworks.githubminer.dto.response.repository.refs;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import lombok.Data;

@Data
public class RefObject extends GenericJson {
    @Key
    private String type;
    @Key
    private String sha;
}
