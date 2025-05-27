package com.shank.nodes;

/*
 * Represents a float node.
 * @author Kevin Meltzer
 * @version 1.3
 */
public class FloatNode extends Node {

	/**
	 * A float which will be the element of a node
	 */
	private Float num;
	
	/**
	 * Creates a floatNode object
	 * @param num A Float
	 */
	public FloatNode(Float num) {
		this.num = num;
	}
	
	/**
	 * Creates a empty FloatNode object
	 */
	public FloatNode() {

	}
	
	/**
	 * Returns the float from a node
	 * @return A float from a node
	 */
	public Float getFloat() {
		return this.num;
	}
	
	/**
	 * Retrieves the float of a node and returns it as a string
	 * @return The float from a node as a string
	 */
	@Override
	public String toString() {
		return this.num + "";
	}
}
