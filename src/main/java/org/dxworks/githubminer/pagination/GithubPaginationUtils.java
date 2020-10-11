package org.dxworks.githubminer.pagination;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestInitializer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dxworks.githubminer.http.GithubHttpResponse;
import org.dxworks.githubminer.service.GithubApiService;
import org.dxworks.utils.java.rest.client.response.HttpResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class GithubPaginationUtils extends GithubApiService {

    public GithubPaginationUtils() {
    }

    public GithubPaginationUtils(String githubRootUrl) {
        super(githubRootUrl);
    }

    public GithubPaginationUtils(HttpRequestInitializer httpRequestInitializer) {
        super(httpRequestInitializer);
    }

    public GithubPaginationUtils(String githubRootUrl, HttpRequestInitializer httpRequestInitializer) {
        super(githubRootUrl, httpRequestInitializer);
    }

    @SneakyThrows
    public <T> List<T> getAllElements(GenericUrl firstUrl, Type type) {

        GithubHttpResponse httpResponse = (GithubHttpResponse) getHttpResponse(firstUrl);

        List<T> elements = new ArrayList<>();

        while (hasNextPageLink(httpResponse)) {
            elements.addAll((Collection<T>) httpResponse.parseAs(type));
            GenericUrl genericUrl = new GenericUrl(httpResponse.getPageLinks().getNext());
            httpResponse = (GithubHttpResponse) getHttpResponse(genericUrl);
        }

        if (httpResponse != null)
            elements.addAll((Collection<T>) httpResponse.parseAs(type));

        return elements;
    }

    private HttpResponse getHttpResponse(GenericUrl genericUrl) {
        log.info("Retrieving " + genericUrl.toString());
        return httpClient.get(genericUrl);
    }

    private boolean hasNextPageLink(GithubHttpResponse httpResponse) {
        return httpResponse != null && httpResponse.getPageLinks().getNext() != null;
    }
}
