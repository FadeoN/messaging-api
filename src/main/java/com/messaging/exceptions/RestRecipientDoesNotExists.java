package com.messaging.exceptions;

import com.messaging.enums.BaseExceptionType;
import lombok.Builder;

import java.util.List;

public class RestRecipientDoesNotExists extends RestException {

    public RestRecipientDoesNotExists() {
        super(BaseExceptionType.REST_RECIPIENT_USER_DOES_NOT_EXISTS, null, null);
    }

    @Builder
    private RestRecipientDoesNotExists(String message, List<String> errors) {
        super(BaseExceptionType.REST_RECIPIENT_USER_DOES_NOT_EXISTS, message, errors);
    }
}