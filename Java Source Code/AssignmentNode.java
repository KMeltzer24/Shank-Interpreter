
/*
 * Represents a assignment node.
 * @author Kevin Meltzer
 * @version 1.4
 */
public class AssignmentNode extends StatementNode {

	/**
	 * The target node
	 */
	private VariableReferenceNode target;
	
	/**
	 * The expression node
	 */
	private Node expression;
	
	/**
	 * Gets the target of the node
	 * @return The target variable of the assignment node
	 */
	public VariableReferenceNode getTarget() {
		return this.target;
	}
	
	/**
	 * Gets the expression of the node
	 * @return The expression node of the assignment
	 */
	public Node getExpression() {
		return this.expression;
	}
	
	/**
	 * Creates a assignment node with a target and a expression
	 * @param target A reference to a variable node
	 * @param expression A AST node which holds the expression
	 */
	public AssignmentNode(VariableReferenceNode target, Node expression) {
		this.target = target;
		this.expression = expression;
	}
	
	/**
	 * Returns the target and expression of this node as a string
	 * @return The target and expression of the node
	 */
	public String toString() {
		return target + " := " + expression + "\n";
	}
}
