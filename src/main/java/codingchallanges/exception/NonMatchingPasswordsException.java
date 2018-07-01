package codingchallanges.exception;

import lombok.Getter;

@Getter
public class NonMatchingPasswordsException extends RuntimeException {

    private static final long serialVersionUID = 0L;

    private String errorMessage;

    /**
     * Initializes the exception with the given message.
     * 
     * @param message message
     */
    public NonMatchingPasswordsException(String message) {
        this.errorMessage = message;
    }

}
