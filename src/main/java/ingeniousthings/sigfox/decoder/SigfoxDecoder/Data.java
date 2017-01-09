package SigfoxDecoder;

public class Data {
	private String nom;
	private Object var;
	
	public Data() {
		super();
	}
	public Data(String nom) {
		super();
		this.nom = nom;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Object getVar() {
		return var;
	}
	public void setVar(Object var) {
		this.var = var;
	}
}
