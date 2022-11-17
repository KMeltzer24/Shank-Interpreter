import java.util.ArrayList;
import java.util.HashMap;

/**
 * Preforms semantic analysis on a ast
 * @author Kevin Meltzer
 * @version 1.9
 */
public class SemanticAnalysis {
	/**
	 * A list to hold function nodes
	 */
	private ArrayList<FunctionNode> functionNodeList;
	
	/**
	 * Create a SemanticAnalysis object 
	 * @param list A ArrayList of functionNodes 
	 */
	public SemanticAnalysis(ArrayList<FunctionNode> functionNodeList) {
		this.functionNodeList = functionNodeList;
	}
	
	/**
	 * Calles check assignments on the list of function nodes to check if they are valid
	 * @throws Exception
	 */
	public void CheckStatements() throws Exception {
		for (int i = 0; i < this.functionNodeList.size(); i++) { 
			CheckAssignments(this.functionNodeList.get(i).getStatements(), this.functionNodeList.get(i));
		}
	}
	
	/**
	 * Checks the types of both sides of a assignment statement to see
	 * if that statement is valid
	 * @param StatementNodeList A list of statement nodes
	 * @param functionNode A current function which assignments are being checked
	 * @throws Exception
	 */
	public void CheckAssignments(ArrayList<StatementNode> StatementNodeList, FunctionNode functionNode) throws Exception {
		HashMap<String, String> varTypeHash = new HashMap<String, String>();		// Creates a hashmap of variable names and variable types
		for (int z = 0; z < functionNode.getParameters().size(); z++) {
			varTypeHash.put(functionNode.getParameters().get(z).getName(), functionNode.getParameters().get(z).getState().toString());
		}
		for (int y = 0; y < functionNode.getLocalVars().size(); y++) {
			varTypeHash.put(functionNode.getLocalVars().get(y).getName(), functionNode.getLocalVars().get(y).getState().toString());
		}
		if (StatementNodeList != null) {
			for (int x = 0; x < StatementNodeList.size(); x++) {					// For each statement, check if an assignment
				if (StatementNodeList.get(x) instanceof AssignmentNode) {			// and then check if the types on both sides match
					String type = varTypeHash.get(((AssignmentNode)StatementNodeList.get(x)).getTarget().toString());
					if (type == null) {
						throw new Exception("Variable not declared");
					}
					if (((AssignmentNode)StatementNodeList.get(x)).getExpression() instanceof MathOpNode) {	
								ArrayList<String> typeList = new ArrayList<String>();
								getMathOpNodeTypes((MathOpNode)((AssignmentNode)StatementNodeList.get(x)).getExpression(), typeList, varTypeHash);
								for (int a = 0; a < typeList.size(); a++) {
									switch(type) {
										case "INTEGER":
											if (!typeList.get(a).equals("class IntegerNode")) {
												throw new Exception("Cannot assign other types to an integer.");
											}
											break;
											
										case "REAL":
											if (!typeList.get(a).equals("class FloatNode")) {
												throw new Exception("Cannot assign other types to a real.");
											}
											break;
											
										case "STRING":
											if (!typeList.get(a).equals("class StringNode") && !typeList.get(a).equals("class CharNode")) {
												throw new Exception("Cannot assign other types to a string.");
											}
											break;
											
										case "CHAR":
											throw new Exception("Cannot assign more than one character to a char.");
											
										case "BOOLEAN":
											if (!typeList.get(a).equals("class BoolNode")) {
												throw new Exception("Cannot assign other types to an boolean");
											}
											break;
											
									}
								}
								
					// Checks if a variable reference node is the same type it is being assigned to
					} else if (((AssignmentNode)StatementNodeList.get(x)).getExpression() instanceof VariableReferenceNode) { 
						if (!varTypeHash.get(((AssignmentNode)StatementNodeList.get(x)).getExpression().toString()).equals(type)) {
							throw new Exception("Cannot do assignments with different types.");
						}  
					} else {
						String expType = ((AssignmentNode)StatementNodeList.get(x)).getExpression().getClass().toString();
						switch(type) {
							case "INTEGER":
								if (!expType.equals("class IntegerNode")) {
									throw new Exception("Cannot assign other types to an integer.");
								}
								break;
								
							case "REAL":
								if (!expType.equals("class FloatNode")) {
									throw new Exception("Cannot assign other types to a real.");
								}
								break;
								
							case "STRING":
								if (!expType.equals("class StringNode") && !expType.equals("class CharNode")) {
									throw new Exception("CCannot assign other types to a string.");
								}
								break;
								
							case "CHAR":
								if (!expType.equals("class CharNode")) {
									throw new Exception("Cannot assign other types to a char.");
								}
								break;
								
							case "BOOLEAN":
								if (!(expType.equals("class BoolNode") || expType.equals("class BooleanExpressionNode"))) {
									throw new Exception("Cannot assign other types to a boolean.");
								}
								break;
								
						}
					}	
				// Uses recursion to check the statements of loop nodes
				} else if (StatementNodeList.get(x) instanceof WhileNode) {
					CheckAssignments(((WhileNode)StatementNodeList.get(x)).getList(), functionNode);
				} else if (StatementNodeList.get(x) instanceof ForNode) {
					CheckAssignments(((ForNode)StatementNodeList.get(x)).getList(), functionNode);
				} else if (StatementNodeList.get(x) instanceof RepeatNode) {
					CheckAssignments(((RepeatNode)StatementNodeList.get(x)).getList(), functionNode);
				} else if (StatementNodeList.get(x) instanceof IfNode) {
					if (((IfNode)StatementNodeList.get(x)).getIfNode() == null) {
						CheckAssignments(((IfNode)StatementNodeList.get(x)).getList(), functionNode);
					} else {
						CheckAssignments(((IfNode)StatementNodeList.get(x)).getIfNode().getList(), functionNode);
					}
				}
			}
		}
	}	
	
