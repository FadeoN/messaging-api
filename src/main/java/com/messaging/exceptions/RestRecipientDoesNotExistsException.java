package com.messaging.exceptions;

import com.messaging.enums.BaseExceptionType;
import lombok.Builder;

import java.util.List;

public class RestRecipientDoesNotExistsException extends RestException {

    public RestRecipientDoesNotExistsException() {
        super(BaseExceptionType.REST_RECIPIENT_USER_DOES_NOT_EXISTS, null, null);
    }

    @Builder
    private RestRecipientDoesNotExistsException(String message, List<String> errors) {
        super(BaseExceptionType.REST_RECIPIENT_USER_DOES_NOT_EXISTS, message, errors);
    }
}