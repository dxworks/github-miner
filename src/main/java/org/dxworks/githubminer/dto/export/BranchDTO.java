package org.dxworks.githubminer.dto.export;

import com.google.api.client.util.Key;
import lombok.Data;

@Data
class BranchDTO {
	@Key
	private String commit;
	@Key
	private String label;
	@Key
	private String ref;
	@Key
	private String user;
	@Key
	private RepoDTO repo;
}
