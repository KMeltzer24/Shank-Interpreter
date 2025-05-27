package com.shank.nodes;

import java.util.ArrayList;

/**
 * Represents a Else node
 * @author Kevin Meltzer
 * @version 1.5
 */
public class ElseNode extends IfNode {
	
	/**
	 * The list of statement nodes
	 */
	private final ArrayList<StatementNode> list;
	
	
	/**
	 * Creates a Else node
	 * @param list The list of statement nodes
	 */
	public ElseNode(ArrayList<StatementNode> list) {
		super(null, list, null);
		this.list = list;
	}
	
	/**
	 * Returns the list of statement nodes
	 * @Return the list of statement nodes
	 */
	@Override
	public String toString() {
		return "else\n" + this.list + "\n";
	}
}
