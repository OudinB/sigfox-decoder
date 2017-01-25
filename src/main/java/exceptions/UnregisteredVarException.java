package exceptions;

public class UnregisteredVarException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public UnregisteredVarException (String name) {
		super("Unregistred var: " + name);
	}
}