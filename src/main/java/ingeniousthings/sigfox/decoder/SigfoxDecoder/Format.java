package SigfoxDecoder;

public class Format {
	VarType type;
	String nom;
	int deb;
	int fin;
	
	public Format() {
		type = null;
		nom = null;
		deb = -1;
		fin = -1;
	}
	public Format(VarType type, String nom, int deb, int fin) {
		this.type = type;
		this.nom = nom;
		this.deb = deb;
		this.fin = fin;
	}

	public VarType getType() {
		return type;
	}
	public void setType(VarType type) {
		this.type = type;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getDeb() {
		return deb;
	}
	public void setDeb(int deb) {
		this.deb = deb;
	}
	public int getFin() {
		return fin;
	}
	public void setFin(int fin) {
		this.fin = fin;
	}

}
