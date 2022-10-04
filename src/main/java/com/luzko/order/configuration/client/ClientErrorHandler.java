package com.luzko.order.configuration.client;

import com.luzko.order.service.exception.ProductRestClientException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.ResponseExtractor;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientErrorHandler extends DefaultResponseErrorHandler {

    private final List<HttpMessageConverter<?>> messageConverters;

    private ResponseExtractor<ClientErrorContext> responseExtractor;

    @PostConstruct
    public void init() {
        responseExtractor = new HttpMessageConverterExtractor<>(ClientErrorContext.class, messageConverters);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        ClientErrorContextEntry clientErrorContextEntry;
        if ((clientErrorContextEntry = getClientErrorContextEntry(response)) != null) {
            throw new ProductRestClientException(
                    clientErrorContextEntry.getCode(), clientErrorContextEntry.getMessage());
        } else {
            super.handleError(response);
        }
    }

    private ClientErrorContextEntry getClientErrorContextEntry(final ClientHttpResponse response) {
        try {
            ClientErrorContextEntry clientErrorContextEntry = null;
            ClientErrorContext errorContext = responseExtractor.extractData(response);
            if (errorContext != null && !CollectionUtils.isEmpty(errorContext.getErrors())) {
                clientErrorContextEntry = errorContext.getErrors().get(0);
            }
            return clientErrorContextEntry;
        } catch (Exception e) {
            return null;
        }
    }

    @Data
    private static class ClientErrorContext {
        private List<ClientErrorContextEntry> errors;
    }

    @Data
    private static class ClientErrorContextEntry {
        private String code;
        private String message;
    }
}
