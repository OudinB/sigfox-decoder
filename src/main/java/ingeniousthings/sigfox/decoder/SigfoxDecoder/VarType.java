package ingeniousthings.sigfox.decoder.SigfoxDecoder;

import exceptions.UnknownTypeException;

public enum VarType {
	BOOLEAN,
	BYTE,
	INTEGER,
	FLOAT,
	DOUBLE,
	CHAR,
	STRING;
	
	public static VarType toVarType(String typeStr) throws UnknownTypeException {
		VarType type;
		
		switch(typeStr) {
			case "bool" :
				type = BOOLEAN;
				break;
			case "byte" :
				type = BYTE;
				break;
			case "int" :
				type = INTEGER;
				break;
			case "float" :
				type = FLOAT;
				break;
			case "double" :
				type = DOUBLE;
				break;
			case "char" :
				type = CHAR;
				break;
			case "string" :
				type = STRING;
				break;
			default :
				throw new UnknownTypeException(typeStr);
		}
		return type;
	}
	
	@Override
	public String toString() {
		String typeStr = "";
		
		switch(this) {
		case BOOLEAN :
			typeStr = "bool";
			break;
		case BYTE :
			typeStr = "byte";
			break;
		case INTEGER :
			typeStr = "int";
			break;
		case FLOAT :
			typeStr = "float";
			break;
		case DOUBLE :
			typeStr = "double";
			break;
		case CHAR :
			typeStr = "char";
			break;
		case STRING :
			typeStr = "string";
			break;
		}
		return typeStr;
	}
	
}
