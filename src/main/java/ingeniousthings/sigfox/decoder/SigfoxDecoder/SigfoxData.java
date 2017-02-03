package ingeniousthings.sigfox.decoder.SigfoxDecoder;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import exceptions.UnregisteredVarException;

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
	
	public void JSONStore(PrintWriter file) throws FileNotFoundException, UnsupportedEncodingException {
	    file.println(this.toString());
	    file.close();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		JSONObject jsonObj = new JSONObject();
		
		for(Data elem : this.data)
			jsonObj.put(elem.getName(), elem.getVar());
		
		return jsonObj.toString();
	}
}
