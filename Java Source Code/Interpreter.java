import java.util.ArrayList;
import java.util.HashMap;

/*
 * An Interpreter which interprets a ast
 * @author Kevin Meltzer
 * @version 1.9
 */
public class Interpreter {
	
	/**
	 * Creates a hashmap of strings and callable nodes
	 */
	private static HashMap<String, CallableNode> functMap = new HashMap<String, CallableNode>();
	
	/**
	 * Puts a inputed function into a hashmap and also adds the
	 * built in functions to the hashmap
	 * @param name The name of a function
	 * @param node A callable node
	 */
	public void putHashmap(String name, CallableNode node) {
		ArrayList<VariableNode> read = new ArrayList<VariableNode>();
		ArrayList<VariableNode> write = new ArrayList<VariableNode>();
		ArrayList<VariableNode> sqrtParam = new ArrayList<VariableNode>(){ { 
			add(new VariableNode("someFloat", VariableNode.dataType.REAL, new FloatNode(), false)); 
            add(new VariableNode("result", VariableNode.dataType.REAL, new FloatNode(), true)); }
		};
		ArrayList<VariableNode> getRandomParam = new ArrayList<VariableNode>(){ { 
            add(new VariableNode("resultInteger", VariableNode.dataType.INTEGER, new IntegerNode(), true)); }
		};
		ArrayList<VariableNode> integerToRealParam = new ArrayList<VariableNode>(){ { 
			add(new VariableNode("someInteger", VariableNode.dataType.INTEGER, new IntegerNode(), false)); 
            add(new VariableNode("someReal", VariableNode.dataType.REAL, new FloatNode(), true)); }
		};
		ArrayList<VariableNode> realToIntegerParam = new ArrayList<VariableNode>(){ { 
			add(new VariableNode("someReal", VariableNode.dataType.REAL, new FloatNode(), false)); 
            add(new VariableNode("someInt", VariableNode.dataType.INTEGER, new IntegerNode(), true)); }
		};
		ArrayList<VariableNode> LeftParam = new ArrayList<VariableNode>(){ { 
			add(new VariableNode("someString", VariableNode.dataType.STRING, new StringNode(), false)); 
			add(new VariableNode("length", VariableNode.dataType.INTEGER, new IntegerNode(), false)); 
            add(new VariableNode("resultString", VariableNode.dataType.STRING, new StringNode(), true)); }
		};
		ArrayList<VariableNode> RightParam = new ArrayList<VariableNode>(){ { 
			add(new VariableNode("someString", VariableNode.dataType.STRING, new StringNode(), false)); 
			add(new VariableNode("length", VariableNode.dataType.INTEGER, new IntegerNode(), false)); 
            add(new VariableNode("resultString", VariableNode.dataType.STRING, new StringNode(), true)); }
		};
		ArrayList<VariableNode> SubstringParam = new ArrayList<VariableNode>(){ { 
			add(new VariableNode("someString", VariableNode.dataType.STRING, new StringNode(), false)); 
			add(new VariableNode("index", VariableNode.dataType.INTEGER, new IntegerNode(), false)); 
			add(new VariableNode("length", VariableNode.dataType.INTEGER, new IntegerNode(), false)); 
	        add(new VariableNode("resultString", VariableNode.dataType.STRING, new StringNode(), true)); }
		};
		if (functMap.isEmpty()) {
			functMap.put("read", new read(null, null));
			functMap.put("write", new write(null, null));
			functMap.put("squareRoot", new squareRoot(sqrtParam, null));
			functMap.put("getRandom", new getRandom(getRandomParam, null));
			functMap.put("integerToReal", new integerToReal(integerToRealParam, null));
			functMap.put("realToInteger", new realToInteger(realToIntegerParam, null));
			functMap.put("Left", new Left(LeftParam, null));
			functMap.put("Right", new Right(RightParam, null));
			functMap.put("Substring", new Substring(SubstringParam, null));
		}
		functMap.put(name, node);
	}
	
