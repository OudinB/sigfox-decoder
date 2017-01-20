package ingeniousthings.sigfox.decoder.SigfoxDecoder;

import java.math.BigInteger;

import exceptions.SyntaxError;
import exceptions.UnknownType;

public class SigfoxDecoder {
	
	private Format[] format;
	
	public SigfoxDecoder() { }
	public SigfoxDecoder(Format[] format) {
		this.format = new Format[format.length];
		this.setFormat(format);
	}
	public SigfoxDecoder(String formatStr) throws SyntaxError, UnknownType {
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
	
	public Format[] getFormat() {
		return format;
	}
	public void setFormatAt(int i, Format format) {
		this.format[i] = new Format(format.getType(), format.getName(), format.getBegin(), format.getEnd());
	}
	public void setFormat(Format[] format) {
		for(int i=0;i<format.length;++i) {
			this.format[i] = new Format(format[i].getType(), format[i].getName(), format[i].getBegin(), format[i].getEnd());
		}
	}
	
	private VarType typeVar(String typeStr) throws UnknownType {
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
				type = VarType.STRING;
				break;
			default :
				throw new UnknownType();
		}
		return type;
	}
	
	private String makeString(String bTrame) {
		String res = new String();
		
		for(int i=0;i<bTrame.length()-7;i+=8)
			res += (char) Integer.parseInt(bTrame.substring(i, i+8), 2);
		
		return res;
	}
	
	public SigfoxData decode(String trame) throws UnknownType {
		BigInteger n = new BigInteger("1" + trame, 16);
		String bTrame = n.toString(2).substring(1);
		SigfoxData data = new SigfoxData(format.length);
		
		for(int i=0;i<format.length;++i) {
			switch(format[i].getType()) {
			case BOOLEAN :
				data.add(i, format[i].getName(), (Boolean) (bTrame.charAt(format[i].getBegin()) == '1'));
				break;
			case BYTE :
				data.add(i, format[i].getName(), bTrame.charAt(format[i].getBegin()));
				break;
			case INTEGER :
				data.add(i, format[i].getName(), Integer.parseInt(bTrame.substring(format[i].getBegin(), format[i].getEnd()+1), 2));
				break;
			case FLOAT :
				data.add(i, format[i].getName(), Float.parseFloat(bTrame.substring(format[i].getBegin(), format[i].getEnd())));
				break;
			case DOUBLE :
				data.add(i, format[i].getName(), Double.parseDouble(bTrame.substring(format[i].getBegin(), format[i].getEnd())));
				break;
			case CHAR :
				Character c = (char) Integer.parseInt(bTrame.substring(format[i].getBegin(), format[i].getBegin()+8), 2);
				data.add(i, format[i].getName(), c);
				break;
			case STRING :
				data.add(i, format[i].getName(), makeString(bTrame.substring(format[i].getBegin(), format[i].getEnd()+1)));
				break;
			default :
				throw new UnknownType();
			}
		}
		return data;
	}

	public void display() {
		for(int i=0;i<format.length;++i)
			System.out.println("Var n°" + i + " : \"" + format[i].getName()
					+ "\" of type " + format[i].getType()
					+ " occupying bits from " + format[i].getBegin()
					+ " to " + format[i].getEnd());
	}

}
