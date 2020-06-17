package org.dxworks.githubminer.dto.export;

import com.google.api.client.util.Key;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemoteInfoDTO {
	@Key
	private List<PullRequestDTO> pullRequests;
	@Key
	private List<CommitInfoDTO> commitInfos;
	@Key
	private List<BranchDTO> branches;
}