	/**
	 * Gets a function from the hashmap
	 * @param name The name of the function
	 * @return A callable node
	 */
	public static CallableNode hashGet(String name) {
		return functMap.get(name);
	}
	
	/**
	 * Calculates the result of the expression as a integer
	 * @param node An ast node
	 * @return A int value which is the result of the expression
	 */
	public static int resolveInt(Node node, HashMap<String, InterpreterDataType> varHash) {
		if (node instanceof IntegerNode) {					// Returns the value of the node if it is a integerNode
			return ((IntegerNode)node).getInteger();
		} else if (node instanceof VariableReferenceNode) {	
			return Integer.parseInt(varHash.get(((VariableReferenceNode)node).toString()).toString());
		} else {
			if (((MathOpNode)node).getState().equals(MathOpNode.operator.PLUS)) {	//Iterates through the ast to resolve the result
				return resolveInt(((MathOpNode)node).getLeft(), varHash) + resolveInt(((MathOpNode)node).getRight(), varHash);
			} else if (((MathOpNode)node).getState().equals(MathOpNode.operator.MINUS)) {
				return resolveInt(((MathOpNode)node).getLeft(), varHash) - resolveInt(((MathOpNode)node).getRight(), varHash);
			} else if (((MathOpNode)node).getState().equals(MathOpNode.operator.TIMES)) {
				return resolveInt(((MathOpNode)node).getLeft(), varHash) * resolveInt(((MathOpNode)node).getRight(), varHash);
			} else {
				return resolveInt(((MathOpNode)node).getLeft(), varHash) / resolveInt(((MathOpNode)node).getRight(), varHash);
			}
		}
	}
	
	/**
	 * Calculates the result of the expression as a float
	 * @param node An ast node
	 * @return A Float value which is the result of the expression
	 */
	public static Float resolveFloat(Node node, HashMap<String, InterpreterDataType> varHash) {
		if (node instanceof FloatNode) {					// Returns the value of the node if it is a FloatNode
			return ((FloatNode)node).getFloat();
		} else if (node instanceof VariableReferenceNode) {	
			return Float.parseFloat(varHash.get(((VariableReferenceNode)node).toString()).toString());
		} else {
			if (((MathOpNode)node).getState().equals(MathOpNode.operator.PLUS)) {	//Iterates through the ast to resolve the result
				return resolveFloat(((MathOpNode)node).getLeft(), varHash) + resolveFloat(((MathOpNode)node).getRight(), varHash);
			} else if (((MathOpNode)node).getState().equals(MathOpNode.operator.MINUS)) {
				return resolveFloat(((MathOpNode)node).getLeft(), varHash) - resolveFloat(((MathOpNode)node).getRight(), varHash);
			} else if (((MathOpNode)node).getState().equals(MathOpNode.operator.TIMES)) {
				return resolveFloat(((MathOpNode)node).getLeft(), varHash) * resolveFloat(((MathOpNode)node).getRight(), varHash);
			} else {
				return resolveFloat(((MathOpNode)node).getLeft(), varHash) / resolveFloat(((MathOpNode)node).getRight(), varHash);
			}
		}
	}
	
	/**
	 * Calculates the result of a string expression 
	 * @param node An ast node
	 * @return A String value which is the result of the expression
	 * @throws Exception 
	 */
	public static String resolveString(Node node, HashMap<String, InterpreterDataType> varHash) throws Exception {
		if (node instanceof StringNode) {				// Returns the value of the node if it is a StringNode
			return node.toString();
		} else if (node instanceof CharNode) {			// Returns the value of the node if it is a CharNode
			return node.toString();
		} else if (node instanceof VariableReferenceNode) {	
			return varHash.get(((VariableReferenceNode)node).toString()).toString();
		} else {
			if (((MathOpNode)node).getState().equals(MathOpNode.operator.PLUS)) {	//Iterates through the ast to resolve the result
				return resolveString(((MathOpNode)node).getLeft(), varHash) + resolveString(((MathOpNode)node).getRight(), varHash);
			} else {
				throw new Exception("Operation invalid with Strings");
			}
		}
	}
	
