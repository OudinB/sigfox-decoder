package ingeniousthings.sigfox.decoder.Exception;

public class UnregisteredVar extends Exception {
	private static final long serialVersionUID = 1L;

	public UnregisteredVar() {
		System.err.println("Variable non enregistr�e : v�rifiez l'orthographe.");
		//� remplacer par prinstack et � ecrire en anglais
	}
}
