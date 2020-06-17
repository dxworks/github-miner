package org.dxworks.githubminer.dto.export;

import com.google.api.client.util.Key;
import lombok.Data;

import java.util.List;

@Data
class PullRequestDTO {
	@Key
	private Integer id;
	@Key
	private String title;
	@Key
	private String body;
	@Key
	private PRBranchDTO head;
	@Key
	private PRBranchDTO base;
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
	private String status;
	@Key
	private String author;
	@Key
	private String merger;
	@Key
	private List<String> approvedBy;
	@Key
	private List<PullRequestCommentDTO> comments;
}
