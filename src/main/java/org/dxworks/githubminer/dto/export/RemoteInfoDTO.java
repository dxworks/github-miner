package org.dxworks.githubminer.dto.export;

import com.google.api.client.util.Key;
import lombok.Data;

import java.util.List;

@Data
public class RemoteInfoDTO {
	@Key
	private List<PullRequestDTO> pullRequests;
	@Key
	private List<CommitInfoDTO> commitInfos;
	@Key
	private List<BranchDTO> branches;
}
