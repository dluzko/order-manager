package com.luzko.order.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_REQUEST(HttpStatus.BAD_REQUEST),
    UNHANDLED(HttpStatus.INTERNAL_SERVER_ERROR),
    WAREHOUSE_MANAGER_ERROR(HttpStatus.SERVICE_UNAVAILABLE);

    private final HttpStatus httpStatus;
}