	/**
	 * Calculates the result of a char expression 
	 * @param node An ast root node
	 * @return A Char value which is the result of the expression
	 * @throws Exception 
	 */
	public static char resolveCharacter(Node node, HashMap<String, InterpreterDataType> varHash) throws Exception {
		if (node instanceof CharNode) {			// Returns the value of the node if it is a CharNode
			return node.toString().charAt(0);
		} else if (node instanceof VariableReferenceNode) {	
			return varHash.get(((VariableReferenceNode)node).toString()).toString().charAt(0);
		} else {
			throw new Exception("Cannot do operations with characters");
		}
	}
	
	/**
	 * Calculates the result of a boolean expression 
	 * @param node An ast root node
	 * @return A Boolean value which is the result of the expression
	 * @throws Exception 
	 */
	public static boolean resolveBoolean(Node node, HashMap<String, InterpreterDataType> varHash) throws Exception {
		if (node instanceof VariableReferenceNode) {	
			return Boolean.parseBoolean(varHash.get(((VariableReferenceNode)node).toString()).toString());
		} else if (((BooleanExpressionNode)node).getRight() == null) {	
			return Boolean.parseBoolean(((BooleanExpressionNode)node).getLeft().toString());
		} else if (((BooleanExpressionNode)node).getLeft() == null) {	
			return Boolean.parseBoolean(varHash.get(((BooleanExpressionNode)node).getRight().toString()).toString());
		} else {
			return EvaluateBooleanExpression((BooleanExpressionNode)node, varHash);
		}
	}
	
	/**
	 * Evaluates a boolean expression
	 * @param node A node that represents a boolean expression
	 * @param varHash A hashmap of strings, and IDTs representing current variables
	 * @return A boolean value which represents the evaluated expression
	 * @throws Exception 
	 */
	public static boolean EvaluateBooleanExpression(BooleanExpressionNode node, HashMap<String, InterpreterDataType> varHash) throws Exception {
		
		Node left;
		Node right;
		if (node.getLeft() instanceof VariableReferenceNode) {		// Gets left node from the expression
			if (varHash.get(node.getLeft().toString()) instanceof IntDataType) {
				left = new IntegerNode(Integer.parseInt(varHash.get(node.getLeft().toString()).toString()));
			} else if (varHash.get(node.getLeft().toString()) instanceof FloatDataType) {
				left = new FloatNode(Float.parseFloat(varHash.get(node.getLeft().toString()).toString()));
			} else {
				throw new Exception("Can only evaluate a boolean expression with numbers");
			}
		} else {
			left = node.getLeft();
		}
		
		if (node.getRight() instanceof VariableReferenceNode) {		// Gets right node from the expression
			if (varHash.get(node.getRight().toString()) instanceof IntDataType) {
				right = new IntegerNode(Integer.parseInt(varHash.get(node.getRight().toString()).toString()));
			} else if (varHash.get(node.getRight().toString()) instanceof BooleanDataType) {
				return Boolean.parseBoolean(varHash.get(node.getRight().toString()).toString());
			} else if (varHash.get(node.getRight().toString()) instanceof FloatDataType) {
				right = new FloatNode(Float.parseFloat(varHash.get(node.getRight().toString()).toString()));
			} else {
				throw new Exception("Can only evaluate a boolean expression with numbers");
			}
		} else {
			right = node.getRight();
		}
		
		if (node.getCondition() == null) {					// If the condition is null, parse a boolean from the boolnode
			return Boolean.parseBoolean(left.toString());	// in the left node
		}
		String typeL = left.getClass().toString();			// Sets as string to the type of the left node
		String typeR = right.getClass().toString();			// Sets as string to the type of the right node
		if (left instanceof MathOpNode) {
			typeL = ((MathOpNode)left).getLeft().getClass().toString();
		} else if (right instanceof MathOpNode) {
			typeR = ((MathOpNode)right).getLeft().getClass().toString();
		}
		if (!typeL.equals(typeR)) {
			throw new Exception("Comparison can only take place between the same data types");
		}
		
		// For each condition, resolves the expression and returns a boolean value
		switch(node.getCondition()) {
			case "LESS":
				if (typeL.equals("class IntegerNode")) {
					return resolveInt(left, varHash) < resolveInt(right, varHash);
				} else {
					return resolveFloat(left, varHash) < resolveFloat(right, varHash);
				}
				
			case "LESSOREQUAL":
				if (typeL.equals("class IntegerNode")) {
					return resolveInt(left, varHash) <= resolveInt(right, varHash);
				} else {
					return resolveFloat(left, varHash) <= resolveFloat(right, varHash);
				}
				
			case "GREATER":
				if (typeL.equals("class IntegerNode")) {
					return resolveInt(left, varHash) > resolveInt(right, varHash);
				} else {
					return resolveFloat(left, varHash) > resolveFloat(right, varHash);
				}
				
			case "GREATEROREQUAL":
				if (typeL.equals("class IntegerNode")) {
					return resolveInt(left, varHash) >= resolveInt(right, varHash);
				} else {
					return resolveFloat(left, varHash) >= resolveFloat(right, varHash);
				}
				
			case "NOTEQUAL":
				if (typeL.equals("class IntegerNode")) {
					return !((Integer)resolveInt(left, varHash)).equals((Integer)resolveInt(right, varHash));
				} else {
					return !((Float)resolveFloat(left, varHash)).equals((Float)resolveFloat(right, varHash));
				}
				
			case "EQUAL":
				if (typeL.equals("class IntegerNode")) {
					return ((Integer)resolveInt(left, varHash)).equals((Integer)resolveInt(right, varHash));
				} else {
					return ((Float)resolveFloat(left, varHash)).equals((Float)resolveFloat(right, varHash));
				}
		}
		return true;
	}
	
