package com.luzko.order.configuration.client.properties;

import lombok.Data;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.Duration;

@Data
@Validated
public class ClientSettingsProperties {
    @NotNull
    private String rootUri;
    @NotNull
    private Duration connectionTimeout;
    @NotNull
    private Duration readTimeout;
    @NestedConfigurationProperty
    private PoolingHttpClientConnectionManager connectionManager;
}
