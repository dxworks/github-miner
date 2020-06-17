package org.dxworks.githubminer.dto.export;

import com.google.api.client.util.Key;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dxworks.githubminer.dto.commons.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Key
    private Long id;
    @Key
    private String login;
    @Key
    private String url;
    @Key
    private String email;
    @Key
    private String name;
    @Key
    private String avatarUrl;

    public static UserDTO fromUser(User user) {
        if (user == null)
            return null;
        return UserDTO.builder()
                .avatarUrl(user.getAvatarUrl())
                .email(user.getEmail())
                .login(user.getLogin())
                .id(user.getId())
                .url(user.getUrl())
                .name(user.getName())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }
}