	/**
	 * Makes a list of strings containing the types in a math op node
	 * @param node A math op node which is desired to get types from
	 * @param typeList A list of strings which the types are added to
	 * @param varTypeHash A hashmap containing variable name and types
	 * @return
	 */
	public ArrayList<String> getMathOpNodeTypes(MathOpNode node, ArrayList<String> typeList, HashMap<String, String> varTypeHash) {
		if (node.getLeft() instanceof MathOpNode) {
			getMathOpNodeTypes((MathOpNode)node.getLeft(), typeList, varTypeHash);
		} else if (node.getLeft() instanceof VariableReferenceNode) {			
			switch (varTypeHash.get((node.getLeft().toString()))) {
				case "INTEGER":
					typeList.add("class IntegerNode");
					break;
				case "FLOAT":
					typeList.add("class FloatNode");
					break;
				case "STRING":
					typeList.add("class StringNode");
					break;
				case "CHAR":
					typeList.add("class CharNode");
					break;
				case "BOOLEAN":
					typeList.add("class BoolNode");
					break;
			}
		} else {
			typeList.add(node.getLeft().getClass().toString());
		}
		if (node.getRight() instanceof MathOpNode) {
			getMathOpNodeTypes((MathOpNode)node.getRight(), typeList, varTypeHash);
		} else if (node.getRight() instanceof VariableReferenceNode) {			
			switch (varTypeHash.get((node.getRight().toString()))) {
				case "INTEGER":
					typeList.add("class IntegerNode");
					break;
				case "FLOAT":
					typeList.add("class FloatNode");
					break;
				case "STRING":
					typeList.add("class StringNode");
					break;
				case "CHAR":
					typeList.add("class CharNode");
					break;
				case "BOOLEAN":
					typeList.add("class BoolNode");
					break;
			}
		} else {
			typeList.add(node.getRight().getClass().toString());
		}
		return typeList;
	}
}