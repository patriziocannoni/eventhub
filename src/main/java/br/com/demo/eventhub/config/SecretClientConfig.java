package br.com.demo.eventhub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;

@Configuration
public class SecretClientConfig {
    
    @Value("${spring.cloud.azure.keyvault.secret.endpoint}")
    private String keyVaultUrl;

    @Value("${spring.cloud.azure.keyvault.secret.client-secret}")
    private String appClientSecret;

    @Value("${spring.cloud.azure.keyvault.secret.client-id}")
    private String clientId;

    @Value("${spring.cloud.azure.keyvault.secret.tenant-id}")
    private String tenantId;

    @Bean
    public SecretClient secretClient() {
        ClientSecretCredential credential = new ClientSecretCredentialBuilder()
            .tenantId(tenantId)
            .clientId(clientId)
            .clientSecret(appClientSecret)
            .build();

        return new SecretClientBuilder()
            .vaultUrl(keyVaultUrl)
            .credential(credential)
            .buildClient();
    }

}
