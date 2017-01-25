package exceptions;

public class SyntaxError extends Exception {
	private static final long serialVersionUID = 1L;
	public SyntaxError(){ 
		  super("Syntax error in parser");
		}
}
