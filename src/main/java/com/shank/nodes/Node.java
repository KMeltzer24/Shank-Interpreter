package com.shank.nodes;

/*
 * Represents a abstract node.
 * @author Kevin Meltzer
 * @version 1.1
 */
public abstract class Node {
	
	/**
	 * Creates a empty node
	 */
	public Node() {
		
	}
	
	/**
	 * Returns the node as a string
	 */
	@Override
	public abstract String toString();
}