	/**
	 * Interprets a function
	 * @param function A function node to be interpreted
	 * @param dataTypes The parameters of a function as data types
	 * @throws Exception
	 */
	public static void InterpretFunction(FunctionNode function, ArrayList<InterpreterDataType> dataTypes) throws Exception {
		// Creates a hashmap of reserved words
		HashMap<String, InterpreterDataType> varHash = new HashMap<String, InterpreterDataType>();
		ArrayList<VariableNode> paramVars = function.getParameters();
		ArrayList<VariableNode> localVars = function.getLocalVars();
		// adds parameters to the hashmap
		for(int i = 0; i < dataTypes.size(); i++) {		
			varHash.put(paramVars.get(i).getName(), dataTypes.get(i));		
		}
		// Adds local variables to the hash map
		for(int i = 0; i < localVars.size(); i++) {	
			if (localVars.get(i).isConstant()) {
				if (localVars.get(i).getState().equals(VariableNode.dataType.INTEGER)) {
					varHash.put(localVars.get(i).getName(), new IntDataType(Integer.parseInt(localVars.get(i).getInitialValue().toString())));
				} else if (localVars.get(i).getState().equals(VariableNode.dataType.REAL)) {
					varHash.put(localVars.get(i).getName(), new FloatDataType(Float.parseFloat(localVars.get(i).getInitialValue().toString())));
				} else if (localVars.get(i).getState().equals(VariableNode.dataType.STRING)) {
					varHash.put(localVars.get(i).getName(), new StringDataType(localVars.get(i).getInitialValue().toString()));
				} else if (localVars.get(i).getState().equals(VariableNode.dataType.CHAR)) {
					varHash.put(localVars.get(i).getName(), new CharDataType(localVars.get(i).getInitialValue().toString().charAt(0)));
				} else {
					varHash.put(localVars.get(i).getName(), new BooleanDataType(Boolean.parseBoolean(localVars.get(i).getInitialValue().toString())));
				}
			} else {
				if (localVars.get(i).getState().equals(VariableNode.dataType.INTEGER)) {
					varHash.put(localVars.get(i).getName(), new IntDataType());			
				} else if (localVars.get(i).getState().equals(VariableNode.dataType.REAL)) {
					varHash.put(localVars.get(i).getName(), new FloatDataType());
				} else if (localVars.get(i).getState().equals(VariableNode.dataType.STRING)) {
					varHash.put(localVars.get(i).getName(), new StringDataType());
				} else if (localVars.get(i).getState().equals(VariableNode.dataType.CHAR)) {
					varHash.put(localVars.get(i).getName(), new CharDataType());
				} else {
					varHash.put(localVars.get(i).getName(), new BooleanDataType());
				}
			}	
		}
		InterpretBlock(function.getStatements(), varHash);
		for (int y = 0; y < dataTypes.size(); y++) {
			if (((FunctionNode)hashGet(function.getFunctionName())).getParameters().get(y).isConstant()) { 
				dataTypes.set(y, varHash.get(paramVars.get(y).getName()));
			}
		}
	}
	
