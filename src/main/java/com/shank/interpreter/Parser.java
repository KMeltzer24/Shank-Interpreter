package com.shank.interpreter;

import java.util.ArrayList;

import com.shank.nodes.AssignmentNode;
import com.shank.nodes.BoolNode;
import com.shank.nodes.BooleanExpressionNode;
import com.shank.nodes.CharNode;
import com.shank.nodes.ElseNode;
import com.shank.nodes.FloatNode;
import com.shank.nodes.ForNode;
import com.shank.nodes.FunctionCallNode;
import com.shank.nodes.FunctionNode;
import com.shank.nodes.IfNode;
import com.shank.nodes.IntegerNode;
import com.shank.nodes.MathOpNode;
import com.shank.nodes.Node;
import com.shank.nodes.ParameterNode;
import com.shank.nodes.RepeatNode;
import com.shank.nodes.StatementNode;
import com.shank.nodes.StringNode;
import com.shank.nodes.VariableNode;
import com.shank.nodes.VariableReferenceNode;
import com.shank.nodes.WhileNode;

/**
 * A parser which creates a AST out of tokens from a Shank program
 * @author Kevin Meltzer
 * @version 2.0
 */
public class Parser {
	
	/**
	 * A list of tokens representing a math equation or a function
	 */
	private final ArrayList<Token> list;
	
	/**
	 * Create a Parser object 
	 * @param list A ArrayList of tokens representing a math equation or a function
	 */
	public Parser(ArrayList<Token> list) {
		this.list = list;
	}
	
	/**
	 * Creates a AST out of a A ArrayList of tokens representing a Shank program
	 * @return A list of AST nodes
	 * @throws Exception
	 */
	public ArrayList<FunctionNode> parse() throws Exception {
		return functionDefinitions();
	}
	
	/**
	 * Creates an MathOpNode by using recursive descent
	 * @return A MathOpNode
	 * @throws Exception
	 */
	public Node expression() throws Exception {
		// Gets a term which is set to left
		Node left = term();
		while(true) {
			if (null == list.get(0).getState()) {
				return null;
			} else switch (list.get(0).getState()) {
				case PLUS -> {
					matchAndRemove("PLUS");
					left =  new MathOpNode(MathOpNode.operator.PLUS, left, term());
				}
				case MINUS -> {
					matchAndRemove("MINUS");
					left =  new MathOpNode(MathOpNode.operator.MINUS, left, term());
				}
				case GREATER -> {
					matchAndRemove("GREATER");
					left =  new BooleanExpressionNode(left, MathOpNode.operator.GREATER.toString(), term());
				}
				case LESS -> {
					matchAndRemove("LESS");
					left =  new BooleanExpressionNode(left, MathOpNode.operator.LESS.toString(), term());
				}
				case GREATEROREQUAL -> {
					matchAndRemove("GREATEROREQUAL");
					left =  new BooleanExpressionNode(left, MathOpNode.operator.GREATEROREQUAL.toString(), term());
				}
				case LESSOREQUAL -> {
					matchAndRemove("LESSOREQUAL");
					left =  new BooleanExpressionNode(left, MathOpNode.operator.LESSOREQUAL.toString(), term());
				}
				case EQUAL -> {
					matchAndRemove("EQUAL");
					left =  new BooleanExpressionNode(left, MathOpNode.operator.EQUAL.toString(), term());
				}
				case NOTEQUAL -> {
					matchAndRemove("NOTEQUAL");
					left =  new BooleanExpressionNode(left, MathOpNode.operator.NOTEQUAL.toString(), term());
				}
				default -> {
					if (left instanceof BoolNode) {
						left =  new BooleanExpressionNode(left, null, null);
					}
					return left;
				}
            }
		}
	}
		
