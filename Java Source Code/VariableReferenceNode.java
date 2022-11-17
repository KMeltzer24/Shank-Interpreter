
/*
 * Represents a variable reference node.
 * @author Kevin Meltzer
 * @version 1.4
 */
public class VariableReferenceNode extends Node {

	/**
	 * The name of the variable being referenced
	 */
	private String variableName;
	
	/**
	 * Creates a Variable Reference node with the name of the variable being referenced
	 * @param variableName Name of the variable being referenced
	 */
	public VariableReferenceNode(String variableName) {
		this.variableName = variableName;
	}

	/**
	 * Returns the variable name
	 * @return The name of the variable
	 */
	public String toString() {
		return this.variableName;
	}
}
