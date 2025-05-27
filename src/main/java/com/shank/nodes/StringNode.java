package com.shank.nodes;

/*
 * Represents a String node.
 * @author Kevin Meltzer
 * @version 1.9
 */
public class StringNode extends Node {

	/**
	 * A String which will be the element of a node
	 */
	private String str;
	
	/**
	 * Creates a StringNode object
	 * @param str A String
	 */
	public StringNode(String str) {
		this.str = str;
	}
	
	/**
	 * Creates a empty StringNode object
	 */
	public StringNode() {

	}
	
	/**
	 * Retrieves the String of a node 
	 * @return The String from a node 
	 */
	@Override
	public String toString() {
		return this.str;
	}
}
