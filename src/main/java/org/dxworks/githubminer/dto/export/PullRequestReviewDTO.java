package org.dxworks.githubminer.dto.export;

import com.google.api.client.util.Key;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dxworks.githubminer.dto.response.repository.pullrequests.PullRequestReview;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PullRequestReviewDTO {
    @Key
    private UserDTO user;
    @Key
    private String state;
    @Key
    private String body;
    @Key
    private String date;

    public static PullRequestReviewDTO fromPullRequestReview(PullRequestReview pullRequestReview) {
        if (pullRequestReview == null || com.google.api.client.util.Data.isNull(pullRequestReview))
            return null;
        return builder()
                .body(pullRequestReview.getBody())
                .date(pullRequestReview.getSubmittedAt())
                .state(pullRequestReview.getState())
                .user(UserDTO.fromUser(pullRequestReview.getUser()))
                .build();
    }
}
