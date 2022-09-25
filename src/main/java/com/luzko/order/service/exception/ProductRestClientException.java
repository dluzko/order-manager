package com.luzko.order.service.exception;

import lombok.Getter;
import org.springframework.web.client.RestClientException;

@Getter
public class ProductRestClientException extends RestClientException {

    private final String error;

    public ProductRestClientException(final String error, final String msg) {
        super(msg);
        this.error = error;
    }
}
