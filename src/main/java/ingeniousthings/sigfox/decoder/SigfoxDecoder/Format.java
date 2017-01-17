package ingeniousthings.sigfox.decoder.SigfoxDecoder;

public class Format {
	private VarType type;
	private String name;
	private int begin;
	private int end;
	
	public Format(VarType type, String name, int begin, int end) {
		this.type = type;
		this.name = name;
		this.begin = begin;
		this.end = end;
	}

	public VarType getType() {
		return type;
	}
	public void setType(VarType type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}

}
