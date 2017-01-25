package exceptions;

public class UnknownTypeException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public UnknownTypeException(String type) {
		super("Unknown type: " + type);
	}
}
