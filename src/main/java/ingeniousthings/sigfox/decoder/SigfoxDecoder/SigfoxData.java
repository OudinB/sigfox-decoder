package ingeniousthings.sigfox.decoder.SigfoxDecoder;

import java.util.*;

import exceptions.UnregisteredVar;

public class SigfoxData {
	private List<Data> data;
	
	public SigfoxData() {
		this.data = new ArrayList<Data>();
	}
	public SigfoxData(int length) {
		this.data = new ArrayList<Data>(length);
	}
	public SigfoxData(List<Data> data) {
		this.data = new ArrayList<Data>(data.size());
		Collections.copy(this.data, data);
	}
	
	public List<Data> getData() {
		return data;
	}
	
	public void add(int i, String name, Object var) {
		data.add(i, (Data) new Data(name, var));
	}
	
	private int find(String name) {
		int i = 0;
		while(i < data.size() && !data.get(i).getName().equals(name))
			i++;
		return i;
	}
	
	public boolean getBool(String name) throws UnregisteredVar {
		int i = find(name);
		if(i != -1)
			return (boolean) data.get(i).getVar();
		else
			throw new UnregisteredVar();
	}
	public byte getByte(String name) throws UnregisteredVar {
		int i = find(name);
		if(i != -1)
			return (byte) data.get(i).getVar();
		else
			throw new UnregisteredVar();
	}
	public int getInt(String name) throws UnregisteredVar {
		int i = find(name);
		if(i < this.data.size())
			return (int) data.get(i).getVar();
		else
			throw new UnregisteredVar();
	}
	public float getFloat(String name) throws UnregisteredVar {
		int i = find(name);
		if(i != -1)
			return (int) data.get(i).getVar();
		else
			throw new UnregisteredVar();
	}
	public double getDouble(String name) throws UnregisteredVar {
		int i = find(name);
		if(i != -1)
			return (double) data.get(i).getVar();
		else
			throw new UnregisteredVar();
	}
	public char getChar(String name) throws UnregisteredVar {
		int i = find(name);
		if(i != -1)
			return (char) data.get(i).getVar();
		else
			throw new UnregisteredVar();
	}
	public String getString(String name) throws UnregisteredVar {
		int i = find(name);
		if(i != -1)
			return (String) data.get(i).getVar();
		else
			throw new UnregisteredVar();
	}
	
	public void display() {
		for(int i=0;i<data.size();++i)
			System.out.println("Var n°" + i + " : " + data.get(i).getName()	+ " = " + data.get(i).getVar());
	}
}
