package org.dxworks.githubminer.repository.actions.secrets;

import com.google.api.client.http.GenericUrl;
import com.google.common.collect.ImmutableMap;
import com.goterl.lazycode.lazysodium.LazySodiumJava;
import com.goterl.lazycode.lazysodium.SodiumJava;
import com.goterl.lazycode.lazysodium.exceptions.SodiumException;
import com.goterl.lazycode.lazysodium.utils.Base64MessageEncoder;
import com.goterl.lazycode.lazysodium.utils.Key;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dxworks.githubminer.dto.request.repository.actions.secrets.SecretRequestBody;
import org.dxworks.githubminer.dto.response.repository.actions.secrets.Secret;
import org.dxworks.githubminer.dto.response.repository.actions.secrets.SecretsForRepo;
import org.dxworks.githubminer.dto.response.repository.actions.secrets.SecretsPublicKey;
import org.dxworks.githubminer.repository.GithubRepositoryService;
import org.dxworks.utils.java.rest.client.providers.BasicAuthenticationProvider;
import org.dxworks.utils.java.rest.client.response.HttpResponse;

import java.util.List;

@Slf4j
public class GithubSecretsService extends GithubRepositoryService {


    public GithubSecretsService(String owner, String repo, BasicAuthenticationProvider authenticationProvider) {
        super(owner, repo, authenticationProvider);
    }

    @SneakyThrows
    public List<Secret> listAllSecretsForRepo() {


        String apiPath = getApiPath("actions", "secrets");

        HttpResponse httpResponse = httpClient.get(new GenericUrl(apiPath));

        SecretsForRepo secretsForRepo = httpResponse.parseAs(SecretsForRepo.class);

        return secretsForRepo.getSecrets();
    }

    @SneakyThrows
    public SecretsPublicKey getPublicKey() {

        String apiPath = getApiPath("actions", "secrets", "public-key");

        HttpResponse httpResponse = httpClient.get(new GenericUrl(apiPath));

        return httpResponse.parseAs(SecretsPublicKey.class);
    }

    @SneakyThrows
    public boolean createSecret(String name, String value) {
        String apiPath = getApiPath(ImmutableMap.of("name", name), "actions", "secrets", ":name");

        SecretRequestBody body = createNewSecret(value);

        HttpResponse httpResponse = httpClient.put(new GenericUrl(apiPath), body);

        int statusCode = httpResponse.getStatusCode();
        return statusCode == 201 || statusCode == 204;
    }

    private SecretRequestBody createNewSecret(String value) {
        SecretsPublicKey publicKey = getPublicKey();

        LazySodiumJava lazySodiumJava = new LazySodiumJava(new SodiumJava(), new Base64MessageEncoder());

        String encryptedSecret;
        try {
            encryptedSecret = lazySodiumJava.cryptoBoxSealEasy(value, Key.fromBase64String(publicKey.getKey()));
        } catch (SodiumException e) {
            log.error("Secret encryption failed!", e);
            return null;
        }

        return new SecretRequestBody(encryptedSecret, publicKey.getKey_id());

    }

}
