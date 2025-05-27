package com.shank.nodes;

import java.util.ArrayList;

/*
 * Represents a function call node.
 * @author Kevin Meltzer
 * @version 1.6
 */
public class FunctionCallNode extends StatementNode {

	/**
	 * The name of the function
	 */
	private final String functionName;
	
	/**
	 * Gets the name of the function
	 * @return The name of the function
	 */
	public String getFunctionName() {
		return this.functionName;
	}
	
	/**
	 * The list of parameters for the function call
	 */
	private final ArrayList<ParameterNode> parameterList;
	
	/**
	 * Gets the list of parameters of the node
	 * @return The list of parameters of the node
	 */
	public ArrayList<ParameterNode> getParameters() {
		return this.parameterList;
	}
	
	/**
	 * Creates a function call node with a functionName and a functionName
	 * @param functionName The name of the function
	 * @param parameterList The list of parameters for the function call
	 */
	public FunctionCallNode(String functionName, ArrayList<ParameterNode> parameterList) {
		this.functionName = functionName;
		this.parameterList = parameterList;
	}
	
	/**
	 * Returns the functionName and parameterList of this node as a string
	 * @return The functionName and parameterList of the node
	 */
	@Override
	public String toString() {
		return "\n" + functionName +  " " + parameterList + "\n";
	}
}
