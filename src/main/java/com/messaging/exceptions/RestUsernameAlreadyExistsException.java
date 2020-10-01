package com.messaging.exceptions;

import lombok.Builder;
import com.messaging.enums.BaseExceptionType;

import java.util.List;

public class RestUsernameAlreadyExistsException extends RestException{

    public RestUsernameAlreadyExistsException() {
        super(BaseExceptionType.REST_USERNAME_ALREADY_EXISTS_EXCEPTION, null, null);
    }

    @Builder
    private RestUsernameAlreadyExistsException(String message, List<String> errors) {
        super(BaseExceptionType.REST_USERNAME_ALREADY_EXISTS_EXCEPTION, message, errors);
    }
}
