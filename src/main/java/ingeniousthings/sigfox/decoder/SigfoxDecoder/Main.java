package SigfoxDecoder;

import Exception.UnregisteredVar;

public class Main {
	
	public static void main(String[] args) {
		String format = "0-47:str:str 48-63:int:int1 64-95:int:int2";
		SigfoxDecoder decoder = new SigfoxDecoder(format);
		//decoder.afficher();
		SigfoxData data = decoder.decode("41424344454601234567890A");
		//data.afficher();
		
		try {
			assert(data.getString("str") == "ABCDEF");
			assert(data.getInt("int1") == 291);
			assert(data.getInt("int2") == 1164413194);
		} catch (UnregisteredVar e) {
			e.printStackTrace();
		}
	}

}
