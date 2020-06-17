package org.dxworks.githubminer.dto.response.repository.branches;

import com.google.api.client.util.Key;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
public class Branch {
    @Key
    private String name;
    @Key
    @Delegate
    private BranchCommit commit;
    @Key("protected")
    private boolean isProtected;
}
