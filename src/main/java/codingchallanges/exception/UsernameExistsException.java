package codingchallanges.exception;

import lombok.Getter;

@Getter
public class UsernameExistsException extends RuntimeException {

    private static final long serialVersionUID = 0L;

    private String errorMessage;

    /**
     * Initializes the exception with the given message.
     * 
     * @param message message
     */
    public UsernameExistsException(String message) {
        this.errorMessage = message;
    }

}