	/**
	 * Interprets the body of a function
	 * @param statements The body statements of a function
	 * @param varHash A hashmap of variables
	 * @throws Exception
	 */
	public static void InterpretBlock(ArrayList<StatementNode> statements, HashMap<String, InterpreterDataType> varHash) throws Exception {
		if (statements != null) {	// if the statements is not null, this interprets the body of a function
			for (int i = 0; i < statements.size(); i++) {
				if (statements.get(i) instanceof FunctionCallNode) {
					InterpretFunctionCall(i, statements, varHash);
				} else if(statements.get(i) instanceof AssignmentNode) {
					InterpretAssignment(i, statements, varHash);
				} else if(statements.get(i) instanceof WhileNode) {
					InterpretWhile(i, statements, varHash);
				} else if(statements.get(i) instanceof RepeatNode) {
					InterpretRepeat(i, statements, varHash);
				} else if(statements.get(i) instanceof IfNode) {
					InterpretIf(i, statements, varHash);
				} else if (statements.get(i) instanceof ForNode) {
					InterpretFor(i, statements, varHash);
				}			
			}
		}
	}
	
	/**
	 * Interprets function calls
	 * @param i current location in statements
	 * @param statements The body of a function
	 * @param varHash A hashmap of variables represented by interpreter data types
	 * @throws Exception 
	 */
	public static void InterpretFunctionCall(int i, ArrayList<StatementNode> statements, HashMap<String, InterpreterDataType> varHash) throws Exception {
		String functionName = ((FunctionCallNode)statements.get(i)).getFunctionName().toString();
		ArrayList<ParameterNode> paramList = ((FunctionCallNode)statements.get(i)).getParameters();
		ArrayList<InterpreterDataType> dataTypeList = new ArrayList<InterpreterDataType>(); 
		
		// Adds parameters to a data type list
		for (int x = 0; x < paramList.size(); x++) {
			if (paramList.get(x).getNode() instanceof IntegerNode) {
				dataTypeList.add(new IntDataType(Integer.parseInt(paramList.get(x).getNode().toString())));
			} else if (paramList.get(x).getNode() instanceof FloatNode) {
				dataTypeList.add(new FloatDataType(Float.parseFloat(paramList.get(x).getNode().toString())));
			} else if (paramList.get(x).getNode() instanceof StringNode) {
				dataTypeList.add(new StringDataType(paramList.get(x).getNode().toString()));
			} else if (paramList.get(x).getNode() instanceof CharNode) {
				dataTypeList.add(new CharDataType(paramList.get(x).getNode().toString().charAt(0)));
			} else if (paramList.get(x).getNode() instanceof BoolNode) {
				dataTypeList.add(new BooleanDataType(Boolean.parseBoolean(paramList.get(x).getNode().toString()))); 
			} else {
				dataTypeList.add(varHash.get(paramList.get(x).getNode().toString()));
			}
		}
		
		// Executes each of the built in functions with the data type list
		switch(functionName) {
			case "read":
				ArrayList<VariableNode> readVars = new ArrayList<VariableNode>();
				String name = "a";
				for (int q = 0; q < dataTypeList.size(); q++) {
					if (!(paramList.get(q).hasVar())) {
						throw new Exception("Read variable does not have var");
					}
					if (dataTypeList.get(q) instanceof IntDataType) {
						readVars.add(new VariableNode(name, VariableNode.dataType.INTEGER, new IntegerNode(), true));
					} else if (dataTypeList.get(q) instanceof FloatDataType) {
						readVars.add(new VariableNode(name, VariableNode.dataType.REAL, new FloatNode(), true));
					} else if (dataTypeList.get(q) instanceof StringDataType) {
						readVars.add(new VariableNode(name, VariableNode.dataType.STRING, new StringNode(), true));
					} else if (dataTypeList.get(q) instanceof CharDataType) {
						readVars.add(new VariableNode(name, VariableNode.dataType.CHAR, new CharNode(), true));
					} else {
						readVars.add(new VariableNode(name, VariableNode.dataType.BOOLEAN, new BoolNode(), true));
					}
					name += 1;
				}
				read readNode = new read(readVars, dataTypeList);
				readNode.Execute();
				for (int t = 0; t < dataTypeList.size(); t++) {
					varHash.put(paramList.get(t).getNode().toString(), readNode.getDataTypesList().get(t));
				}
				break;
				
			case "write":
				write writeNode = new write(((write)hashGet("write")).getParameters(), dataTypeList);
				writeNode.Execute();
				break;
				
			case "squareRoot":
				if (((squareRoot)hashGet("squareRoot")).getParameters().size() != dataTypeList.size()) {
					throw new Exception("Incorrect number of parameters for squareRoot");
				}
				if (!(paramList.get(1).hasVar())) {
					throw new Exception("The second parameter in squareRoot does not have var");
				}
				squareRoot squareRootNode = new squareRoot(((squareRoot)hashGet("squareRoot")).getParameters(), dataTypeList);
				squareRootNode.Execute();
				varHash.put(paramList.get(1).getNode().toString(), squareRootNode.getDataTypesList().get(1));
				break;	
				
			case "getRandom":
				if (((getRandom)hashGet("getRandom")).getParameters().size() != dataTypeList.size()) {
					throw new Exception("Incorrect number of parameters for getRandom");
				}
				if (!(paramList.get(0).hasVar())) {
					throw new Exception("The parameter in getRandom does not have var");
				}
				getRandom getRandomNode = new getRandom(((getRandom)hashGet("getRandom")).getParameters(),dataTypeList);
				getRandomNode.Execute();
				varHash.put(paramList.get(0).getNode().toString(), getRandomNode.getDataTypesList().get(0));
				break;
				
			case "integerToReal":
				if (((integerToReal)hashGet("integerToReal")).getParameters().size() != dataTypeList.size()) {
					throw new Exception("Incorrect number of parameters for integerToReal");
				}
				if (!(paramList.get(1).hasVar())) {
					throw new Exception("The second parameter in integerToReal does not have var");
				}
				integerToReal integerToRealNode = new integerToReal(((integerToReal)hashGet("integerToReal")).getParameters(),dataTypeList);
				integerToRealNode.Execute();
				varHash.put(paramList.get(1).getNode().toString(), integerToRealNode.getDataTypesList().get(1));
				break;
				
			case "realToInteger":
				if (((realToInteger)hashGet("realToInteger")).getParameters().size() != dataTypeList.size()) {
					throw new Exception("Incorrect number of parameters for realToInteger");
				}
				if (!(paramList.get(1).hasVar())) {
					throw new Exception("The second parameter in realToInteger does not have var");
				}
				realToInteger realToIntegerNode = new realToInteger(((realToInteger)hashGet("realToInteger")).getParameters(),dataTypeList);
				realToIntegerNode.Execute();
				varHash.put(paramList.get(1).getNode().toString(), realToIntegerNode.getDataTypesList().get(1));
				break;
					
			case "Left":
				if (((Left)hashGet("Left")).getParameters().size() != dataTypeList.size()) {
					throw new Exception("Incorrect number of parameters for Left");
				}
				if (!(paramList.get(2).hasVar())) {
					throw new Exception("The third parameter in Left does not have var");
				}
				Left LeftNode = new Left(((Left)hashGet("Left")).getParameters(),dataTypeList);
				LeftNode.Execute();
				varHash.put(paramList.get(2).getNode().toString(), LeftNode.getDataTypesList().get(2));
				break;
				
			case "Right":
				if (((Right)hashGet("Right")).getParameters().size() != dataTypeList.size()) {
					throw new Exception("Incorrect number of parameters for Right");
				}
				if (!(paramList.get(2).hasVar())) {
					throw new Exception("The third parameter in Right does not have var");
				}
				Right RightNode = new Right(((Right)hashGet("Right")).getParameters(),dataTypeList);
				RightNode.Execute();
				varHash.put(paramList.get(2).getNode().toString(), RightNode.getDataTypesList().get(2));
				break;
				
			case "Substring":
				if (((Substring)hashGet("Substring")).getParameters().size() != dataTypeList.size()) {
					throw new Exception("Incorrect number of parameters for Substring");
				}
				if (!(paramList.get(3).hasVar())) {
					throw new Exception("The fourth parameter in Substring does not have var");
				}
				Substring SubstringNode = new Substring(((Substring)hashGet("Substring")).getParameters(),dataTypeList);
				SubstringNode.Execute();
				varHash.put(paramList.get(3).getNode().toString(), SubstringNode.getDataTypesList().get(3));
				break;
				
			default:
				if ((FunctionNode)hashGet(functionName) == null) {
					throw new Exception("This function does not exist");
				}
				InterpretFunction((FunctionNode)hashGet(functionName), dataTypeList);
				for (int y = 0; y < ((FunctionNode)hashGet(functionName)).getParameters().size(); y++) {
					if (((FunctionNode)hashGet(functionName)).getParameters().get(y).isConstant() && paramList.get(y).hasVar()) { 
						varHash.put(paramList.get(y).getNode().toString(), dataTypeList.get(y));
					}
				}	
				break;
		}
	}
	
