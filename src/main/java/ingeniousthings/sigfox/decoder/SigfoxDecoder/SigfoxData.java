package SigfoxDecoder;
import Exception.UnregisteredVar;
import SigfoxDecoder.Data;

public class SigfoxData {
	private Data[] data;
	
	public SigfoxData() {
		super();
	}
	public SigfoxData(int length) {
		super();
		data = new Data[length];
	}
	
	public Object[] getData() {
		return data;
	}
	public void setDataVarAt(int i, Object dataVar) {
		this.data[i].setVar(dataVar);
	}
	public void setDataAt(int i, Data data) {
		this.data[i] = data;
	}
	public void setData(Data[] data) {
		this.data = data;
	}
	
	public void add(int i, String nom, Object var) {
		data[i] = new Data(nom);
		data[i].setVar(var);
	}
	
	private int find(String nom) {
		int i = 0;
		while(i < data.length && data[i].getNom() != nom)
			i++;
		return i;
	}
	
	public boolean getBool(String nom) throws UnregisteredVar {
		int i = find(nom);
		if(i < data.length)
			return (boolean) data[find(nom)].getVar();
		else
			throw new UnregisteredVar();
	}
	public byte getByte(String nom) throws UnregisteredVar {
		int i = find(nom);
		if(i < data.length)
			return (byte) data[find(nom)].getVar();
		else
			throw new UnregisteredVar();
	}
	public int getInt(String nom) throws UnregisteredVar {
		int i = find(nom);
		if(i < data.length)
			return (int) data[find(nom)].getVar();
		else
			throw new UnregisteredVar();
	}
	public float getFloat(String nom) throws UnregisteredVar {
		int i = find(nom);
		if(i < data.length)
			return (float) data[find(nom)].getVar();
		else
			throw new UnregisteredVar();
	}
	public double getDouble(String nom) throws UnregisteredVar {
		int i = find(nom);
		if(i < data.length)
			return (double) data[find(nom)].getVar();
		else
			throw new UnregisteredVar();
	}
	public char getChar(String nom) throws UnregisteredVar {
		int i = find(nom);
		if(i < data.length)
			return (char) data[find(nom)].getVar();
		else
			throw new UnregisteredVar();
	}
	public String getString(String nom) throws UnregisteredVar {
		int i = find(nom);
		if(i < data.length)
			return (String) data[find(nom)].getVar();
		else
			throw new UnregisteredVar();
	}
	
	public void afficher() {
		for(int i=0;i<data.length;++i)
			System.out.println("Var n°" + i + " : " + data[i].getNom()
					+ " = " + data[i].getVar());
	}
}
