package ingeniousthings.sigfox.decoder.SigfoxDecoder;

public enum VarType {
	BOOLEAN,	//0 : bool		|	deb = fin
	BYTE,		//1 : byte		|	deb = fin
	INTEGER,	//2 : int		|	convertir de hexa vers base 10
	FLOAT,		//3 : float		|	idem
	DOUBLE,		//4 : double	|	idem
	CHAR,		//5 : char		|	conversion de l'hexa vers l'ASCII
	STRING,		//6 : str		|	idem
}
