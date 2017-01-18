package ingeniousthings.sigfox.decoder.Exception;

public class UnregisteredVar extends Exception {
	private static final long serialVersionUID = 1L;

	public UnregisteredVar() {
		System.err.println("Variable non enregistrée : vérifiez l'orthographe.");
		//à remplacer par prinstack et à ecrire en anglais
	}
}
