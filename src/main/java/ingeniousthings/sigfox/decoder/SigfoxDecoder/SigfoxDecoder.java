package ingeniousthings.sigfox.decoder.SigfoxDecoder;

import ingeniousthings.sigfox.decoder.Exception.*;

public class SigfoxDecoder {
	
	private Format[] format;
	
	public SigfoxDecoder() { }
	public SigfoxDecoder(Format[] format) {
		this.format = new Format[format.length];
		this.setFormat(format);
	}
	public SigfoxDecoder(String formatStr) {
		format = new Format[countVar(formatStr)];
		if(format.length > 0) {
			int i = 0;		//itérateur sur formatStr
			int j = 0;		//itérateur sur format
			while(i < formatStr.length()) {
				try {
					i = intervalVar(i, j, formatStr);
					i = typeVar(i, j, formatStr);
					i = nameVar(i, j, formatStr);
					j++;
				}
				catch(UnknownType ut) {
					if(i < formatStr.length()) {
						i = formatStr.indexOf(' ', i) + 1;
						if(i == 0)
							i = formatStr.length();
						else
							j++;
					}
				}
				catch(SyntaxError se) {
					i = formatStr.indexOf(' ', i) + 1;
					if(i == 0)
						i = formatStr.length();
					
					format[j].setType(null);
					format[j].setName(null);
					format[j].setBegin(-1);
					format[j++].setEnd(-1);
				}
				catch(Exception e) {
					i = formatStr.length();
				}
			}
		}
	}
	
	public Format[] getFormat() {
		return format;
	}
	public void setFormatAt(int i, Format format) {
		this.format[i].setType(format.getType());
		this.format[i].setBegin(format.getBegin());
		this.format[i].setEnd(format.getEnd());
	}
	public void setFormat(Format[] format) {
		for(int i=0;i<format.length;++i) {
			this.format[i].setType(format[i].getType());
			this.format[i].setBegin(format[i].getBegin());
			this.format[i].setEnd(format[i].getEnd());
		}
	}
	
	private int countVar(String formatStr) {
		int i = 0;
		int length = formatStr.length();
		int count = 0;
		
		while(i < length) {
			while(i < length && formatStr.charAt(i) != ' ')
				i++;
			if(i < length)
				i++;
			count++;
		}
		
		return count;
	}
	private int intervalVar(int i, int j, String formatStr) throws SyntaxError {
		int length = formatStr.length();
		format[j] = new Format(null,null,-1,-1);
		
		String tmpBegin = new String();
		while(i < length && Character.isDigit(formatStr.charAt(i)))
			tmpBegin = tmpBegin + formatStr.charAt(i++);

		if(i < length && formatStr.charAt(i) == '-') {
			++i;
			if(tmpBegin.length() > 0)
				format[j].setBegin(Integer.parseInt(tmpBegin));
			else
				format[j].setBegin(0);
		} else
			throw new SyntaxError();
			
		String tmpEnd = new String();
		while(i < length && Character.isDigit(formatStr.charAt(i)))
			tmpEnd = tmpEnd + formatStr.charAt(i++);
		
		if(i < length && formatStr.charAt(i) == ':') {
			i++;
			if(tmpEnd.length() > 0)
				format[j].setEnd(Integer.parseInt(tmpEnd));
			else
				format[j].setEnd(Integer.MAX_VALUE);
		} else
			throw new SyntaxError();
		
		return i;
	}
	private int typeVar(int i, int j, String formatStr) throws SyntaxError, UnknownType {
		int length = formatStr.length();

		String tmpType = new String();
		while(i < length && formatStr.charAt(i) != ' ' && formatStr.charAt(i) != ':')
			tmpType = tmpType + formatStr.charAt(i++);

		if(i < length && formatStr.charAt(i) == ':') {
			i++;
			
			switch(tmpType) {
			case "bool" :
				format[j].setType(VarType.BOOLEAN);
				break;
			case "byte" :
				format[j].setType(VarType.BYTE);
				break;
			case "int" :
				format[j].setType(VarType.INTEGER);
				break;
			case "float" :
				format[j].setType(VarType.FLOAT);
				break;
			case "double" :
				format[j].setType(VarType.DOUBLE);
				break;
			case "char" :
				format[j].setType(VarType.CHAR);
				break;
			case "str" :
				format[j].setType(VarType.STRING);
				break;
			default :
				throw new UnknownType();
			}
		} else
			throw new SyntaxError();

		return i;
	}
	private int nameVar(int i, int j, String formatStr) throws SyntaxError {
		int length = formatStr.length();
		

		String tmpName = new String();
		while(i < length && (Character.isLetter(formatStr.charAt(i)) || Character.isDigit(formatStr.charAt(i))))
			tmpName = tmpName + formatStr.charAt(i++);
		
		if(i == length || (i < length && formatStr.charAt(i) == ' ')) {
			++i;
			format[j].setName(tmpName);
		} else
			throw new SyntaxError();
		
		return i;
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
	
	public SigfoxData decode(String trame) {
		int[] bTrame = toByteStr(trame);
		SigfoxData data = new SigfoxData(format.length);
		
		for(int i=0;i<format.length;++i) {
			try {
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
			} catch(UnknownType ut) {}
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
