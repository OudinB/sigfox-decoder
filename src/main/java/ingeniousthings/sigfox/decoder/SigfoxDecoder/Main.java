package ingeniousthings.sigfox.decoder.SigfoxDecoder;
import exceptions.*;

public class Main {
	
	public static void main(String[] args) {
		try {
			SigfoxDecoder decoder = new SigfoxDecoder("0-7:char:char 0-47:str:str 48-63:int:int1 64-95:int:int2");
			SigfoxData data = decoder.decode("41424344454601234567890A");
			//SigfoxDecoder decoder = new SigfoxDecoder("0-3:int:temperature");
	        //SigfoxData data = decoder.decode("c101013030320e0c03ed8000");
	        
			decoder.display();
			data.display();
			
			System.out.println(data.get("char"));
			System.out.println(data.get("str"));
			System.out.println(data.get("int1"));
			System.out.println(data.get("int2"));
			//System.out.println(data.get("unregistered_element"));
			//assert(data.get("str") == "ABCDEF");
			//assert(data.get("int1") == 291);
			//assert(data.get("int2") == 1164413194);
		} catch (UnregisteredVarException e) {
			System.err.println("Unregistered variable exception");
			e.printStackTrace();
		} catch (SyntaxErrorException e) {
			System.err.println("Syntax error in parser");
			e.printStackTrace();
		} catch (UnknownTypeException e) {
			System.err.println("Unknown variable type exception");
			e.printStackTrace();
		}
	}

}
