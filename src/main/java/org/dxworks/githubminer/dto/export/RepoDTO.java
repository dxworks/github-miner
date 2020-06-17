package org.dxworks.githubminer.dto.export;

import com.google.api.client.util.Key;
import lombok.Data;


@Data
class RepoDTO {
	@Key
	private Integer id;
	@Key
	private String name;
	@Key
	private String fullName;
	@Key
	private String owner;
}
