package messaging.exceptions;

import lombok.Builder;
import messaging.enums.BaseExceptionType;

import java.util.List;

public class RestGeneralException extends RestException {

	public RestGeneralException() {
		super(BaseExceptionType.REST_GENERAL_EXCEPTION, null, null);
	}

	@Builder
	private RestGeneralException(String message, List<String> errors) {
		super(BaseExceptionType.REST_GENERAL_EXCEPTION, message, errors);
	}
}
