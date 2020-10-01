package com.messaging.exceptions;

import com.messaging.enums.BaseExceptionType;
import lombok.Builder;

import java.util.List;

public class RestBlockedByRecipientUserException extends RestException {

    public RestBlockedByRecipientUserException() {
        super(BaseExceptionType.REST_BLOCKED_BY_RECIPIENT_USER_EXCEPTION, null, null);
    }

    @Builder
    private RestBlockedByRecipientUserException(String message, List<String> errors) {
        super(BaseExceptionType.REST_BLOCKED_BY_RECIPIENT_USER_EXCEPTION, message, errors);
    }
}