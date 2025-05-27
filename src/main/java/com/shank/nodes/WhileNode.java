package com.shank.nodes;

import java.util.ArrayList;

/**
 * Represents a While loop node
 * @author Kevin Meltzer
 * @version 1.5
 */
public class WhileNode extends StatementNode {
	
	/**
	 * The BooleanExpression of the node
	 */
	private final BooleanExpressionNode exp;
	
	/**
	 * The list of statement nodes
	 */
	private final ArrayList<StatementNode> list;
	
	/**
	 * Gets the boolean expression of the node
	 * @return The boolean expression of the node
	 */
	public BooleanExpressionNode getExp() {
		return this.exp;
	}

	/**
	 * Gets the statements of the node
	 * @return The statements of the node
	 */
	public ArrayList<StatementNode> getList() {
		return this.list;
	}

	/**
	 * Creates a while node
	 * @param exp The BooleanExpression of the node
	 * @param list The list of statement nodes
	 */
	public WhileNode(BooleanExpressionNode exp, ArrayList<StatementNode> list) {
		this.exp = exp;
		this.list = list;
	}
	
	/**
	 * Returns the BooleanExpression and the list of statement nodes
	 * @return the BooleanExpression and the list of statement nodes
	 */
	@Override
	public String toString() {
		if (list == null) {
			return "While " + exp + "\n\n";
		} 
		return "While " + exp + "\n" + list + "\n";
	}
}