	/**
	 * Creates an * or / MathOpNode by using recursive descent
	 * @return A node to become the child of a expression
	 * @throws Exception
	 */
	public Node term() throws Exception {
		// Gets a term which is set to left
		Node left = factor();
		// Checks if the next token is times or divides, if it is,
		// then it creates a new node with the corresponding operator, 
		// left and sets the right as a call of factor()
		while(true) {
			if (null == list.get(0).getState()) {
				return null;
			} else switch (list.get(0).getState()) {
				case TIMES -> {
					matchAndRemove("TIMES");
					left =  new MathOpNode(MathOpNode.operator.TIMES, left, factor());
					}
				case DIVIDES -> {
					matchAndRemove("DIVIDES");
					left =  new MathOpNode(MathOpNode.operator.DIVIDES, left, factor());
					}
				case MOD -> {
					matchAndRemove("MOD");
					left =  new MathOpNode(MathOpNode.operator.MOD, left, factor());
					}
				default -> {
					return left;
					}
			}
		}
	}
	
	/**
	 * Creates a expression node by retrieving from 
	 * the token list
	 * @return a integer, float, string, char, boolean or MathOp node
	 * @throws Exception
	 */
	public Node factor() throws Exception {
		// Checks if the current token is an identifier
		// If it is, create and return a VariableReferenceNode
		Token token = matchAndRemove("IDENTIFIER");
		if(token == null) {
			token = matchAndRemove("LPAREN");
		} else {
			return new VariableReferenceNode(token.getValue());	
		}
		
		// If token equals null,
		// this matches and removes a number
		// from the list and create an Integer 
		// or float node
		if (token == null) {
			if ((token = matchAndRemove("TRUE")) != null || (token = matchAndRemove("FALSE")) != null) {
				return new BoolNode(Boolean.parseBoolean(token.getValue()));		
			} 
			if ((token = matchAndRemove("STRINGCONTENTS")) != null) {
				return new StringNode(token.getValue());		
			} else if ((token = matchAndRemove("CHARCONTENTS")) != null) {
				return new CharNode(token.getValue().charAt(0));
			}
			token = matchAndRemove("NUMBER");
			nullTester(token);
			try {
				return new IntegerNode(Integer.parseInt(token.getValue()));		
			} catch (NumberFormatException e) {
				return new FloatNode(Float.valueOf(token.getValue()));	
			}
		} else {
			// If a left parenthesis is matched and removed,
			// call expression() and matchAndRemove a right parenthesis
			// and then return the MathOpNode
			Node node = expression();
			if (!"PLUSMINUSTIMESDIVIDESMOD".contains(((MathOpNode)node).getState().toString())) {
				throw new Exception("Not a vaild math expression");
			}
			matchAndRemove("RPAREN");
			return node;
		}
	}
	

	/**
	 * Parses function definitions
	 * @return A list of function nodes
	 * @throws Exception
	 */
	public ArrayList<FunctionNode> functionDefinitions() throws Exception {
		ArrayList<FunctionNode> functionNodeList = new ArrayList<>();
		// Remove all endofline tokens until a not endofline token is found
		removeEndOfLines();
		// If the token is not defined, return null
		// if not, while the token is defined,  call FunctionDefinition and add a function
		// to the list
		if (!(list.get(0).getState().equals(Token.state.DEFINE))) {
			return null;
		}
		while(list.get(0).getState().equals(Token.state.DEFINE)) {
			functionNodeList.add(functionDefinition());
			if (list.isEmpty()) {
				break;
			}
			removeEndOfLines();
		}
		if (!list.isEmpty()) {
			throw new Exception("Invalid dedent");
		}
		return functionNodeList;
	}
	
