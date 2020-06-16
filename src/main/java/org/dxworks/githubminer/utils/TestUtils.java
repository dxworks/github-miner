package org.dxworks.githubminer.utils;

import lombok.SneakyThrows;
import org.dxworks.utils.java.rest.client.providers.BasicAuthenticationProvider;

import java.io.FileInputStream;
import java.nio.file.Paths;
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
        properties.load(new FileInputStream(Paths.get("config", "test.properties").toFile()));
    }

    public static BasicAuthenticationProvider getGithubCredentials() {
        return new BasicAuthenticationProvider(getProperty("test.github.username"), getProperty("test.github.token"));
    }

    public static BasicAuthenticationProvider getJiraCredentials() {
        return new BasicAuthenticationProvider(getProperty("test.jira.username"), getProperty("test.jira.token"));
    }
}
