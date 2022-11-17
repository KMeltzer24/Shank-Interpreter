import java.util.ArrayList;

/*
 * Represents a function node.
 * @author Kevin Meltzer
 * @version 1.6
 */
public class FunctionNode extends CallableNode {

	/**
	 * A list of the local variables of the function
	 */
	private ArrayList<VariableNode> localVariables = new ArrayList<VariableNode>();
	
	/**
	 * A list of the statements of the function
	 */
	private ArrayList<StatementNode> statements = new ArrayList<StatementNode>();
	
	/**
	 * Creates a function node with a name, parameters and local variables
	 * @param functionName Name of the function
	 * @param parameters Parameters of the function
	 * @param localVariables Local variables of the function
	 * @param statements Statements of the function
	 */
	public FunctionNode(String functionName, ArrayList<VariableNode> parameters, ArrayList<VariableNode> localVariables, ArrayList<StatementNode> statements) {
		super(functionName, parameters);
		this.localVariables = localVariables;
		this.statements = statements;
	}
	
	/**
	 * Gets the name of the function
	 * @return A string with the name of the function
	 */
	public String getFunctionName() {
		return this.functionName;
	}
	
	/**
	 * Gets the parameters of the function
	 * @return The parameters of the function
	 */
	public ArrayList<VariableNode> getParameters() {
		return this.parameters;
	}
	
	/**
	 * Gets the local variables of the function
	 * @return The local variables of the function
	 */
	public ArrayList<VariableNode> getLocalVars() {
		return this.localVariables;
	}
	
	/**
	 * Gets the statements of the function
	 * @return The statements of the function
	 */
	public ArrayList<StatementNode> getStatements() {
		return this.statements;
	}
	
	/**
	 * Returns the fields of a function node object as a string
	 * @return The name of the function, parameters, local variables and statements
	 */
	public String toString() {
		return "\n\n" + functionName + "'s parameters are \n\n" + parameters + "\n\nIt's local variables are \n\n" + localVariables + "\n\nAnd it's statements are \n\n" + statements;
	}
}
