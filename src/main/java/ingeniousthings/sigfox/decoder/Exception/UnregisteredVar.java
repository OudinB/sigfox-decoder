package Exception;

public class UnregisteredVar extends Exception {
	private static final long serialVersionUID = 1L;

	public UnregisteredVar() {
		System.err.println("Variable non enregistrée : vérifiez l'orthographe.");
	}
}
