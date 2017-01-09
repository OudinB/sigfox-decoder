package SigfoxDecoder;
import SigfoxDecoder.SigfoxData;
import Exception.SyntaxError;
import Exception.UnknownType;
import SigfoxDecoder.Format;

public class SigfoxDecoder {
	
	private Format[] format;
	
	public SigfoxDecoder() {
		super();
	}
	public SigfoxDecoder(Format[] format) {
		super();
		this.format = new Format[format.length];
		this.setFormat(format);
	}
	public SigfoxDecoder(String formatStr) {
		super();
		
		format = new Format[nmbVar(formatStr)];
		if(format.length > 0) {
			int i = 0;		//itérateur sur formatStr
			int j = 0;		//itérateur sur format
			while(i < formatStr.length()) {
				try {
					i = intervalVar(i, j, formatStr);
					i = typeVar(i, j, formatStr);
					i = nomVar(i, j, formatStr);
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
					format[j].setNom(null);
					format[j].setDeb(-1);
					format[j++].setFin(-1);
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
		this.format[i].type = format.type;
		this.format[i].deb = format.deb;
		this.format[i].fin = format.fin;
	}
	public void setFormat(Format[] format) {
		for(int i=0;i<format.length;++i) {
			this.format[i].type = format[i].type;
			this.format[i].deb = format[i].deb;
			this.format[i].fin = format[i].fin;
		}
	}
	
	private int nmbVar(String formatStr) {
		int i = 0;
		int length = formatStr.length();
		int nmb = 0;
		
		while(i < length) {
			while(i < length && formatStr.charAt(i) != ' ')
				i++;
			if(i < length)
				i++;
			nmb++;
		}
		
		return nmb;
	}
	private int intervalVar(int i, int j, String formatStr) throws SyntaxError {
		int length = formatStr.length();
		format[j] = new Format();
		
		String tmpDeb = new String();
		while(i < length && Character.isDigit(formatStr.charAt(i)))
			tmpDeb = tmpDeb + formatStr.charAt(i++);

		if(i < length && formatStr.charAt(i) == '-') {
			++i;
			if(tmpDeb.length() > 0)
				format[j].setDeb(Integer.parseInt(tmpDeb));
			else
				format[j].setDeb(0);
		} else
			throw new SyntaxError();
			
		String tmpFin = new String();
		while(i < length && Character.isDigit(formatStr.charAt(i)))
			tmpFin = tmpFin + formatStr.charAt(i++);
		
		if(i < length && formatStr.charAt(i) == ':') {
			i++;
			if(tmpFin.length() > 0)
				format[j].setFin(Integer.parseInt(tmpFin));
			else
				format[j].setFin(Integer.MAX_VALUE);
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
				format[j].setType(VarType.Boolean);
				break;
			case "byte" :
				format[j].setType(VarType.Byte);
				break;
			case "int" :
				format[j].setType(VarType.Integer);
				break;
			case "float" :
				format[j].setType(VarType.Float);
				break;
			case "double" :
				format[j].setType(VarType.Double);
				break;
			case "char" :
				format[j].setType(VarType.Char);
				break;
			case "str" :
				format[j].setType(VarType.String);
				break;
			default :
				throw new UnknownType();
			}
		} else
			throw new SyntaxError();

		return i;
	}
	private int nomVar(int i, int j, String formatStr) throws SyntaxError {
		int length = formatStr.length();
		

		String tmpNom = new String();
		while(i < length && (Character.isLetter(formatStr.charAt(i)) || Character.isDigit(formatStr.charAt(i))))
			tmpNom = tmpNom + formatStr.charAt(i++);
		
		if(i == length || (i < length && formatStr.charAt(i) == ' ')) {
			++i;
			format[j].setNom(tmpNom);
		} else
			throw new SyntaxError();
		
		return i;
	}
	
	private int makeInt(int[] byteCh, int deb, int fin) {
		int res = 0;
		for(int i=0;i<fin+1-deb;++i)
			res += byteCh[fin-i]*Math.pow(2,i);
		return res;
	}
	private float makeFloat(int[] byteCh, int deb, int fin) {
		float res = 0;
		for(int i=0;i<fin-deb;++i)
			res += byteCh[fin-i]*Math.pow(2,i);
		
		return res;
	}
	private double makeDouble(int[] byteCh, int deb, int fin) {
		double res = 0;
		for(int i=0;i<fin-deb;++i)
			res += byteCh[fin-i]*Math.pow(2,i);
		
		return res;
	}
	private char makeChar(int[] byteCh, int deb) {
		return (char) makeInt(byteCh, deb, deb+7);
	}
	private String makeString(int[] byteCh, int deb, int fin) {
		String res = new String();

		for(int i=0;i<(fin+1-deb)/8;++i)
			res += makeChar(byteCh, deb+i*8);
		
		return res;
	}
	
	private static int[] toByteCh(String s) {
		int[] byteCh = new int[s.length()*4];
		for(int i=0;i<s.length();++i) {
			int tmp = 0;

			if(Character.isDigit(s.charAt(i)))
				tmp = Integer.parseInt(s.substring(i, i+1));
			else if(Character.isLetter(s.charAt(i)))
				if(Character.isUpperCase(s.charAt(i)))
					tmp = 10 + s.charAt(i) - 'A';
				else
					tmp = 10 + s.charAt(i) - 'a';
				
			byteCh[i*4+3] = tmp%2;
			byteCh[i*4+2] = ((int) tmp/2)%2;
			byteCh[i*4+1] = ((int) ((int) tmp/2)/2)%2;
			byteCh[i*4] = ((int) ((int) ((int) tmp/2)/2)/2)%2;;
		}
	    return byteCh;
	}
	
	public SigfoxData decode(String trame) {
		int[] bTrame = toByteCh(trame);
		SigfoxData data = new SigfoxData(format.length);
		
		for(int i=0;i<format.length;++i) {
			try {
				switch(format[i].type) {
				case Boolean :
					data.add(i, format[i].getNom(), (Boolean) (bTrame[format[i].getDeb()] == 1));
					break;
				case Byte :
					data.add(i, format[i].getNom(), bTrame[format[i].getDeb()]);
					break;
				case Integer :
					data.add(i, format[i].getNom(), (Integer) makeInt(bTrame, format[i].getDeb(), format[i].getFin()));
					break;
				case Float :
					data.add(i, format[i].getNom(), (Float) makeFloat(bTrame, format[i].getDeb(), format[i].getFin()));
					break;
				case Double :
					data.add(i, format[i].getNom(), (Double) makeDouble(bTrame, format[i].getDeb(), format[i].getFin()));
					break;
				case Char :
					data.add(i, format[i].getNom(), (Character) makeChar(bTrame, format[i].getDeb()));
					break;
				case String :
					data.add(i, format[i].getNom(), makeString(bTrame, format[i].getDeb(), format[i].getFin()));
					break;
				default :
					throw new UnknownType();
				}
			} catch(UnknownType ut) {}
		}
		return data;
	}

	public void afficher() {
		for(int i=0;i<format.length;++i)
			System.out.println("Var n°" + i + " : \"" + format[i].getNom()
					+ "\" de type " + format[i].getType()
					+ " sur les bits " + format[i].getDeb()
					+ " à " + format[i].getFin());
	}

}