	/**
	 * Interprets Assignments
	 * @param i current location in statements
	 * @param statements The body of a function
	 * @param varHash A hashmap of variables represented by interpreter data types
	 * @throws Exception 
	 */
	public static void InterpretAssignment(int i, ArrayList<StatementNode> statements, HashMap<String, InterpreterDataType> varHash) throws Exception {
		String target = ((AssignmentNode)statements.get(i)).getTarget().toString();
		Node expression = ((AssignmentNode)statements.get(i)).getExpression();
		if (varHash.get(target) instanceof IntDataType) {
			varHash.put(target.toString(), new IntDataType(resolveInt(expression, varHash))); 
		} else if (varHash.get(target) instanceof FloatDataType) {
			varHash.put(target, new FloatDataType(resolveFloat(expression, varHash))); 
		} else if (varHash.get(target) instanceof StringDataType) {
			varHash.put(target, new StringDataType(resolveString(expression, varHash))); 
		} else if (varHash.get(target) instanceof CharDataType) {
			varHash.put(target, new CharDataType(resolveCharacter(expression, varHash))); 
		} else if (varHash.get(target) instanceof BooleanDataType) {
			varHash.put(target, new BooleanDataType(resolveBoolean(expression, varHash))); 
		}
	}
	
	/**
	 * Interprets a while loop
	 * @param i The current location in statements
	 * @param statements The body of a function
	 * @param varHash A hashmap of variables represented by interpreter data types
	 * @throws Exception 
	 */
	public static void InterpretWhile(int i, ArrayList<StatementNode> statements, HashMap<String, InterpreterDataType> varHash) throws Exception {
		while (EvaluateBooleanExpression(((WhileNode)statements.get(i)).getExp(), varHash)) {
			InterpretBlock(((WhileNode)statements.get(i)).getList(), varHash);
		}
	}

