package org.dxworks.githubminer.pagination;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestInitializer;
import lombok.SneakyThrows;
import org.dxworks.githubminer.http.GithubHttpResponse;
import org.dxworks.githubminer.service.GithubApiService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GithubPaginationUtils extends GithubApiService {

    public GithubPaginationUtils() {
    }

    public GithubPaginationUtils(HttpRequestInitializer httpRequestInitializer) {
        super(httpRequestInitializer);
    }

    @SneakyThrows
    public <T> List<T> getAllElements(GenericUrl firstUrl, Type type) {

        GithubHttpResponse httpResponse = (GithubHttpResponse) httpClient.get(firstUrl);

        List<T> elements = new ArrayList<>();

        while (hasNextPageLink(httpResponse)) {
            elements.addAll((Collection<T>) httpResponse.parseAs(type));
            httpResponse = (GithubHttpResponse) httpClient.get(new GenericUrl(httpResponse.getPageLinks().getNext()));
        }

        if (httpResponse != null)
            elements.addAll((Collection<T>) httpResponse.parseAs(type));

        return elements;
    }

    private boolean hasNextPageLink(GithubHttpResponse httpResponse) {
        return httpResponse != null && httpResponse.getPageLinks().getNext() != null;
    }
}
