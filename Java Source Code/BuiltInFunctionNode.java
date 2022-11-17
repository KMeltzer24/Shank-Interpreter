import java.util.ArrayList;

/*
 * Represents a built in function node.
 * @author Kevin Meltzer
 * @version 1.6
 */
public abstract class BuiltInFunctionNode extends CallableNode {
	
	/**
	 * Creates a built in function node object
	 * @param functionName The name of the function
	 * @param parameters A list of parameters of the funciton
	 * @param isVariadic A boolean containing whether or not the function is variadic
	 */
	public BuiltInFunctionNode(String functionName, ArrayList<VariableNode> parameters, boolean isVariadic) {
		super(functionName, parameters);
		this.isVariadic = isVariadic;
		
	}
	
	/**
	 * A boolean containing whether or not this function is variadic
	 */
	private boolean isVariadic;
	
	/**
	 * Gets whether the function is variadic
	 * @return A boolean containing whether the function is variadic
	 */
	public boolean isVariadic() {
		return this.isVariadic;
	}
	
	/**
	 * Gets parameters and changes var parameters
	 * @return A list of parameter nodes
	 * @throws Exception 
	 */
	public abstract void Execute() throws Exception;
}