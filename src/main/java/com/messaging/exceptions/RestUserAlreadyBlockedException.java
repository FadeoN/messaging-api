package com.messaging.exceptions;

import com.messaging.enums.BaseExceptionType;
import lombok.Builder;

import java.util.List;

public class RestUserAlreadyBlockedException extends RestException {

    public RestUserAlreadyBlockedException() {
        super(BaseExceptionType.REST_USER_ALREADY_BLOCKED_EXCEPTION, null, null);
    }

    @Builder
    private RestUserAlreadyBlockedException(String message, List<String> errors) {
        super(BaseExceptionType.REST_USER_ALREADY_BLOCKED_EXCEPTION, message, errors);
    }
}

