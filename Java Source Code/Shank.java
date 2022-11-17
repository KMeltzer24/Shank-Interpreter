import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Takes a file as input and lexes, parses and interprets a Shank program
 * @author Kevin Meltzer
 * @version 1.9
 */
public class Shank {
	/**
	 * Takes a file as input and lexes, parses and interprets a Shank program
	 * @param args A text file
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// Checks if there is only one arg
		if(args.length == 0 || args.length > 1) {
			System.out.println("Error: Only one argument must be used");
			System.exit(1);
		}
		// Puts all lines from an input file into a arraylist
		File arguments = new File(args[0]);
		ArrayList<String> lines = new ArrayList<String>(Files.readAllLines(arguments.toPath()));
		ArrayList<Token> lexerResult = new ArrayList<Token>();
		// Defines the lexer, parser and interpreter
		Lexer lexer = new Lexer();
		Parser parser;
		SemanticAnalysis SemanticAnalysis;
		Interpreter interpreter = new Interpreter();
		ArrayList<FunctionNode> list = new ArrayList<FunctionNode>();
		
		// If the input file is empty, throws an empty file exception
		if(lines.size() == 0) {
			System.out.println("Empty file inputed");
			System.exit(1);
		}
		
		// Lexes, parses and interprets the arrayList of function definitions.
		// Also catches an exception if thrown by Lexer, parser or interpreter class
		try {
			for(int i = 0; i < lines.size(); i++) {
				lexerResult.addAll(lexer.lex(lines.get(i)));
			}
			try {
				parser = new Parser(lexerResult);
				list = parser.parse();
			} catch (Exception e) {
				System.out.println(e);
				System.exit(1);
			}
			if (list == null) {
				throw new Exception("Nothing to compile");
			}
			SemanticAnalysis = new SemanticAnalysis(list);
			SemanticAnalysis.CheckStatements();
			for (int x = 0; x < list.size(); x++) {
				interpreter.putHashmap(list.get(x).getFunctionName(), list.get(x));
			}
			try {
				interpreter.InterpretFunction((FunctionNode)interpreter.hashGet("start"), new ArrayList<InterpreterDataType>());
			} catch (Exception E){
				throw new Exception("start function not found");
			}
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}
}
