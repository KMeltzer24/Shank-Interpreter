package com.shank.interpreter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import com.shank.nodes.FunctionNode;

/**
 * Takes a file as input and lexes, parses and interprets a Shank program
 * @author Kevin Meltzer
 * @version 2.0
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
		ArrayList<String> lines = null;
		try {		
			lines = new ArrayList<>(Files.readAllLines(arguments.toPath()));
		} catch (IOException E) {											// Catches when the file inputed is not found
			System.out.println("File \"" + args[0] + "\" not found.");
			System.exit(1);
		}
		ArrayList<Token> lexerResult = new ArrayList<>();
		// Defines the lexer, parser and interpreter
		Lexer lexer = new Lexer();
		Parser parser;
		SemanticAnalysis SemanticAnalysis;
		Interpreter interpreter = new Interpreter();
		ArrayList<FunctionNode> list = new ArrayList<>();
		
		// If the input file is empty, throws an empty file exception
		if(lines.isEmpty()) {
			System.out.println("Empty file inputed");
			System.exit(1);
		}
		
		// Lexes, parses and interprets the arrayList of function definitions.
		// Also catches an exception if thrown by Lexer, parser or interpreter class
		try {
			for(int i = 0; i < lines.size(); i++) {
				lexerResult.addAll(lexer.lex(lines.get(i)));
			}
			for (int i = 0; i < lexer.lastIdentationLevel; i++) {
				lexerResult.add(new Token(Token.state.DEDENT, " Dedent"));
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
				Interpreter.InterpretFunction((FunctionNode)Interpreter.hashGet("start"), new ArrayList<>());
			} catch (Exception E){
				throw new Exception("start function not found");
			}
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}
}
