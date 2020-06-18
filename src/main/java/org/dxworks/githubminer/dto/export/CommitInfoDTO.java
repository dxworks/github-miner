package org.dxworks.githubminer.dto.export;

import com.google.api.client.util.Key;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dxworks.githubminer.dto.response.repository.commits.RepoCommit;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommitInfoDTO {
	@Key
	private String id;
	@Key
	private UserDTO author;
	@Key
	private UserDTO committer;

	public static CommitInfoDTO fromCommit(RepoCommit repoCommit) {
		if (repoCommit == null || com.google.api.client.util.Data.isNull(repoCommit))
			return null;
		return builder()
				.id(repoCommit.getSha())
				.author(UserDTO.fromUser(repoCommit.getAuthor()))
				.committer(UserDTO.fromUser(repoCommit.getCommitter()))
				.build();
	}
}
