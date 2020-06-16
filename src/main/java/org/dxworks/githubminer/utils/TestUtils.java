package org.dxworks.githubminer.utils;

import lombok.SneakyThrows;
import org.dxworks.utils.java.rest.client.providers.BasicAuthenticationProvider;

import java.util.Properties;

public class TestUtils {

    private static Properties properties;

    public static String getProperty(String key) {
        if (properties == null)
            loadProperties();

        return properties.getProperty(key);
    }

    @SneakyThrows
    private static void loadProperties() {
        properties = new Properties();
        properties.load(TestUtils.class.getClassLoader().getResourceAsStream("test.properties"));
    }

    public static BasicAuthenticationProvider getGithubCredentials() {
        return new BasicAuthenticationProvider(getProperty("test.github.username"), getProperty("test.github.token"));
    }

    public static BasicAuthenticationProvider getJiraCredentials() {
        return new BasicAuthenticationProvider(getProperty("test.jira.username"), getProperty("test.jira.token"));
    }
}
