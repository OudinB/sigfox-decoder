package ingeniousthings.sigfox.decoder.SigfoxDecoder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import exceptions.SyntaxError;
import exceptions.UnknownTypeException;

public class SigfoxDecoder {
	
	private Format[] format;
	
	public SigfoxDecoder() { }
	public SigfoxDecoder(Format[] format) throws SyntaxError {
		this.format = new Format[format.length];
		this.setFormat(format);
	}
	public SigfoxDecoder(String formatStr) throws SyntaxError, UnknownTypeException {
		String[] lsVar = formatStr.split(" ");
		format = new Format[lsVar.length];
		
		for(int i=0; i<lsVar.length; ++i) {
			String[] formatTmp = lsVar[i].split(":");
			
			if(formatTmp.length == 3) {
				VarType type = typeVar(formatTmp[1]);
				String[] interval = formatTmp[0].split("-");

				format[i] = new Format(type, formatTmp[2], Integer.parseInt(interval[0]), Integer.parseInt(interval[1]));
			} else
				throw new SyntaxError();
		}
	}
	public SigfoxDecoder(String param, FileReader file) throws ParseException, FileNotFoundException, IOException, SyntaxError, UnknownTypeException {
		if(param.equals("message") || param.equals("config")) {
			JSONParser parser = new JSONParser();
			
			JSONObject jsonObj = (JSONObject) parser.parse(file);
			JSONObject jsonMess = (JSONObject) parser.parse(jsonObj.get(param).toString());
			JSONArray jsonArray = (JSONArray) parser.parse(jsonMess.get("format").toString());
			
			format = new Format[jsonArray.size()];
			
			for(int i=0; i<jsonArray.size(); ++i) {
				JSONObject jsonElem = (JSONObject) parser.parse(jsonArray.get(i).toString());
				
				VarType type = typeVar((String) jsonElem.get("type"));
				Integer start = jsonElem.get("start") != null ? ((Long) jsonElem.get("start")).intValue() : null;
				Integer end = jsonElem.get("end") != null ? ((Long) jsonElem.get("end")).intValue() : null;
				String name = (String) jsonElem.get("name");
				format[i] = new Format(type, name, start, end);
			}
		} else
			throw new SyntaxError();
	}
	
	public Format[] getFormat() {
		return format;
	}
	public void setFormatAt(int i, Format format) throws SyntaxError {
		this.format[i] = new Format(format.getType(), format.getName(), format.getStart(), format.getEnd());
	}
	public void setFormat(Format[] format) throws SyntaxError {
		for(int i=0;i<format.length;++i) {
			this.format[i] = new Format(format[i].getType(), format[i].getName(), format[i].getStart(), format[i].getEnd());
		}
	}
	
	private VarType typeVar(String typeStr) throws UnknownTypeException {
		VarType type;
		switch(typeStr) {
			case "bool" :
				type = VarType.BOOLEAN;
				break;
			case "byte" :
				type = VarType.BYTE;
				break;
			case "int" :
				type = VarType.INTEGER;
				break;
			case "float" :
				type = VarType.FLOAT;
				break;
			case "double" :
				type = VarType.DOUBLE;
				break;
			case "char" :
				type = VarType.CHAR;
				break;
			case "str" :
			case "string" :
				type = VarType.STRING;
				break;
			default :
				throw new UnknownTypeException(typeStr);
		}
		return type;
	}
	
	private String makeString(String bTrame) {
		String res = new String();
		
		for(int i=0;i<bTrame.length()-7;i+=8)
			res += (char) Integer.parseInt(bTrame.substring(i, i+8), 2);
		
		return res;
	}
	
	public SigfoxData decode(String trame) {
		BigInteger n = new BigInteger("1" + trame, 16);
		String bTrame = n.toString(2).substring(1);
		SigfoxData data = new SigfoxData();
		
		for(int i=0;i<format.length;++i) {
			switch(format[i].getType()) {
			case BOOLEAN :
				data.add(i, format[i].getName(), (Boolean) (bTrame.charAt(format[i].getStart()) == '1'));
				break;
			case BYTE :
				data.add(i, format[i].getName(), bTrame.charAt(format[i].getStart()));
				break;
			case INTEGER :
				data.add(i, format[i].getName(), Integer.parseInt(bTrame.substring(format[i].getStart(), format[i].getEnd()+1), 2));
				break;
			case FLOAT :
				data.add(i, format[i].getName(), Float.parseFloat(bTrame.substring(format[i].getStart(), format[i].getEnd())));
				break;
			case DOUBLE :
				data.add(i, format[i].getName(), Double.parseDouble(bTrame.substring(format[i].getStart(), format[i].getEnd())));
				break;
			case CHAR :
				Character c = (char) Integer.parseInt(bTrame.substring(format[i].getStart(), format[i].getStart()+8), 2);
				data.add(i, format[i].getName(), c);
				break;
			case STRING :
				data.add(i, format[i].getName(), makeString(bTrame.substring(format[i].getStart(), format[i].getEnd()+1)));
				break;
			}
		}
		return data;
	}
	
	@Override
	public String toString() {
		String str = "";
		for(int i=0;i<format.length;++i)
			str += "Var#" + i + ": \"" + format[i].getName()
					+ "\" of type " + format[i].getType()
					+ " occupying bits " + format[i].getStart()
					+ " to " + format[i].getEnd() + "\n";
		
		return str;
	}

}
