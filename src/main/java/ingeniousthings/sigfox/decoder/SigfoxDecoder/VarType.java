package SigfoxDecoder;

public enum VarType {
	Boolean,	//0 : bool		|	deb = fin
	Byte,		//1 : byte		|	deb = fin
	Integer,	//2 : int		|	convertir de hexa vers base 10
	Float,		//3 : float		|	idem
	Double,		//4 : double	|	idem
	Char,		//5 : char		|	conversion de l'hexa vers l'ASCII
	String,		//6 : str		|	idem
}
