package ingeniousthings.sigfox.decoder.SigfoxDecoder;

import exceptions.SyntaxError;

public class Format {
	private VarType type;
	private String name;
	private int start;
	private int end;
	
	public Format(VarType type, String name, Integer start, Integer end) throws SyntaxError {
		if(name != null && start != null && end != null) {
			this.type = type;
			this.name = name;
			this.start = start;
			this.end = end;
		} else
			throw new SyntaxError();
	}

	public VarType getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public int getStart() {
		return start;
	}
	public int getEnd() {
		return end;
	}

}
