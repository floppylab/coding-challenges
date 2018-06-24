package codingchallanges.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ClientException.class)
	public ResponseEntity<Object> handleAll(ClientException ex, WebRequest request) {
	    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getClientMessage());
	    return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}
