package com.shank.interpreter;

import java.util.ArrayList;
import java.util.HashMap;

import com.shank.nodes.AssignmentNode;
import com.shank.nodes.BoolNode;
import com.shank.nodes.BooleanExpressionNode;
import com.shank.nodes.CharNode;
import com.shank.nodes.FloatNode;
import com.shank.nodes.ForNode;
import com.shank.nodes.FunctionNode;
import com.shank.nodes.IfNode;
import com.shank.nodes.IntegerNode;
import com.shank.nodes.MathOpNode;
import com.shank.nodes.RepeatNode;
import com.shank.nodes.StatementNode;
import com.shank.nodes.StringNode;
import com.shank.nodes.VariableReferenceNode;
import com.shank.nodes.WhileNode;


/**
 * Preforms semantic analysis on a ast
 * @author Kevin Meltzer
 * @version 1.9
 */
public class SemanticAnalysis {
	/**
	 * A list to hold function nodes
	 */
	private final ArrayList<FunctionNode> functionNodeList;
	
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
		HashMap<String, String> varTypeHash = new HashMap<>();		// Creates a hashmap of variable names and variable types
		for (int z = 0; z < functionNode.getParameters().size(); z++) {
			varTypeHash.put(functionNode.getParameters().get(z).getName(), functionNode.getParameters().get(z).getState().toString());
		}
		for (int y = 0; y < functionNode.getLocalVars().size(); y++) {
			varTypeHash.put(functionNode.getLocalVars().get(y).getName(), functionNode.getLocalVars().get(y).getState().toString());
		}
		if (StatementNodeList != null) {
			for (int x = 0; x < StatementNodeList.size(); x++) {							// For each statement, check if an assignment
				if (StatementNodeList.get(x) instanceof AssignmentNode assignmentNode) {	// and then check if the types on both sides match
					String type = varTypeHash.get(assignmentNode.getTarget().toString());
					if (type == null) {
						throw new Exception("Variable not declared");
					}
					if (assignmentNode.getExpression() instanceof MathOpNode mathOpNode) {	
								ArrayList<String> typeList = new ArrayList<>();
								getMathOpNodeTypes(mathOpNode, typeList, varTypeHash);
								for (int a = 0; a < typeList.size(); a++) {
									switch(type) {
										case "INTEGER" -> {
											if (!typeList.get(a).equals("IntegerNode")) {
												throw new Exception("Cannot assign other types to an integer.");
											}
										}
											
										case "REAL" -> {
											if (!typeList.get(a).equals("FloatNode")) {
												throw new Exception("Cannot assign other types to a real.");
											}
										}
											
										case "STRING" -> {
											if (!typeList.get(a).equals("StringNode") && !typeList.get(a).equals("CharNode")) {
												throw new Exception("Cannot assign other types to a string.");
											}
										}
											
										case "CHAR" -> throw new Exception("Cannot assign more than one character to a char.");
											
										case "BOOLEAN" -> {
											if (!typeList.get(a).equals("BoolNode")) {
												throw new Exception("Cannot assign other types to an boolean");
											}
										}
											
									}
								}
								
					// Checks if a variable reference node is the same type it is being assigned to
					} else if (assignmentNode.getExpression() instanceof VariableReferenceNode) { 
						if (!varTypeHash.get(assignmentNode.getExpression().toString()).equals(type)) {
							throw new Exception("Cannot do assignments with different types.");
						}  
					} else {
						switch(type) {
							case "INTEGER" -> {
								if (!(assignmentNode.getExpression() instanceof IntegerNode)) {
									throw new Exception("Cannot assign other types to an integer.");
								}
							}
								
							case "REAL" -> {
								if (!(assignmentNode.getExpression() instanceof FloatNode)) {
									throw new Exception("Cannot assign other types to a real.");
								}
							}
								
							case "STRING" -> {
								if (!(assignmentNode.getExpression() instanceof StringNode) && !(assignmentNode.getExpression() instanceof CharNode)) {
									throw new Exception("Cannot assign other types to a string.");
								}
							}
								
							case "CHAR" -> {
								if (!(assignmentNode.getExpression() instanceof CharNode)) {
									throw new Exception("Cannot assign other types to a char.");
								}
							}
								
							case "BOOLEAN" -> {
								if (!(assignmentNode.getExpression() instanceof BoolNode || assignmentNode.getExpression() instanceof BooleanExpressionNode)) {
									throw new Exception("Cannot assign other types to a boolean.");
								}
							}
								
						}
					}	
				// Uses recursion to check the statements of loop nodes
				} else if (StatementNodeList.get(x) instanceof WhileNode whileNode) {
					CheckAssignments(whileNode.getList(), functionNode);
				} else if (StatementNodeList.get(x) instanceof ForNode forNode) {
					CheckAssignments(forNode.getList(), functionNode);
				} else if (StatementNodeList.get(x) instanceof RepeatNode repeatNode) {
					CheckAssignments(repeatNode.getList(), functionNode);
				} else if (StatementNodeList.get(x) instanceof IfNode ifNode) {
					if (ifNode.getIfNode() == null) {
						CheckAssignments(ifNode.getList(), functionNode);
					} else {
						CheckAssignments(ifNode.getIfNode().getList(), functionNode);
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
		if (node.getLeft() instanceof MathOpNode mathOpNode) {
			getMathOpNodeTypes(mathOpNode, typeList, varTypeHash);
		} else if (node.getLeft() instanceof VariableReferenceNode) {			
			switch (varTypeHash.get((node.getLeft().toString()))) {
				case "INTEGER" -> typeList.add("IntegerNode");
				case "FLOAT" -> typeList.add("FloatNode");
				case "STRING" -> typeList.add("StringNode");
				case "CHAR" -> typeList.add("CharNode");
				case "BOOLEAN" -> typeList.add("BoolNode");
			}
		} else {
			typeList.add(node.getLeft().getClass().getSimpleName());
		}
		if (node.getRight() instanceof MathOpNode mathOpNode) {
			getMathOpNodeTypes(mathOpNode, typeList, varTypeHash);
		} else if (node.getRight() instanceof VariableReferenceNode) {			
			switch (varTypeHash.get((node.getRight().toString()))) {
				case "INTEGER" -> typeList.add("IntegerNode");
				case "FLOAT" -> typeList.add("FloatNode");
				case "STRING" -> typeList.add("StringNode");
				case "CHAR" -> typeList.add("CharNode");
				case "BOOLEAN" -> typeList.add("BoolNode");
			}
		} else {
			typeList.add(node.getRight().getClass().getSimpleName());
		}
		return typeList;
	}

}