package org.dxworks.githubminer.dto.export;

import com.google.api.client.util.Key;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dxworks.githubminer.dto.response.repository.pullrequests.PullRequest;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PullRequestDTO {
	@Key
	private Long id;
	@Key
	private Long number;
	@Key
	private String title;
	@Key
	private String body;
	@Key
	private PrBranchDTO head;
	@Key
	private PrBranchDTO base;
	@Key
	private List<String> commits;
	@Key
	private String createdAt;
	@Key
	private String updatedAt;
	@Key
	private String mergedAt;
	@Key
	private String closedAt;
	@Key
	private String state;
	@Key
	private UserDTO createdBy;
	@Key
	private UserDTO assignee;
	@Key
	private UserDTO mergedBy;
	@Key
	private List<PullRequestCommentDTO> comments;
	@Key
	private List<PullRequestReviewDTO> reviews;

	public static PullRequestDTO fromPullRequest(PullRequest pullRequest) {
		if (pullRequest == null || com.google.api.client.util.Data.isNull(pullRequest))
			return null;
		return builder()
				.id(pullRequest.getId())
				.number(pullRequest.getNumber())
				.title(pullRequest.getTitle())
				.body(pullRequest.getBody())
				.head(PrBranchDTO.fromPullRequestBranch(pullRequest.getHead()))
				.base(PrBranchDTO.fromPullRequestBranch(pullRequest.getBase()))
				.createdAt(pullRequest.getCreatedAt())
				.mergedAt(pullRequest.getMergedAt())
				.updatedAt(pullRequest.getUpdatedAt())
				.closedAt(pullRequest.getClosedAt())
				.state(pullRequest.getState())
				.createdBy(UserDTO.fromUser(pullRequest.getUser()))
				.assignee(UserDTO.fromUser(pullRequest.getAssignee()))
				.mergedBy(UserDTO.fromUser(pullRequest.getMergedBy()))
				.build();
	}
}
