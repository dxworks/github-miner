package org.dxworks.githubminer.dto.export;

import com.google.api.client.util.Key;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dxworks.githubminer.dto.response.repository.pullrequests.PullRequestBranch;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrBranchDTO {
    @Key
    private String commit;
    @Key
    private String label;
    @Key
    private String ref;
    @Key
    private UserDTO user;
    @Key
    private RepoDTO repo;

    public static PrBranchDTO fromPullRequestBranch(PullRequestBranch branch) {
        if (branch == null || com.google.api.client.util.Data.isNull(branch))
            return null;
        return builder()
                .commit(branch.getSha())
                .label(branch.getLabel())
                .ref(branch.getRef())
                .user(UserDTO.fromUser(branch.getUser()))
                .repo(RepoDTO.fromRepository(branch.getRepo()))
                .build();
    }
}
