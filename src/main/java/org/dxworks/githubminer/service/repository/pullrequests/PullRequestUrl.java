package org.dxworks.githubminer.service.repository.pullrequests;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;
import lombok.Data;

@Data
public class PullRequestUrl extends GenericUrl {

    @Key
    private String state;

    public PullRequestUrl(String encodedUrl, String state) {
        super(encodedUrl);
        this.state = state;
    }
}
