import java.util.ArrayList;

/*
 * Represents a callable node.
 * @author Kevin Meltzer
 * @version 1.6
 */
public abstract class CallableNode extends Node {

	/**
	 * Creates a Callable node
	 * @param functionName the name of the function
	 * @param parameters A list of parameters of the function
	 */
	public CallableNode(String functionName, ArrayList<VariableNode> parameters) {
		this.functionName = functionName;
		this.parameters = parameters;
	}
	
	/**
	 * The name of the function
	 */
	protected String functionName;
	
	/**
	 * A list of the parameters of the function
	 */
	protected ArrayList<VariableNode> parameters = new ArrayList<VariableNode>();

}
