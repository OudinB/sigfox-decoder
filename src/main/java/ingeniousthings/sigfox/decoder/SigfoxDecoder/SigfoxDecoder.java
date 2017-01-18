package ingeniousthings.sigfox.decoder.SigfoxDecoder;

import ingeniousthings.sigfox.decoder.Exception.*;

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
	
	private int makeInt(int[] byteStr, int begin, int end) {
		int res = 0;
		for(int i=0;i<end+1-begin;++i)
			res += byteStr[end-i]*Math.pow(2,i);
		return res;
	}
	private float makeFloat(int[] byteStr, int begin, int end) {
		float res = 0;
		for(int i=0;i<end-begin;++i)
			res += byteStr[end-i]*Math.pow(2,i);
		
		return res;
	}
	private double makeDouble(int[] byteStr, int begin, int end) {
		double res = 0;
		for(int i=0;i<end-begin;++i)
			res += byteStr[end-i]*Math.pow(2,i);
		
		return res;
	}
	private char makeChar(int[] byteCh, int begin) {
		return (char) makeInt(byteCh, begin, begin+7);
	}
	private String makeString(int[] byteStr, int begin, int end) {
		String res = new String();

		for(int i=0;i<(end+1-begin)/8;++i)
			res += makeChar(byteStr, begin+i*8);
		
		return res;
	}
	
	private static int[] toByteStr(String s) {
		int[] byteStr = new int[s.length()*4];
		for(int i=0;i<s.length();++i) {
			int tmp = 0;

			if(Character.isDigit(s.charAt(i)))
				tmp = Integer.parseInt(s.substring(i, i+1));
			else if(Character.isLetter(s.charAt(i)))
				if(Character.isUpperCase(s.charAt(i)))
					tmp = 10 + s.charAt(i) - 'A';
				else
					tmp = 10 + s.charAt(i) - 'a';
				
			byteStr[i*4+3] = tmp%2;
			byteStr[i*4+2] = ((int) tmp/2)%2;
			byteStr[i*4+1] = ((int) ((int) tmp/2)/2)%2;
			byteStr[i*4] = ((int) ((int) ((int) tmp/2)/2)/2)%2;;
		}
	    return byteStr;
	}
	
	public SigfoxData decode(String trame) throws UnknownType {
		int[] bTrame = toByteStr(trame);
		SigfoxData data = new SigfoxData(format.length);
		
		for(int i=0;i<format.length;++i) {
			switch(format[i].getType()) {
			case BOOLEAN :
				data.add(i, format[i].getName(), (Boolean) (bTrame[format[i].getBegin()] == 1));
				break;
			case BYTE :
				data.add(i, format[i].getName(), bTrame[format[i].getBegin()]);
				break;
			case INTEGER :
				data.add(i, format[i].getName(), (Integer) makeInt(bTrame, format[i].getBegin(), format[i].getEnd()));
				break;
			case FLOAT :
				data.add(i, format[i].getName(), (Float) makeFloat(bTrame, format[i].getBegin(), format[i].getEnd()));
				break;
			case DOUBLE :
				data.add(i, format[i].getName(), (Double) makeDouble(bTrame, format[i].getBegin(), format[i].getEnd()));
				break;
			case CHAR :
				data.add(i, format[i].getName(), (Character) makeChar(bTrame, format[i].getBegin()));
				break;
			case STRING :
				data.add(i, format[i].getName(), makeString(bTrame, format[i].getBegin(), format[i].getEnd()));
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
