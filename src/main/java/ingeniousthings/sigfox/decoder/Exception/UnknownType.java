package Exception;

public class UnknownType extends Exception {
	private static final long serialVersionUID = 1L;

	public UnknownType() {
		System.err.println("Type inconnu passé dans le backend !");
	}
}
