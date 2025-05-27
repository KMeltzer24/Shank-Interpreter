package com.shank.nodes;

/*
 * Represents a Variable node.
 * @author Kevin Meltzer
 * @version 1.7
 */
public class VariableNode extends Node {

	/**
	 * A String which will be the name of the node
	 */
	private final String name;
	
	/**
	 * Returns the name of the variable
	 * @return The name of the variable
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Holds a boolean on whether the node is a constant or not
	 */ 
	private final boolean isConstant;
	
	/**
	 * Returns whether the variable is constant
	 * @return Whether the variable is constant
	 */
	public boolean isConstant() {
        return this.isConstant == true;
	}
	
	/**
	 * Makes a enum with all of the possible valid input states
	 */
	public enum dataType {
		INTEGER,
		REAL,
		STRING,
		CHAR,
		BOOLEAN
	}
	
	/**
	 * Holds the state of the node
	 */ 
	private final dataType state;
	
	/**
	 * Gets the state of the variable
	 * @return The state of the variable
	 */
	public dataType getState() {
		return this.state;
	}
	
	
	/**
	 * Holds the initial value of the node
	 */ 
	private Node initialValue;
	
	/**
	 * Gets the initial value of the node
	 * @return The initial vale of the node
	 */
	public Node getInitialValue() {
		return this.initialValue;
	}
	
	/**
	 * Sets the initial value of the node
	 * @param newVal A node for the initial value of the node to be set to
	 */
	public void setInitialValue(Node newVal) {
		this.initialValue = newVal;
	}
	
	/**
	 * Creates a variable node with a name, state, initial value and a boolean on whether or not it is a constant
	 * @param name Name of variable
	 * @param state Type of variable
	 * @param initialValue Initial value of the variable
	 * @param isConstant Boolean which represents if the node is a constants
	 */
	public VariableNode(String name, dataType state, Node initialValue, boolean isConstant) {
		this.name = name;
		this.state = state;
		this.initialValue = initialValue;
		this.isConstant = isConstant;
	}
	
	/**
	 * Returns the fields of a variable node object
	 * @return The name, state, initial value and a boolean on whether or not it is a constant as a string
	 */
	@Override
	public String toString() {
		if (isConstant == true) {
			return "Constant \nName: " + this.name + "\nData type: " + this.state + "\nInitial value: " + initialValue + "\n\n";	
		} else {
			return "Name: " + this.name + "\nData type: " + this.state + "\n\n";			
		}
	}
}
