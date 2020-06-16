package org.dxworks.githubminer.dto.response.repository.actions.secrets;

import com.google.api.client.util.Key;
import lombok.Data;

@Data
public class SecretsPublicKey {
    @Key
    private String key;
    @Key
    private String key_id;
}