	/**
	 * Interprets a repeat loop
	 * @param i The current location in statements
	 * @param statements The body of a function
	 * @param varHash A hashmap of variables represented by interpreter data types
	 * @throws Exception 
	 */
	public static void InterpretRepeat(int i, ArrayList<StatementNode> statements, HashMap<String, InterpreterDataType> varHash) throws Exception {
		do {
			InterpretBlock(((RepeatNode)statements.get(i)).getList(), varHash);
		} while (!EvaluateBooleanExpression(((RepeatNode)statements.get(i)).getExp(), varHash));
	}
	
	/**
	 * Interprets a for loop
	 * @param i The current location in statements
	 * @param statements The body of a function
	 * @param varHash A hashmap of variables represented by interpreter data types
	 * @throws Exception 
	 */
	public static void InterpretFor(int i, ArrayList<StatementNode> statements, HashMap<String, InterpreterDataType> varHash) throws Exception {
		InterpreterDataType start;
		InterpreterDataType end;
		if (!(((ForNode)statements.get(i)).getStart() instanceof IntegerNode) || !(((ForNode)statements.get(i)).getEnd() instanceof IntegerNode) || !(varHash.get(((ForNode)statements.get(i)).getVar().toString()) instanceof IntDataType)) {
			throw new Exception("Non integer in for loop bounds");
		}
		if (((ForNode)statements.get(i)).getStart() instanceof VariableReferenceNode) {
			start = varHash.get(((ForNode)statements.get(i)).getStart().toString());
			if (!(start instanceof IntDataType)) {
				throw new Exception("Non integer starting boung in for loop");
			}
		} else {
			start = new IntDataType(((IntegerNode)((ForNode)statements.get(i)).getStart()).getInteger());
		}
		if (((ForNode)statements.get(i)).getEnd() instanceof VariableReferenceNode) {
			end = varHash.get(((ForNode)statements.get(i)).getEnd().toString());
			if (!(end instanceof IntDataType)) {
				throw new Exception("Non integer ending boung in for loop");
			}
		} else {
			end = new IntDataType(((IntegerNode)((ForNode)statements.get(i)).getEnd()).getInteger());
		}
		int startInt = Integer.parseInt(start.toString());
		int endInt = Integer.parseInt(end.toString());
		varHash.put(((ForNode)statements.get(i)).getVar().toString(), start);
		for (int x = startInt; x <= endInt; x++) {
			varHash.put(((ForNode)statements.get(i)).getVar().toString(), new IntDataType(x));
			InterpretBlock(((ForNode)statements.get(i)).getList(), varHash);
		}			
	}
	
