import java.util.ArrayList;

/**
 * Represents a If loop node
 * @author Kevin Meltzer
 * @version 1.5
 */
public class IfNode extends StatementNode {
	
	/**
	 * The BooleanExpression of the node
	 */
	private BooleanExpressionNode exp;
	
	/**
	 * The list of statement nodes
	 */
	private ArrayList<StatementNode> list;
	

	/**
	 * The IfNode of the node
	 */
	private IfNode ifnode;

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
	 * Gets the chained if of the node
	 * @return The chained if of the node
	 */
	public IfNode getIfNode() {
		return this.ifnode;
	}
	
	/**
	 * Creates a If node
	 * @param exp The BooleanExpression of the node
	 * @param list The list of statement nodes
	 */
	public IfNode(BooleanExpressionNode exp, ArrayList<StatementNode> list, IfNode ifnode) {
		this.exp = exp;
		this.list = list;
		this.ifnode = ifnode;
	}
	
	/**
	 * Returns the BooleanExpression and the list of statement nodes
	 * @Return the BooleanExpression and the list of statement nodes
	 */
	public String toString() {
		if (ifnode == null) {
			return "if (" + exp + ")\n" + "then\n" + list + "\n";
		} else {
			return "if (" + exp + ")\n" + "then\n" + list + ifnode;
		}
	}
}
