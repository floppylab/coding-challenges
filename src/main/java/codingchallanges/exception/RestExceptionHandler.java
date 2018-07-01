package codingchallanges.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Catches all the ClientExceptions and turns them into an ApiError response entity.
     * 
     * @param ex exception
     * @param request current request
     * @return response with error message
     */
    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ApiError> handleAll(ClientException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getClientMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
