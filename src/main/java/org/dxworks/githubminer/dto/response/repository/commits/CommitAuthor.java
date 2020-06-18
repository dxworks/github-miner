package org.dxworks.githubminer.dto.response.repository.commits;

import com.google.api.client.util.Key;
import lombok.Data;
import org.dxworks.githubminer.dto.commons.Author;

@Data
public class CommitAuthor extends Author {
    @Key
    private String date;
}
