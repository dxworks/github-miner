package org.dxworks.githubminer.dto.response.repository.commits;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dxworks.githubminer.dto.commons.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepoCommit extends GenericJson {
    @Key
    private String sha;
    @Key
    private Commit commit;
    @Key
    private User author;
    @Key
    private User committer;
}
