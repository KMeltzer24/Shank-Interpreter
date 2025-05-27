package com.shank.interpreter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Takes in a Shank program separates it into tokens
 * @author Kevin Meltzer
 * @version 2.0
 */
public class Lexer { 
	
	// Keeps track of the indentation level of the previous line
	private boolean inComment = false;
	// Keeps track of the indentation level of the previous line
	public int lastIdentationLevel = 0;

	/**
	 * Takes in a Shank program separates it into tokens
	 * @param str A line of a Shank program
	 * @return An ArrayList of tokens
	 * @throws Exception
	 */
	public ArrayList<Token> lex(String str) throws Exception {
		
		// creates the ArrayList which will be returned
		ArrayList<Token> tokenList = new ArrayList<>();
		
		// splits the input string into a array character by character
		String[] arr = str.split("");
		String value = "";
		
		// The index of the last non space character
		int x;
		
		// The amount of each parenthesis inputed
		int lparenNum = 0;
		int rparenNum = 0;
		
		// Creates a hashmap of reserved words
		HashMap<String, Token.state> resWords = new HashMap<>();
		resWords.put("integer", Token.state.INTEGER);
		resWords.put("real", Token.state.REAL);
		resWords.put("variables", Token.state.VARIABLES);
		resWords.put("constants", Token.state.CONSTANTS);
		resWords.put("define", Token.state.DEFINE);
		resWords.put("if", Token.state.IF);
		resWords.put("then", Token.state.THEN);
		resWords.put("else", Token.state.ELSE);
		resWords.put("elsif", Token.state.ELSIF);
		resWords.put("for", Token.state.FOR);
		resWords.put("from", Token.state.FROM);
		resWords.put("to", Token.state.TO);
		resWords.put("while", Token.state.WHILE);
		resWords.put("repeat", Token.state.REPEAT);
		resWords.put("until", Token.state.UNTIL);
		resWords.put("mod", Token.state.MOD);
		resWords.put("var", Token.state.VAR);
		resWords.put("true", Token.state.TRUE);
		resWords.put("false", Token.state.FALSE);
		resWords.put("string", Token.state.STRING);
		resWords.put("character", Token.state.CHAR);
		resWords.put("boolean", Token.state.BOOLEAN);
		
		// Options for valid characters
		String options = ".+-*/();:=,<>'\"{}";
		int commIndex = 0;

		// If the input string is empty create a token with the value of EndOfLine
		if (str.length() == 0) { 
			tokenList.add(new Token(Token.state.ENDOFLINE, " EndOfLine\n"));
			return tokenList;
		// If currently inside of a multiline comment, go into comment mode
		} else if (inComment == true) {
			commIndex = 0;
			while (!(arr[commIndex].equals("}"))) {		
				commIndex++;
				if (commIndex == str.length()) {
					tokenList.add(new Token(Token.state.ENDOFLINE, " EndOfLine\n"));
					return tokenList;
				}
			}
			inComment = false;

			// If the comment is the last character of a line,
			// add a endofline token to the list
			if (commIndex == str.length()-1) {
				tokenList.add(new Token(Token.state.ENDOFLINE, " EndOfLine\n"));
			}
			commIndex++;
		}

		// Creates dedent tokens if the line starts at indentation level 0
		if (!(arr[0].equals(" ") || arr[0].equals("\t")) && lastIdentationLevel != 0) { 
			int prevLvl = lastIdentationLevel;
			for (int i = 0; i < prevLvl; i++) {
				tokenList.add(new Token(Token.state.DEDENT, " Dedent"));
				lastIdentationLevel--;
			}
		}

		// Iterates through the array with the contents of the input string
		for (int i = commIndex; i < str.length(); i++) {
			Token currentToken = new Token();
			x=i-1;

			// checks if the current value of the array is a space or tab
			if (arr[i].equals(" ") || arr[i].equals("\t")) {
				// Checks indents/"empty" lines
				if (tokenList.isEmpty()) {
					// if line is "empty", returns a end of line token
					int emptyChkInt = 0;
					while (arr[emptyChkInt].equals(" ") || arr[emptyChkInt].equals("\t")) {
						emptyChkInt++;
						if (emptyChkInt == str.length()) {
							tokenList.add(new Token(Token.state.ENDOFLINE, " EndOfLine\n"));
							return tokenList;
						}
					}
					
					// counts the number of indents in a line
					int spaces = 0;
					int tabs = 0;
					while (arr[i].equals(" ") || arr[i].equals("\t")) {
						if (arr[i].equals(" ")) spaces++; else tabs++;
						i++;
					}
					i--;
					tabs += spaces/4;
					tabs -= lastIdentationLevel; 
					// if the indentation level is less than the previous line, output dedent tokens
					if (tabs < 0) {
						for (int p = 0; p < tabs*-1; p++) {
							tokenList.add(new Token(Token.state.DEDENT, " Dedent"));
							lastIdentationLevel--;
						}
					// if the indentation level is greater than the previous line, output indent tokens
					} else {
						for (int p = 0; p < tabs; p++) {
							tokenList.add(new Token(Token.state.INDENT, " Indent"));
							lastIdentationLevel++;
						}
					}
				// gets the next character that is not a space
				} else if (value.length() != 0) {								
					while (arr[i].equals(" ") || arr[i].equals("\t")) {
						i++;
					}
					// If there is a space between 2 numbers an exception is thrown
					if (Character.isDigit(arr[i].charAt(0)) || arr[i].equals(".")) {
						throw new Exception("Invalid space between 2 numbers");
					}
					i--;
				}	
			} else {
				// Checks if the character in the array is a valid character and sets the corresponding state
				// if the character is valid.
				if (Character.isDigit(arr[i].charAt(0)) || Character.isLetter(arr[i].charAt(0)) || options.contains(arr[i])) {
					if (arr[i].equals(".")) {
						currentToken.setState(Token.state.DECIMAL);
					} else if (arr[i].equals("+")) {
						currentToken.setState(Token.state.PLUS);
					} else if (arr[i].equals("-")) {
						currentToken.setState(Token.state.MINUS);
					} else if (arr[i].equals("*")) {
						currentToken.setState(Token.state.TIMES);
					} else if (arr[i].equals("/")) {
						currentToken.setState(Token.state.DIVIDES);
					} else if (arr[i].equals("(")) {
						currentToken.setState(Token.state.LPAREN);
					} else if (arr[i].equals(")")) {
						currentToken.setState(Token.state.RPAREN);
					} else if (arr[i].equals("{")) {
						currentToken.setState(Token.state.LCBRACK);
					} else if (arr[i].equals("}")) {
						currentToken.setState(Token.state.RCBRACK);
					} else if (arr[i].equals(",")) {
						currentToken.setState(Token.state.COMMA);
					} else if (arr[i].equals("=")) {
						currentToken.setState(Token.state.EQUAL);
					} else if (arr[i].equals(";")) {
						currentToken.setState(Token.state.SEMICOLON);
					} else if (arr[i].equals(">")) {
						if(arr[i+1].equals("=")) {
							i++;
							currentToken.setState(Token.state.GREATEROREQUAL);
						} else {
							currentToken.setState(Token.state.GREATER);
						}
					} else if (arr[i].equals("<")) {
						switch (arr[i+1]) {
							case "=" -> {
								i++;
								currentToken.setState(Token.state.LESSOREQUAL);
							}
							case ">" -> {
								i++;
								currentToken.setState(Token.state.NOTEQUAL);
							}
							default -> currentToken.setState(Token.state.LESS);
						}
					} else if (arr[i].equals(":")) {
						// Checks for assignment sign
						if(arr[i+1].equals("=")) {
							i++;
							currentToken.setState(Token.state.ASSIGNMENT);
						} else {
							currentToken.setState(Token.state.COLON);
						}
					} else if (arr[i].equals("'")) {
						String contents = "";
						i++;
						while (!arr[i].equals("'")) {
							contents += arr[i];
							i++;
						}
						if (contents.length() > 1) {
							throw new Exception("A char variable was set to multiple characters");
						} else if (contents.length() == 0) {
							throw new Exception("A char variable is empty");
						} else {
							tokenList.add(new Token(Token.state.CHARCONTENTS, contents));
							if (i == str.length()-1) {
								tokenList.add(new Token(Token.state.ENDOFLINE, " EndOfLine\n"));
							}
						}
					} else if (arr[i].equals("\"")) {
						String contents = "";
						i++;
						while (!arr[i].equals("\"")) {
							contents += arr[i];
							i++;
						}
						if (contents.length() == 0) {
							throw new Exception("A string variable is empty");
						} else {
							tokenList.add(new Token(Token.state.STRINGCONTENTS, contents));	
							if (i == str.length()-1) {
								tokenList.add(new Token(Token.state.ENDOFLINE, " EndOfLine\n"));
							}
						}
					} else if (Character.isLetter(arr[i].charAt(0))) {
						// If this letter is the last letter of the string,
						// and a token to the list and a EndOfLine token to the list
						if (i == str.length()-1) {
							tokenList.add(new Token(Token.state.IDENTIFIER, arr[i]));
							tokenList.add(new Token(Token.state.ENDOFLINE, " EndOfLine\n"));
							break;
						}
						
						// creates a string with the word from the array
						int z = i;
						if (Character.isLetter(arr[i+1].charAt(0)) || Character.isDigit(arr[i+1].charAt(0))) {
							while(Character.isLetter(arr[i].charAt(0)) || Character.isDigit(arr[i].charAt(0))) {
								i++;
								if (i == arr.length) {
									break;
								}
							}
							i--;
							String word = arr[i];
							arr[i] = "";
							while (z < i) {
								arr[i] += arr[z];
								z++;
							}
							arr[i] += word;
						}
						// Checks if the word is reserved, if it is
						// creates a token with the reserved word
						if (resWords.containsKey((arr[i]))) {
							if (arr[i].equals("mod")) {
								currentToken.setState(Token.state.MOD);
							} else {
								if (value.length() != 0) {
									tokenList.add(new Token(Token.state.NUMBER,value));
									value = "";
								}
								if(i == str.length()-1) {
									tokenList.add(new Token(resWords.get(arr[i]), arr[i]));
									tokenList.add(new Token(Token.state.ENDOFLINE, " EndOfLine\n"));
								} else {
									tokenList.add(new Token(resWords.get(arr[i]), arr[i]));
								}
							}
						// creates a token with the identifier word
						} else {
							if(i == str.length()-1) {
								tokenList.add(new Token(Token.state.IDENTIFIER, arr[i]));
								tokenList.add(new Token(Token.state.ENDOFLINE, " EndOfLine\n"));
							} else {
								tokenList.add(new Token(Token.state.IDENTIFIER, arr[i]));
								
							}
						}
					} else {
						currentToken.setState(Token.state.NUMBER);
					}
					if(!(currentToken.getState() == null)) {
						switch(currentToken.getState()) {
							
							// if the current character is a decimal, it checks if the number
							// already has a decimal, and if it doesn't it adds it
							// to a string
							// If this decimal is the last character in the array,
							// it creates a token and adds it to a arrayList
							case DECIMAL:
								if (value.contains(".")) {
									throw new Exception("Cannot have a number with 2 decimals");
								}
								value += arr[i];
								if(i == str.length()-1) {
									tokenList.add(new Token(Token.state.NUMBER,value));
									tokenList.add(new Token(Token.state.ENDOFLINE, " EndOfLine\n"));
								}
								break;
						
							// if the current character is a plus sign, it checks
							// if this plus sign is in a correct mathematical position
							// and if it is then it adds it to a string or creates
							// a token and adds it to a arrayList
							case PLUS:
								if (value.equals(".") || value.equals("+") || value.equals("-") || i == str.length()-1) {
									throw new Exception("Invalid addition sign placement");
								} else if (i == 0) {
									value += arr[i];
									break;
								}
								
								// If the last token in the array is a space
								// this gets the last non space character
								while (arr[x].equals(" ")) {
									x--;
								}
								
								if (arr[x].equals("+") || arr[x].equals("-") || arr[x].equals("*") || arr[x].equals("/")) {
									value += arr[i];
								} else if (value.length() != 0) {
									tokenList.add(new Token(Token.state.NUMBER,value));
									tokenList.add(new Token(Token.state.PLUS,arr[i]));
									value = "";
								} else {
									tokenList.add(new Token(Token.state.PLUS,arr[i]));
								}
								break;
							
							// if the current character is a subtraction sign, it checks
							// if this subtraction sign is in a correct mathematical position
							// and if it is then it adds it to a string or creates
							// a token and adds it to a arrayList
							case MINUS: 
								if (value.equals(".") || value.equals("+") || value.equals("-") || i == str.length()-1) {
									throw new Exception("Invalid subtraction sign placement");
								} else if (i == 0) {
									value += arr[i];
									break;
								}
								
								// If the last token in the array is a space
								// this gets the last non space character
								while (arr[x].equals(" ")) {
									x--;
								}
								
								if (arr[x].equals("+") || arr[x].equals("-") || arr[x].equals("*") || arr[x].equals("/")) {
									value += arr[i];
								} else if (value.length() != 0) {
									tokenList.add(new Token(Token.state.NUMBER,value));
									tokenList.add(new Token(Token.state.MINUS,arr[i]));
									value = "";
								} else {
									tokenList.add(new Token(Token.state.MINUS,arr[i]));
								}
								break;
							
							// if the current character is a multiplication sign, it checks
							// if this multiplication sign is in a correct mathematical position
							// and if it is then creates a token and adds it to a arrayList	
							case TIMES: 
								
								// If the last token in the array is a space
								// this gets the last non space character
								while (arr[x].equals(" ")) {
									x--;
								}
								
								if (arr[x].equals(")") || tokenList.get(tokenList.size()-1).getState().equals(Token.state.IDENTIFIER)) {
									tokenList.add(new Token(Token.state.TIMES,arr[i]));
									break;
								} else if (value.length() == 0 || i == str.length()-1 || value.equals(".") || value.equals("+") || value.equals("-")) {
									throw new Exception("Invalid multiplication sign placement");
								}
								tokenList.add(new Token(Token.state.NUMBER,value));
								tokenList.add(new Token(Token.state.TIMES,arr[i]));
								value = "";
								break;
							
							// if the current character is a division sign, it checks
							// if this division sign is in a correct mathematical position
							// and if it is then creates a token and adds it to a arrayList	
							case DIVIDES: 
								
								// If the last token in the array is a space
								// this gets the last non space character
								while (arr[x].equals(" ")) {
									x--;
								}
								
								if (arr[x].equals(")") || tokenList.get(tokenList.size()-1).getState().equals(Token.state.IDENTIFIER)) {
									tokenList.add(new Token(Token.state.DIVIDES,arr[i]));
									break;
								} else if (value.length() == 0 || i == str.length()-1 || value.equals(".") || value.equals("+") || value.equals("-")) {
									throw new Exception("Invalid division sign placement");
								}
								tokenList.add(new Token(Token.state.NUMBER,value));
								tokenList.add(new Token(Token.state.DIVIDES,arr[i]));
								value = "";
								break;
								
							// if the current character is a modulo, it checks
							// if this modulo is in a correct mathematical position
							// and if it is then creates a token and adds it to a arrayList	
							case MOD: 
								
								// If the last token in the array is a space
								// this gets the last non space character
								while (arr[x].equals(" ")) {
									x--;
								}
								
								if (arr[x].equals(")") || tokenList.get(tokenList.size()-1).getState().equals(Token.state.IDENTIFIER)) {
									tokenList.add(new Token(Token.state.MOD,arr[i]));
									break;
								} else if (value.length() == 0 || i == str.length()-1 || value.equals(".") || value.equals("+") || value.equals("-")) {
									throw new Exception("Invalid mod sign placement");
								}
								tokenList.add(new Token(Token.state.NUMBER,value));
								tokenList.add(new Token(Token.state.MOD,arr[i]));
								value = "";
								break;
								
							// Adds the current character to a string
							// If it is the last character in the array
							// a token is created and added to the arrayList
							case NUMBER:
								value += arr[i];
								if(i == str.length()-1) {
									tokenList.add(new Token(Token.state.NUMBER,value));
									tokenList.add(new Token(Token.state.ENDOFLINE, " EndOfLine\n"));
								}	
								break;
							
								// if the current character is a left parenthesis, it checks
								// if this parenthesis sign is in a correct mathematical position
								// and if it is then creates a token and adds it to a arrayList		
								// and adds one to the count of left parentheses
							case LPAREN:
								if (i == str.length()-1 || value.length() != 0) {
									throw new Exception("Invalid left parenthesis placement");
								} else {
									tokenList.add(new Token(Token.state.LPAREN,arr[i]));
									lparenNum++;
								}
								break;
								
							// if the current character is a right parenthesis, it checks
							// if this parenthesis sign is in a correct mathematical position
							// and if it is then creates a token and adds it to a arrayList	
							// and adds one to the count of right parentheses
							case RPAREN:	
								if (i == 0) {
									throw new Exception("Invalid right parenthesis placement");
								}
								
								// If the last token in the array is a space
								// this gets the last non space character
								while (arr[x].equals(" ")) {
									x--;
								}
								if (arr[x].equals("+") || arr[x].equals("-") || arr[x].equals("*") || arr[x].equals("/")) {
									throw new Exception("Not a valid mathematical statment");
								} else if(value.length() != 0) {
									tokenList.add(new Token(Token.state.NUMBER,value ));
									value = "";
								}
								tokenList.add(new Token(Token.state.RPAREN,arr[i]));
								rparenNum++;
								if(i == str.length()-1) {
									tokenList.add(new Token(Token.state.ENDOFLINE, " EndOfLine\n"));	
								}
								break;

							// if the current character is a left curly bracket, it enters comment mode
							// and disregards all tokens until a right curly bracket is reached
							case LCBRACK:	
								while (!(arr[i].equals("}"))) {		
									i++;
									if (i == str.length()) {
										inComment = true;
										i--;
										break;
									}
								}

								// If the comment is the last character of a line,
								// add a endofline token to the list
								if (value.length() != 0 && i == str.length()-1) {
									tokenList.add(new Token(Token.state.NUMBER,value ));
									tokenList.add(new Token(Token.state.ENDOFLINE, " EndOfLine\n"));
								} else if (i == str.length()-1) {
									tokenList.add(new Token(Token.state.ENDOFLINE, " EndOfLine\n"));	
								}
								break;

							// if the current character is a right curly bracket, it throws
							// an exception because right curley brackets are invalid except for ending comments
							case RCBRACK:	
								throw new Exception("Invalid right curly bracket placement");

							// if the current character is a colon, it adds it to a arrayList	
							case COLON: 
								tokenList.add(new Token(Token.state.COLON,arr[i]));
								break;
							
							// if the current character is a semicolon, it adds it to a arrayList	
							case SEMICOLON:
								tokenList.add(new Token(Token.state.SEMICOLON,arr[i]));
								break;
							
							// if the current character is an equal sign, it adds it to a arrayList		
							case EQUAL:
								if (value.length() != 0) {
									tokenList.add(new Token(Token.state.NUMBER,value));
									tokenList.add(new Token(Token.state.EQUAL,arr[i]));
									value = "";
								} else {
									tokenList.add(new Token(Token.state.EQUAL,arr[i]));
								}
								break;
								
							// if the current character is a comma, it adds it to a arrayList		
							case COMMA:
								if (value.length() != 0) {
									tokenList.add(new Token(Token.state.NUMBER,value));
									tokenList.add(new Token(Token.state.COMMA,arr[i]));
									value = "";
								} else {
									tokenList.add(new Token(Token.state.COMMA,arr[i]));
								}
								break;
							
							// if the current character is an assignment sign, it adds it to a arrayList
							case ASSIGNMENT:
								tokenList.add(new Token(Token.state.ASSIGNMENT,":="));
								break;
							
							// if the current character is an assignment sign, it adds it to a arrayList
							case GREATER:
								if (value.length() != 0) {
									tokenList.add(new Token(Token.state.NUMBER,value));
									tokenList.add(new Token(Token.state.GREATER,">"));
									value = "";
								} else {
									tokenList.add(new Token(Token.state.GREATER,">"));
								}
								break;	
							
							// if the current character is an assignment sign, it adds it to a arrayList
							case LESS:
								if (value.length() != 0) {
									tokenList.add(new Token(Token.state.NUMBER,value));
									tokenList.add(new Token(Token.state.LESS,"<"));
									value = "";
								} else {
									tokenList.add(new Token(Token.state.LESS,"<"));
								}
								break;
							
							// if the current character is an assignment sign, it adds it to a arrayList
							case GREATEROREQUAL:
								if (value.length() != 0) {
									tokenList.add(new Token(Token.state.NUMBER,value));
									tokenList.add(new Token(Token.state.GREATEROREQUAL,">="));
									value = "";
								} else {
									tokenList.add(new Token(Token.state.GREATEROREQUAL,">="));
								}
								break;
							
							// if the current character is an assignment sign, it adds it to a arrayList
							case LESSOREQUAL:
								if (value.length() != 0) {
									tokenList.add(new Token(Token.state.NUMBER,value));
									tokenList.add(new Token(Token.state.LESSOREQUAL,"<="));
									value = "";
								} else {
									tokenList.add(new Token(Token.state.LESSOREQUAL,"<="));
								}
								break;
							
							// if the current character is an assignment sign, it adds it to a arrayList
							case NOTEQUAL:
								if (value.length() != 0) {
									tokenList.add(new Token(Token.state.NUMBER,value));
									tokenList.add(new Token(Token.state.NOTEQUAL,"<>"));
									value = "";
								} else {
									tokenList.add(new Token(Token.state.NOTEQUAL,"<>"));
								}
								break;
							default:
								break;
						}
					}
				} else {
					throw new Exception("Invalid character in Shank");
				}
			}
		}
		// If the number of left parentheses is not equal to the number
		// of right parenthesis, this throws an exception
		if (lparenNum != rparenNum) {
			throw new Exception("Missing one or more left or right parentheses");
		}

		// Removes indent/dedent tokens created by comments if the line is empty besides indents and comments
		if (tokenList.size() == 1) {
			return tokenList;
		} else if (tokenList.get(tokenList.size()-2).getState().equals(Token.state.INDENT)) {
			while (tokenList.get(tokenList.size()-2).getState().equals(Token.state.INDENT)) {
				tokenList.remove(tokenList.size()-2);
				lastIdentationLevel--;
				if (tokenList.size() == 1) break;
			}
		} else if (tokenList.get(tokenList.size()-2).getState().equals(Token.state.DEDENT)) {
			while (tokenList.get(tokenList.size()-2).getState().equals(Token.state.DEDENT)) {
				tokenList.remove(tokenList.size()-2);
				lastIdentationLevel++;
				if (tokenList.size() == 1) break;
			}
		}
		return tokenList;
	}
}