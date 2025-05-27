package com.shank.nodes;

/*
 * Represents a Integer node.
 * @author Kevin Meltzer
 * @version 1.3
 */
public class IntegerNode extends Node {

	/**
	 * A Integer which will be the element of a node
	 */
	private int integer;
	
	/**
	 * Creates a integerNode object
	 * @param integer A integer
	 */
	public IntegerNode(int integer) {
		this.integer = integer;
	}
	
	/**
	 * Creates a empty integerNode object
	 */
	public IntegerNode() {

	}
	
	/**
	 * Returns the Integer of a node
	 * @return A Integer from a node
	 */
	public int getInteger() {
		return this.integer;
	}
	
	/**
	 * Retrieves the integer of a node and returns it as a string
	 * @return The integer from a node as a string
	 */
	@Override
	public String toString() {
		return this.integer + "";
	}
}
