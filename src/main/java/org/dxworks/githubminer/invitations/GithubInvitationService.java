package org.dxworks.githubminer.invitations;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;
import lombok.SneakyThrows;
import org.dxworks.githubminer.GithubApiService;
import org.dxworks.githubminer.dto.response.invitations.Invitation;
import org.dxworks.utils.java.rest.client.providers.BasicAuthenticationProvider;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

public class GithubInvitationService extends GithubApiService {
    public GithubInvitationService() {
        super();
    }

    public GithubInvitationService(BasicAuthenticationProvider authenticationProvider) {
        super(authenticationProvider);
    }

    @SneakyThrows
    public List<Invitation> listInvitationsForUser() {
        String apiPath = getApiPath("user", "repository_invitations");

        HttpResponse httpResponse = httpClient.get(new GenericUrl(apiPath));

        Type type = new TypeToken<List<Invitation>>() {
        }.getType();
        return (List<Invitation>) httpResponse.parseAs(type);
    }

    public boolean acceptInvitation(Invitation invitation) {
        return acceptInvitation(invitation.getId());
    }

    public boolean acceptInvitation(BigDecimal invitationId) {
        ImmutableMap<String, String> map = ImmutableMap.of("invitation_id", invitationId.toString());
        String apiPath = getApiPath(map, "user", "repository_invitations", ":invitation_id");

        HttpResponse httpResponse = httpClient.patch(new GenericUrl(apiPath), null);

        return httpResponse.getStatusCode() == 204;
    }
}
