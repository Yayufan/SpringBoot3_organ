package tw.org.organ.exception;

public class ExistPageException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ExistPageException(String message) {
        super(message);
    }
}