	/**
	 * Interprets a if loop
	 * @param i The current location in statements
	 * @param statements The body of a function
	 * @param varHash A hashmap of variables represented by interpreter data types
	 * @throws Exception 
	 */
	public static void InterpretIf(int i, ArrayList<StatementNode> statements, HashMap<String, InterpreterDataType> varHash) throws Exception {
		if (EvaluateBooleanExpression(((IfNode)statements.get(i)).getExp(), varHash)) {
			InterpretBlock(((IfNode)statements.get(i)).getList(), varHash);
		} else if (((IfNode)statements.get(i)).getIfNode() == null) {  
		
		} else {
			InterpretIfChain(((IfNode)statements.get(i)).getIfNode(), varHash);
		}
	}
	
	/**
	 * Interprets a chain of if loops
	 * @param node A ifNode passed in from last call
	 * @param varHash A hashmap of variables represented by interpreter data types
	 * @throws Exception
	 */
	public static void InterpretIfChain(IfNode node, HashMap<String, InterpreterDataType> varHash) throws Exception {
		if (node instanceof ElseNode) {
			InterpretBlock(node.getList(), varHash);
		} else if (EvaluateBooleanExpression(node.getExp(), varHash)) {
			InterpretBlock(node.getList(), varHash);
		} else if (node.getIfNode().equals(null)) { 
			
		} else {
			InterpretIfChain(node.getIfNode(), varHash);
		}
	}
}
