package org.dxworks.githubminer.dto.response.repository.branches;

import com.google.api.client.util.Key;
import lombok.Data;

@Data
public class BranchCommit {
    @Key
    private String sha;
}
