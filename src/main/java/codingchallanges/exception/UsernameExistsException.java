package codingchallanges.exception;

import lombok.Getter;

@Getter
public class UsernameExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 0L;

	private String errorMessage;
	
	public UsernameExistsException(String message) {
		this.errorMessage = message;
	}

}
