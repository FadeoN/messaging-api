package messaging.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import messaging.exceptions.BaseException;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestApiError {

	private int httpStatus;
	private BaseException exception;

	@Builder.Default
	@JsonFormat (pattern = "YYYY-MM-dd HH:mm")
	private LocalDateTime timestamp = LocalDateTime.now();

}
