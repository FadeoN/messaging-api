package messaging.exceptions;

import lombok.Builder;
import messaging.enums.BaseExceptionType;

import java.util.List;

public class RestResourceNotFoundException extends RestException {

	public RestResourceNotFoundException() {
		super(BaseExceptionType.REST_RESOURCE_NOT_FOUND_EXCEPTION, null, null);
	}

	@Builder
	private RestResourceNotFoundException(String message, List<String> errors) {
		super(BaseExceptionType.REST_RESOURCE_NOT_FOUND_EXCEPTION, message, errors);
	}
}
