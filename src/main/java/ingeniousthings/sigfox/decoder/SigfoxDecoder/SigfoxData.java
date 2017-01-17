package ingeniousthings.sigfox.decoder.SigfoxDecoder;

import ingeniousthings.sigfox.decoder.Exception.UnregisteredVar;

public class SigfoxData {
	private Data[] data;
	
	public SigfoxData() { }
	public SigfoxData(int length) {
		data = new Data[length];
	}
	
	public Object[] getData() {
		return data;
	}
	public void setDataAt(int i, Data data) {
		this.data[i] = data;
	}
	public void setData(Data[] data) {
		this.data = data;
	}
	
	public void add(int i, String name, Object var) {
		data[i] = new Data(name, var);
	}
	
	private int find(String name) {
		int i = 0;
		while(i < data.length && data[i].getName() != name)
			i++;
		return i;
	}
	
	public boolean getBool(String name) throws UnregisteredVar {
		int i = find(name);
		if(i < data.length)
			return (boolean) data[find(name)].getVar();
		else
			throw new UnregisteredVar();
	}
	public byte getByte(String name) throws UnregisteredVar {
		int i = find(name);
		if(i < data.length)
			return (byte) data[find(name)].getVar();
		else
			throw new UnregisteredVar();
	}
	public int getInt(String name) throws UnregisteredVar {
		int i = find(name);
		if(i < data.length)
			return (int) data[find(name)].getVar();
		else
			throw new UnregisteredVar();
	}
	public float getFloat(String name) throws UnregisteredVar {
		int i = find(name);
		if(i < data.length)
			return (float) data[find(name)].getVar();
		else
			throw new UnregisteredVar();
	}
	public double getDouble(String name) throws UnregisteredVar {
		int i = find(name);
		if(i < data.length)
			return (double) data[find(name)].getVar();
		else
			throw new UnregisteredVar();
	}
	public char getChar(String name) throws UnregisteredVar {
		int i = find(name);
		if(i < data.length)
			return (char) data[find(name)].getVar();
		else
			throw new UnregisteredVar();
	}
	public String getString(String name) throws UnregisteredVar {
		int i = find(name);
		if(i < data.length)
			return (String) data[find(name)].getVar();
		else
			throw new UnregisteredVar();
	}
	
	public void display() {
		for(int i=0;i<data.length;++i)
			System.out.println("Var n°" + i + " : " + data[i].getName()
					+ " = " + data[i].getVar());
	}
}
