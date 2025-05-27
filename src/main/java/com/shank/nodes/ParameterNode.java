package com.shank.nodes;

/*
 * Represents a parameter node.
 * @author Kevin Meltzer
 * @version 1.6
 */
public class ParameterNode extends Node {

	/**
	 * A node which will be the parameter
	 */
	private final Node parameter;
	
	/**
	 * Gets the parameter of the node
	 * @return The parameter of the node
	 */
	public Node getNode() {
		return this.parameter;
	}
	
	/**
	 * Holds a boolean on whether the node has a var or not
	 */ 
	private final boolean hasVar;
	
	/**
	 * get a boolean on whether the node has a var or not
	 * @return A boolean on whether the node has a var or not
	 */
	public boolean hasVar() {
		return hasVar;
	}
	
	/**
	 * Creates a parameter node using a node and a boolean
	 * @param parameter A variable which will be the parameter
	 * @param hasVar A boolean containing whether or not it has var
	 */
	public ParameterNode(Node parameter, boolean hasVar) {
		this.parameter = parameter;
		this.hasVar = hasVar;
	}
	
	/**
	 * Returns the parameter of a parameter node as a string
	 * @return The node containing the parameter as a string
	 */
	@Override
	public String toString() {
		if (hasVar == true) {
			return "var " + parameter;	
		} else {
			return parameter + "";			
		}
	}
}