package ingeniousthings.sigfox.decoder.SigfoxDecoder;
import exceptions.*;

public class Main {
	
	public static void main(String[] args) throws SyntaxError, UnknownTypeException {
			SigfoxDecoder decoder = new SigfoxDecoder("0-7:char:char 0-47:str:str 48-63:int:int1 64-95:int:int2");
			SigfoxData data = decoder.decode("41424344454601234567890A");
			//SigfoxDecoder decoder = new SigfoxDecoder("0-3:int:temperature");
	        //SigfoxData data = decoder.decode("c101013030320e0c03ed8000");
	        
			System.out.println(decoder);
			System.out.println(data);
			
	}

}
