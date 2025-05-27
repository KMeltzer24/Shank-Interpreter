package com.shank.nodes;

/*
 * Represents a boolean node.
 * @author Kevin Meltzer
 * @version 1.9
 */
public class BoolNode extends Node {

	/**
	 * A boolean which will be the element of a node
	 */
	private boolean bool;
	
	/**
	 * Creates a BoolNode object
	 * @param bool A boolean
	 */
	public BoolNode(boolean bool) {
		this.bool = bool;
	}
	
	/**
	 * Creates a empty BoolNode object
	 */
	public BoolNode() {

	}
	
	/**
	 * Returns the boolean of a node
	 * @return A boolean from a node
	 */
	public boolean getBoolean() {
		return this.bool;
	}
	
	/**
	 * Retrieves the boolean of a node and returns it as a string
	 * @return The boolean from a node as a string
	 */
	@Override
	public String toString() {
		return this.bool + "";
	}
}
