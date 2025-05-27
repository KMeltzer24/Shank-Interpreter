package com.shank.nodes;

/*
 * Represents a Math operator node.
 * @author Kevin Meltzer
 * @version 1.2
 */
public class MathOpNode extends Node {
	
	/**
	 * Left child of the node
	 */
	private final Node left;
	
	/**
	 * Right child of the node
	 */
	private final Node right;
	
	/**
	 * Retrieves the left node
	 * @return The left node
	 */
	public Node getLeft() {
		return this.left;
	}
	
	/**
	 * Retrieves the right node
	 * @return The right node
	 */
	public Node getRight() {
		return this.right;
	}
	
	/**
	 * Makes a enum with all of the possible valid input states
	 */
	public enum operator {
		PLUS,
		MINUS,
		TIMES,
		DIVIDES,
		MOD,
		GREATER,
		LESS,
		GREATEROREQUAL,
		LESSOREQUAL,
		EQUAL,
		NOTEQUAL
	}
	
	/**
	 * Holds the state of the node
	 */ 
	private final operator state;
	
	/**
	 * Retrieves the state of a node
	 * @return The state of a node
	 */
	public operator getState() {
		return this.state;
	}
	
	
	/**
	 * Creates a MathOpNode object
	 * @param state Desired state of the node
	 * @param left Left child of the node
	 * @param right Right child of the node
	 */
	public MathOpNode(operator state, Node left, Node right) {
		this.state = state;
		this.left = left;
		this.right = right;
	}
	
	/**
	 * Turns the left child, state, and right child of the node
	 * into a string and returns it.
	 * @return The left child, state and right child of the node
	 */
	@Override
	public String toString() {
		return "" + this.left + " " +  this.getState() + " " + this.right;
	}
}
