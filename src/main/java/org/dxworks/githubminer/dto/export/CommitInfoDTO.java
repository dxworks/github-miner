package org.dxworks.githubminer.dto.export;

import com.google.api.client.util.Key;
import lombok.Data;

@Data
public class CommitInfoDTO {
	@Key
	private String id;
	@Key
	private String author;
	@Key
	private String committer;
	@Key
	private String authorDate;
	@Key
	private String committerDate;
}
