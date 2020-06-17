package org.dxworks.githubminer.dto.response.repository.commits;

import com.google.api.client.util.Key;
import lombok.Data;

@Data
public class Commit {
    @Key
    private CommitAuthor author;
    @Key
    private CommitAuthor committer;
    @Key
    private String message;
    @Key
    private String url;
}
