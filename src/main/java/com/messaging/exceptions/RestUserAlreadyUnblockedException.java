package com.messaging.exceptions;

import com.messaging.enums.BaseExceptionType;
import lombok.Builder;

import java.util.List;

public class RestUserAlreadyUnblockedException extends RestException {

    public RestUserAlreadyUnblockedException() {
        super(BaseExceptionType.REST_USER_ALREADY_UNBLOCKED_EXCEPTION, null, null);
    }

    @Builder
    private RestUserAlreadyUnblockedException(String message, List<String> errors) {
        super(BaseExceptionType.REST_USER_ALREADY_UNBLOCKED_EXCEPTION, message, errors);
    }
}