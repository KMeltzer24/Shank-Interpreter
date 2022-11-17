import java.util.ArrayList;

/**
 * Represents a For loop node
 * @author Kevin Meltzer
 * @version 1.5
 */
public class ForNode extends StatementNode {
	
	/**
	 * The VariableReferenceNode of the node
	 */
	private VariableReferenceNode varRef;
	
	/**
	 * The start node of this node
	 */
	private Node start;
	
	/**
	 * The end node of this node
	 */
	private Node end;
	
	/**
	 * The list of statement nodes
	 */
	private ArrayList<StatementNode> list;
	
	/**
	 * Gets the variable reference node of the node
	 * @return The variable reference node
	 */
	public VariableReferenceNode getVar() {
		return this.varRef;
	}

	/**
	 * Gets the starting value of the loop
	 * @return The starting value of the loop
	 */
	public Node getStart() {
		return this.start;
	}

	/**
	 * Gets the end value of the loop
	 * @return The end value of the loop
	 */
	public Node getEnd() {
		return this.end;
	}

	/**
	 * Gets the list of statements nodes of the node
	 * @return The list of statements nodes of the node
	 */
	public ArrayList<StatementNode> getList() {
		return this.list;
	}

	/**
	 * Creates a For node
	 * @param var The VariableReferenceNode of the node
	 * @param start The start node of this node
	 * @param end The end node of this node
	 * @param list The list of statement nodes
	 */
	public ForNode(VariableReferenceNode var, Node start, Node end, ArrayList<StatementNode> list) {
		this.varRef = var;
		this.start = start;
		this.end = end;
		this.list = list;
	}
	
	/**
	 * Returns the VariableReferenceNode, start node, end node and the list of statement nodes
	 * @return the VariableReferenceNode, start node, end node and the list of statement nodes
	 */
	public String toString() {
		return "For " + varRef + " from " + start +  " to " + end + "\n" + list + "\n";
	}
}
