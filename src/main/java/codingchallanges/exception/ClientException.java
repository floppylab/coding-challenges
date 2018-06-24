package codingchallanges.exception;

public class ClientException extends RuntimeException {

	protected static final long serialVersionUID = 0L;
	private static final String ERROR_TEXT = "general exception, reason unknown";
	
	protected String message;
	
	public ClientException() {
		this.message = ERROR_TEXT;
	}
	
	public ClientException(String message) {
		this.message = message;
	}
	
	public String getClientMessage() {
		return message;
	}

}
