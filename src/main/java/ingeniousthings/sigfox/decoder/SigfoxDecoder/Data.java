package ingeniousthings.sigfox.decoder.SigfoxDecoder;

public class Data {
	private String name;
	private Object var;
	
	public Data() {	}
	public Data(String name, Object var) {
		this.name = name;
		this.var = var;
	}
	
	public String getName() {
		return name;
	}
	public Object getVar() {
		return var;
	}
}
