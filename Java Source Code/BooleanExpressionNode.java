
/**
 * Represents a Boolean expression node
 * @author Kevin Meltzer
 * @version 1.5
 */
public class BooleanExpressionNode extends Node {
	
	/**
	 * The left expression of the node
	 */
	private Node left;
	
	/**
	 * The right expression of the node
	 */
	private Node right;
	
	/**
	 * The condition of the node
	 */
	private String condition;
	
	/**
	 * Gets the left node of the expression
	 * @return The left node of the expression
	 */
	public Node getLeft() {
		return this.left;
	}
	
	/**
	 * Gets the right node of the expression
	 * @return The right node of the expression
	 */
	public Node getRight() {
		return this.right;
	}
	
	/**
	 * Gets the condition of the expression
	 * @return The condition of the expression
	 */
	public String getCondition() {
		return this.condition;
	}
	
	/**
	 * Creates a BooleanExpression node
	 * @param left The left expression of the node
	 * @param right The right expression of the node
	 * @param condition The condition of the node
	 */
	public BooleanExpressionNode(Node left, String condition, Node right) {
		this.left = left;
		this.condition = condition;
		this.right = right;
	}
	
	/**
	 * Creates a empty BooleanExpressionNode
	 */
	public BooleanExpressionNode() {
		
	}

	/**
	 * Returns the left, right and condition of the node
	 * @return the left, right and condition of the node
	 */
	public String toString() {
		return left + " " + condition + " " + right;
	}
}