package ingeniousthings.sigfox.decoder.SigfoxDecoder;

import ingeniousthings.sigfox.decoder.Exception.SyntaxError;
import ingeniousthings.sigfox.decoder.Exception.UnknownType;
import ingeniousthings.sigfox.decoder.Exception.UnregisteredVar;

public class Main {
	
	public static void main(String[] args) {
		try {
			//SigfoxDecoder decoder = new SigfoxDecoder("0-47:str:str 48-63:int:int1 64-95:int:int2");
			//SigfoxData data = decoder.decode("41424344454601234567890A");
			SigfoxDecoder decoder = new SigfoxDecoder("0-3:int:temperature");
	        SigfoxData data = decoder.decode("c101013030320e0c03ed8000");
	        
			decoder.display();
			data.display();
			
			//assert(data.getString("str") == "ABCDEF");
			//assert(data.getInt("int1") == 291);
			//assert(data.getInt("int2") == 1164413194);
		} catch (/*UnregisteredVar | */SyntaxError | UnknownType e) {
			e.printStackTrace();
		}
	}

}
