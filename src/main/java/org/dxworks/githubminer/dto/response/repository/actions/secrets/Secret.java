package org.dxworks.githubminer.dto.response.repository.actions.secrets;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class Secret extends GenericJson {

    @Key
    private String name;
}
