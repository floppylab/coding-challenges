package codingchallanges.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiError implements Serializable {

	private static final long serialVersionUID = 1L;

	private HttpStatus status;
	private String message;

	public ApiError(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

}