	/**
	 * Parses a function definition
	 * @return A FunctionNode
	 * @throws Exception
	 */
	public FunctionNode functionDefinition() throws Exception {
		// list to hold function parameters
		ArrayList<VariableNode> param = new ArrayList<>();
		// list to hold local variables
		ArrayList<VariableNode> localVars = new ArrayList<>();
		// list to hold names of the variables
		ArrayList<Token> names = new ArrayList<>();
		// list to hold whether or not a variable has var in front of it
		ArrayList<Boolean> varList = new ArrayList<>();
		
		
		removeEndOfLines();
		nullTester(matchAndRemove("DEFINE"));
		Token functName = matchAndRemove("IDENTIFIER");
		nullTester(functName);
		nullTester(matchAndRemove("LPAREN"));
		if (matchAndRemove("RPAREN") == null) {
			// Gets functions parameters and puts them into a list
			do {
				Token ident;
				do {
					if (matchAndRemove("VAR") == null) {
						varList.add(false);
					} else {
						varList.add(true);
					}
					ident = matchAndRemove("IDENTIFIER");
					nullTester(ident);
					names.add(ident);
				} while (!(matchAndRemove("COMMA") == null));
				nullTester(matchAndRemove("COLON"));
				Token dataType = null;
				switch (list.get(0).getState()) {
					case INTEGER -> {
						dataType = matchAndRemove("INTEGER");
						while (!(names.isEmpty())) {
							param.add(new VariableNode(names.get(0).toString(), VariableNode.dataType.INTEGER, new IntegerNode(), varList.get(0)));
							names.remove(0);
							varList.remove(0);
						}
					}
					case REAL -> {
						dataType = matchAndRemove("REAL");
						while (!(names.isEmpty())) {
							param.add(new VariableNode(names.get(0).toString(), VariableNode.dataType.REAL, new FloatNode(), varList.get(0)));
							names.remove(0);
							varList.remove(0);
						}
					}
					case STRING -> {
						dataType = matchAndRemove("STRING");
						while (!(names.isEmpty())) {
							param.add(new VariableNode(names.get(0).toString(), VariableNode.dataType.STRING, new StringNode(), varList.get(0)));
							names.remove(0);
							varList.remove(0);
						}
					}
					case CHAR -> {
						dataType = matchAndRemove("CHAR");
						while (!(names.isEmpty())) {
							param.add(new VariableNode(names.get(0).toString(), VariableNode.dataType.CHAR, new CharNode(), varList.get(0)));
							names.remove(0);
							varList.remove(0);
						}
					}
					case BOOLEAN -> {
						dataType = matchAndRemove("BOOLEAN");
						while (!(names.isEmpty())) {
							param.add(new VariableNode(names.get(0).toString(), VariableNode.dataType.BOOLEAN, new BoolNode(), varList.get(0)));
							names.remove(0);
							varList.remove(0);
						}
					}
					default -> throw new IllegalArgumentException("Unexpected value: " + list.get(0).getState());
				}
				nullTester(dataType);
			} while (!(matchAndRemove("SEMICOLON") == null));
			nullTester(matchAndRemove("RPAREN"));
		}
		removeEndOfLines();
		// If this function has constants, it gets the constants and puts them into a list
		while (!(matchAndRemove("CONSTANTS") == null)) {
			localVars.addAll(constants());
			removeEndOfLines();
		}
		// If this function has variables, it gets the variables and puts them into a list
		while (!(matchAndRemove("VARIABLES") == null)) {
			localVars.addAll(variables());
			removeEndOfLines();
		}
		return new FunctionNode(functName.toString(), param, localVars, statements());
	}
	
	/**
	 * Gets a variable node of a constant
	 * @return A constant variable node
	 * @throws Exception
	 */
	public ArrayList<VariableNode> constants() throws Exception {
		ArrayList<VariableNode> constants = new ArrayList<>();
		do {
			Token ident = matchAndRemove("IDENTIFIER");
			nullTester(ident);
			String name = ident.toString();
			nullTester(matchAndRemove("EQUAL"));
			Token token = matchAndRemove("NUMBER");
			if (token != null) {
				Node numNode;
				String numOnly;
				nullTester(token);
				// If the constant is a real, creates a variable node with a real inside and returns it
				if (token.toString().contains(".")) {
					numOnly = token.toString().replaceAll("[^\\d.]", ""); 
					numNode = new FloatNode(Float.valueOf(numOnly));				
					constants.add(new VariableNode(name, VariableNode.dataType.REAL, numNode, true));
				// If the constant is a Int, creates a variable node with a int inside and returns it	
				} else {
					numOnly = token.toString().replaceAll("[^0-9]", "");
					numNode = new IntegerNode(Integer.parseInt(numOnly));	
					constants.add(new VariableNode(name, VariableNode.dataType.INTEGER, numNode, true));
				}
			} else {
				switch (list.get(0).getState()) {
					case TRUE -> {
						token = matchAndRemove("TRUE");
						constants.add(new VariableNode(name, VariableNode.dataType.BOOLEAN, new BoolNode(true), true));
					}
					case FALSE -> {
						token = matchAndRemove("FALSE");
						constants.add(new VariableNode(name, VariableNode.dataType.BOOLEAN, new BoolNode(false), true));
					}
					case STRINGCONTENTS -> {
						token = matchAndRemove("STRINGCONTENTS");
						constants.add(new VariableNode(name, VariableNode.dataType.STRING, new StringNode(token.toString()), true));
					}
					case CHARCONTENTS -> {
						token = matchAndRemove("CHARCONTENTS");
						constants.add(new VariableNode(name, VariableNode.dataType.CHAR, new CharNode(token.toString().charAt(0)), true));
					}
					default -> {}
				}
				nullTester(token);
			}
		} while (matchAndRemove("COMMA") != null);
		nullTester(matchAndRemove("ENDOFLINE"));
		return constants;
	}
	
