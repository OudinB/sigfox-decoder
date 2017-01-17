package ingeniousthings.sigfox.decoder.Exception;

public class SyntaxError extends Exception {
	private static final long serialVersionUID = 1L;

	public SyntaxError() {
		System.err.println("Erreur de syntaxe dans le backend !");
	}
}
