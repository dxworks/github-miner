package org.dxworks.githubminer.pagination;

import lombok.Getter;
import org.dxworks.githubminer.http.GithubHttpResponse;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Getter
public class PageLinks {

    protected static final String COMMA = ",";
    protected static final String SEMICOLON = ";";
    protected static final String HEADER_LINK = "Link";
    protected static final String HEADER_NEXT = "X-Next";
    protected static final String HEADER_LAST = "X-Last";
    protected static final String REL = "rel";
    protected static final String LAST = "last";
    protected static final String NEXT = "next";
    protected static final String FIRST = "first";
    protected static final String PREV = "prev";
    protected static final String URL = "url";

    private static final String LINK_REGEX = "<(?<url>[^\\s]*)>;\\s*rel=\\\"(?<rel>next|last|first|prev)\\\"";
    private static final Pattern LINK_PATTERN = Pattern.compile(LINK_REGEX);


    private String first;
    private String last;
    private String next;
    private String prev;

    private GithubHttpResponse response;

    public PageLinks(GithubHttpResponse response) {
        this.response = response;
        String linkHeader = getLinkHeader(HEADER_LINK);
        if (linkHeader != null) {
            Arrays.stream(linkHeader.split(COMMA)).forEach(link -> {
                Matcher matcher = LINK_PATTERN.matcher(link);
                if (matcher.find()) {
                    String url = matcher.group(URL);
                    String rel = matcher.group(REL);
                    setValue(rel, url);
                }
            });
        } else {
            next = getLinkHeader(HEADER_NEXT);
            last = getLinkHeader(HEADER_LAST);
        }
    }

    private void setValue(String rel, String url) {
        switch (rel) {
            case NEXT:
                next = url;
                break;
            case PREV:
                prev = url;
                break;
            case LAST:
                last = url;
                break;
            case FIRST:
                first = url;
                break;
        }
    }

    private String getLinkHeader(String headerLink) {
        Object headers = response.getHeaders().get(headerLink);
        if(headers instanceof String)
            return (String) headers;
        else if(headers instanceof List)
            return ((List<String>) headers).stream().findFirst()
                    .orElse("");

        return "";
    }
}
