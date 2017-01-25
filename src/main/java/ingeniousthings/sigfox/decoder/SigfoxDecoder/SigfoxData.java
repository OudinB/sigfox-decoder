package ingeniousthings.sigfox.decoder.SigfoxDecoder;

import java.util.*;
import exceptions.*;

public class SigfoxData {
	private List<Data> data = new ArrayList<Data>();;
	
	public SigfoxData() { }
	public SigfoxData(List<Data> data) {
		this.data.addAll(data);
	}
	
	public List<Data> getData() {
		return data;
	}
	
	public void add(int i, String name, Object var) {
		data.add(i, new Data(name, var));
	}
	
	public Object get(String name) throws UnregisteredVarException {
		int i = data.indexOf(new Data(name));
		if(i != -1)
			return data.get(i).getVar();
		else
			throw new UnregisteredVarException(name);
	}
	
	@Override
	public String toString() {
		String str = "";
		for(int i=0;i<data.size();++i)
			str += "Var n°" + i + ": " + data.get(i).getName()	+ " = " + data.get(i).getVar() + "\n";
		
		return str;
	}
}
