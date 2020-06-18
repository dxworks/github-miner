package org.dxworks.githubminer.dto.export;

import com.google.api.client.util.Key;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dxworks.githubminer.dto.response.repository.branches.Branch;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchDTO {
    @Key
    private String name;
    @Key
    private String commit;

    public static BranchDTO fromBranch(Branch branch) {
        if (branch == null || com.google.api.client.util.Data.isNull(branch))
            return null;
        return builder()
                .name(branch.getName())
                .commit(branch.getSha())
                .build();
    }
}
