package ingeniousthings.sigfox.decoder.SigfoxDecoder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import exceptions.CommandError;
import exceptions.SyntaxError;
import exceptions.UnknownTypeException;

public class Main {
	public static boolean option(String[] args) {
		List<String> argsLs = new ArrayList<String>();
		for(String elem : args)
			argsLs.add(elem);
		return argsLs.contains("--format") && argsLs.contains("--out")&& args.length == 4;
	}
	public static String JSONTarget(String option, String[] args) {
		List<String> argsLs = new ArrayList<String>();
		for(String elem : args)
			argsLs.add(elem);
			
		return args[argsLs.indexOf(option)+1];
	}
	
	public static void main(String[] args) throws SyntaxError, UnknownTypeException, ParseException, FileNotFoundException, IOException, CommandError {
		if(option(args)) {
			SigfoxDecoder decoder = new SigfoxDecoder("message", new FileReader("./" + JSONTarget("--format", args)));
			SigfoxData data = decoder.decode("c414243444546");
	    		
			System.out.println(decoder);
			System.out.println(data);
			
			data.JSONStore(new PrintWriter("./" + JSONTarget("--out", args)));
		} else
			throw new CommandError();
	}

}
