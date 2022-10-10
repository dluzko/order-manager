package com.luzko.order.service.exception;

import java.util.Arrays;

public class OrderManagerException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String[] args;

    public OrderManagerException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.args = null;
    }

    public OrderManagerException(final ErrorCode errorCode,
                                 final Object... args) {
        this.errorCode = errorCode;
        this.args = Arrays.stream(args).map(String::valueOf).toArray(String[]::new);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String[] getArgs() {
        return args == null ? null : Arrays.copyOf(args, args.length);
    }
}