	/**
	 * Gets the variables and puts them in a list of variable nodes
	 * @return An ArrayList of variable nodes
	 * @throws Exception
	 */
	public ArrayList<VariableNode> variables() throws Exception {
		ArrayList<VariableNode> variables = new ArrayList<>();
		ArrayList<Token> names = new ArrayList<>();
		Token ident;
		ident = matchAndRemove("IDENTIFIER");
		
		// Gets variables names and puts them into a list
		while (!(matchAndRemove("COMMA") == null)) {
			nullTester(ident);
			names.add(ident);
			ident = matchAndRemove("IDENTIFIER");
		}
		nullTester(ident);
		names.add(ident);
		nullTester(matchAndRemove("COLON"));
		switch (list.get(0).getState()) {
			case INTEGER -> {
				matchAndRemove("INTEGER");
				while (!(names.isEmpty())) {
					variables.add(new VariableNode(names.get(0).toString(), VariableNode.dataType.INTEGER, new IntegerNode(), false));
					names.remove(0);
				}
			}
			case REAL -> {
				matchAndRemove("REAL");
				while (!(names.isEmpty())) {
					variables.add(new VariableNode(names.get(0).toString(), VariableNode.dataType.REAL, new FloatNode(), false));
					names.remove(0);
				}
			}
			case STRING -> {
				matchAndRemove("STRING");
				while (!(names.isEmpty())) {
					variables.add(new VariableNode(names.get(0).toString(), VariableNode.dataType.STRING, new StringNode(), false));
					names.remove(0);
				}
			}
			case CHAR -> {
				matchAndRemove("CHAR");
				while (!(names.isEmpty())) {
					variables.add(new VariableNode(names.get(0).toString(), VariableNode.dataType.CHAR, new CharNode(), false));
					names.remove(0);
				}
			}
			case BOOLEAN -> {
				matchAndRemove("BOOLEAN");
				while (!(names.isEmpty())) {
					variables.add(new VariableNode(names.get(0).toString(), VariableNode.dataType.BOOLEAN, new BoolNode(), false));
					names.remove(0);
				}
			}
			default -> {}
		}
		return variables;
	}
	
	/**
	 * Gets statements and returns them in a arrayList
	 * @return A arrayList with StatementNodes
	 * @throws Exception
	 */
	public ArrayList<StatementNode> statements() throws Exception {
		removeEndOfLines();
		nullTester(matchAndRemove("INDENT"));
		ArrayList<StatementNode> StatementNodeList = new ArrayList<>();
		// Add new statement nodes to a list if the next token
		// is an identifier, or a reserved word
		while(list.get(0).getState().equals(Token.state.IDENTIFIER) || list.get(0).getState().equals(Token.state.WHILE) || list.get(0).getState().equals(Token.state.IF) || list.get(0).getState().equals(Token.state.FOR) || list.get(0).getState().equals(Token.state.REPEAT)) {
			StatementNodeList.add(statement());
			removeEndOfLines();
		}
		nullTester(matchAndRemove("DEDENT"));
		return StatementNodeList;
	}
	
	/**
	 * Creates a statement Node
	 * @return A statement Node
	 * @throws Exception
	 */
	public StatementNode statement() throws Exception {
		if (list.get(0).getState().equals(Token.state.WHILE)) {
			return While();
		} else if (list.get(0).getState().equals(Token.state.IF)) {
			return If();
		} else if (list.get(0).getState().equals(Token.state.FOR)) {
			return For();
		} else if (list.get(0).getState().equals(Token.state.REPEAT)) {
			return repeat();
		} else if (list.get(0).getState().equals(Token.state.IDENTIFIER) && peek().getState().equals(Token.state.ASSIGNMENT)) {
			return assignment();
		} else {
			return functionCall();
		}
	}
	
