package exceptions;

public class CommandError extends Exception {
	private static final long serialVersionUID = 1L;
	public CommandError(){ 
		super("Syntax error in the Command Line\nShould be used like this : java -cp sygfox-decoder.jar --format myFormat.json --out decoded.json");
	}
}
