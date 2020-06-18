package org.dxworks.githubminer.dto.export;

import com.google.api.client.util.Key;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dxworks.githubminer.dto.response.repository.Repository;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepoDTO {
	@Key
	private Long id;
	@Key
	private String name;
	@Key
	private String fullName;
	@Key
	private UserDTO owner;

	public static RepoDTO fromRepository(Repository repo) {
		if (repo == null || com.google.api.client.util.Data.isNull(repo))
			return null;
		return builder()
				.id(repo.getId())
				.name(repo.getName())
				.fullName(repo.getFullName())
				.owner(UserDTO.fromUser(repo.getOwner()))
				.build();
	}
}