	/**
	 * Creates an assignment node
	 * @return An assignment node
	 * @throws Exception
	 */
	public AssignmentNode assignment() throws Exception {
		// Checks if the next token is an assignment
		if (peek().getState().equals(Token.state.ASSIGNMENT)) {
			VariableReferenceNode target = new VariableReferenceNode(matchAndRemove("IDENTIFIER").toString());
			matchAndRemove("ASSIGNMENT");
			Node expression = expression(); 
			
			// if the expression is null, throw a exception
			// if it is not null, make and return a new assignment node
			if (expression == null) {
				throw new Exception("Cannot assign null to a variable");
			}
			if (matchAndRemove("ENDOFLINE") != null) {
				return new AssignmentNode(target,expression);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * Creates a while Node
	 * @return A while Node
	 * @throws Exception 
	 */
	public WhileNode While() throws Exception {		
		// Checks for while token, and returns 
		// null if it doesn't find it
		if (matchAndRemove("WHILE") == null) {
			return null;
		}
		BooleanExpressionNode booleanExp = (BooleanExpressionNode)expression();
		nullTester(matchAndRemove("ENDOFLINE"));
		if (booleanExp == null) {
			throw new Exception("Cannot have a while loop without a boolean expression");
		}
		return new WhileNode(booleanExp, statements());
	}

	/**
	 * Creates a for loop Node
	 * @return A for loop Node
	 * @throws Exception 
	 */
	public ForNode For() throws Exception {
		IntegerNode start;
		IntegerNode end;
		// Checks for a for token, and returns 
		// null if it doesn't find it
		if (matchAndRemove("FOR") == null) {
			return null;
		}
		Token ident = matchAndRemove("IDENTIFIER");
		nullTester(ident);
		VariableReferenceNode var = new VariableReferenceNode(ident.toString());
		nullTester(matchAndRemove("FROM"));
		Token startToken = matchAndRemove("NUMBER");
		nullTester(startToken);
		// Creates a intnode if the input is and int, or
		// returns null if its not
		try {
			start = new IntegerNode(Integer.parseInt(startToken.getValue()));		
		} catch (NumberFormatException e) {
			throw new Exception("Non integer starting boung in for loop");
		}
		Token to = matchAndRemove("TO");
		nullTester(to);
		Token endToken = matchAndRemove("NUMBER");
		nullTester(endToken);
		try {
			end = new IntegerNode(Integer.parseInt(endToken.getValue()));		
		} catch (NumberFormatException e) {
			throw new Exception("Non integer ending boung in for loop");
		}
		nullTester(matchAndRemove("ENDOFLINE"));
		return new ForNode(var, start, end, statements());
	}

	/**
	 * Creates a if Node
	 * @return A if Node
	 * @throws Exception 
	 */
	public IfNode If() throws Exception {
		if (matchAndRemove("ELSE") != null) {
			return new ElseNode(statements());
		}
		// Checks for a if or elsif token, and returns 
		// null if it doesn't find it
		if (matchAndRemove("IF") != null || matchAndRemove("ELSIF") != null) {
			Node node = expression();
			BooleanExpressionNode booleanExp;
			if (node instanceof VariableReferenceNode) {
				booleanExp = new BooleanExpressionNode(null, null, node);
			} else {
				booleanExp = (BooleanExpressionNode)node;
			}
			if (booleanExp == null) {
				throw new Exception("Cannot have a if node without a boolean expression");
			}
			nullTester(matchAndRemove("THEN"));
			nullTester(matchAndRemove("ENDOFLINE"));
			ArrayList<StatementNode> statements = statements();
			if (peek().getState().equals(Token.state.IF)) { 	
				return new IfNode(booleanExp,statements, null);
			}
			return new IfNode(booleanExp, statements, If());
		} else {
			return null;
		}
	}
	
	/**
	 * Creates a repeat loop Node
	 * @return A repeat loop Node
	 * @throws Exception 
	 */
	public RepeatNode repeat() throws Exception {
		if (matchAndRemove("REPEAT") == null) {
			return null;
		}
		nullTester(matchAndRemove("ENDOFLINE"));
		ArrayList<StatementNode> statements = statements();
		removeEndOfLines();
		nullTester(matchAndRemove("UNTIL"));
		BooleanExpressionNode booleanExp = (BooleanExpressionNode)expression();
		nullTester(matchAndRemove("ENDOFLINE"));
		if (booleanExp == null) {
			throw new Exception("Cannot have a repeat node without a boolean expression");
		}
		return new RepeatNode(booleanExp, statements);
	}
	
	/**
	 * Creates a function call Node
	 * @return A function call Node
	 * @throws Exception 
	 */
	public FunctionCallNode functionCall() throws Exception {
		ArrayList<ParameterNode> paramList = new ArrayList<>();
		boolean var;
		Token functionName = matchAndRemove("IDENTIFIER");
		Token param;
		nullTester(functionName);
		if (matchAndRemove("ENDOFLINE") == null) {
			do {
                var = matchAndRemove("VAR") != null;
				param = matchAndRemove("NUMBER");
				if (param != null){
					if (var == true) {
						throw new Exception("Cannot have var before a number");
					}
					try {
						paramList.add(new ParameterNode(new IntegerNode(Integer.parseInt(param.getValue())), var));
						continue;
					} catch (NumberFormatException E) {
						paramList.add(new ParameterNode(new FloatNode(Float.valueOf(param.getValue())), var));
						continue;
					}
				}
				param = matchAndRemove("STRINGCONTENTS");
				if (param != null) {
					if (var == true) {
						throw new Exception("Cannot have var before a string");
					}
					paramList.add(new ParameterNode(new StringNode(param.getValue()), var));
					continue;
				}
				param = matchAndRemove("CHARCONTENTS");
				if (param != null) {
					if (var == true) {
						throw new Exception("Cannot have var before a char");
					}
					paramList.add(new ParameterNode(new CharNode(param.getValue().charAt(0)), var));
					continue;
				}
				param = matchAndRemove("TRUE");
				if (param != null) {
					if (var == true) {
						throw new Exception("Cannot have var before a boolean value");
					}
					paramList.add(new ParameterNode(new BoolNode(Boolean.parseBoolean(param.getValue())), var));
					continue;
				}
				param = matchAndRemove("FALSE");
				if (param != null) {
					if (var == true) {
						throw new Exception("Cannot have var before a boolean value");
					}
					paramList.add(new ParameterNode(new BoolNode(Boolean.parseBoolean(param.getValue())), var));
					continue;
				}
				param = matchAndRemove("IDENTIFIER");
				nullTester(param);
				paramList.add(new ParameterNode(new VariableReferenceNode(param.getValue()), var));
			} while (matchAndRemove("COMMA") != null);
			nullTester(matchAndRemove("ENDOFLINE"));
		}
		return new FunctionCallNode(functionName.toString(), paramList);
	}
	
	/**
	 * if the next token matches the inputed type then return it
	 * or return null
	 * @param state Desired state of the token
	 * @return A token or null
	 * @throws Exception
	 */
	public Token matchAndRemove(String state) throws Exception {
		// Checks if the state of the token equals the desired state
		if (list.get(0).getState().equals(Token.state.valueOf(state))) {
			Token temp = list.get(0);
			list.remove(0);
			return temp;
		} else {
			return null;
		}
	}
	
	/**
	 * Tests if the current token is null,
	 * if it is, throws an exception
	 * @param token A token which is tested
	 * @throws Exception
	 */
	public void nullTester(Token token) throws Exception {
		if (token == null) {
			System.out.println(list.get(0));
			System.out.println(list.get(1));
			System.out.println(list.get(2));
			throw new Exception("Not valid syntax");
		}
	}
	
	/**
	 * Gets the next token from the list
	 * @return The next token from the list
	 */
	public Token peek() {
		return list.get(1);
	}
	
	/**
	 * Removes a empty line token and checks for empty lines in the file
	 * @throws Exception
	 */
    @SuppressWarnings("empty-statement")
	public void removeEndOfLines() throws Exception {
		while (!(matchAndRemove("ENDOFLINE") == null));
	}
}
