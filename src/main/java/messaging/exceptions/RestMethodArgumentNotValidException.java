package messaging.exceptions;


import lombok.Builder;
import messaging.enums.BaseExceptionType;

import java.util.List;

public class RestMethodArgumentNotValidException extends RestException {

	public RestMethodArgumentNotValidException() {
		super(BaseExceptionType.REST_METHOD_ARGUEMENT_NOT_VALID_EXCEPTION, null, null);
	}

	@Builder
	private RestMethodArgumentNotValidException(String message, List<String> errors) {
		super(BaseExceptionType.REST_METHOD_ARGUEMENT_NOT_VALID_EXCEPTION, message, errors);
	}

}