package com.luzko.order.configuration.client;

import com.luzko.order.configuration.client.properties.ClientSettingsProperties;
import lombok.RequiredArgsConstructor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class ClientsConfig {
    private final ClientErrorHandler clientErrorHandler;
    private final RestTemplateBuilder restTemplateBuilder;

    @Bean
    @ConfigurationProperties(prefix = "clients.settings.warehouse-manager")
    public ClientSettingsProperties warehouseManagerClientSettingsProperties() {
        return new ClientSettingsProperties();
    }

    @Bean
    public RestTemplate warehouseManagerRestTemplate(
            final ClientSettingsProperties warehouseManagerClientSettingsProperties) {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(warehouseManagerClientSettingsProperties.getConnectionManager())
                .build();

        ClientHttpRequestFactory requestFactory =
                new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

        return restTemplateBuilder
                .requestFactory(() -> requestFactory)
                .rootUri(warehouseManagerClientSettingsProperties.getRootUri())
                .setConnectTimeout(warehouseManagerClientSettingsProperties.getConnectionTimeout())
                .setReadTimeout(warehouseManagerClientSettingsProperties.getReadTimeout())
                .errorHandler(clientErrorHandler)
                .build();
    }
}
