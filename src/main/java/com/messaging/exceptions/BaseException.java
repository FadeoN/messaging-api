package com.messaging.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.messaging.enums.BaseExceptionType;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode (callSuper = false)
@JsonIgnoreProperties ({"cause", "localizedMessage", "suppressed", "stackTrace"})
public class BaseException extends RuntimeException {

	private BaseExceptionType type;

	private String message;

	private List<String> errors;

}
