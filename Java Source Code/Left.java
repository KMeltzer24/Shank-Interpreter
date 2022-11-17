import java.util.ArrayList;

/*
 * Represents the Left function
 * @author Kevin Meltzer
 * @version 1.9
 */
public class Left extends BuiltInFunctionNode {
	
	/**
	 * A list of datatype nodes
	 */
	private ArrayList<InterpreterDataType> dataTypeList;
	
	/**
	 * Creates a Left node
	 * @param parameters A list of parameters for this function
	 * @param dataTypeList A list of data types
	 */
	public Left(ArrayList<VariableNode> parameters, ArrayList<InterpreterDataType> dataTypeList) {
		super("Left", parameters, false);
		this.dataTypeList = dataTypeList;
	}
	
	/**
	 * Gets the parameters of the function
	 * @return A list of variable nodes that represent the parameters of the function
	 */
	public ArrayList<VariableNode> getParameters(){
		return this.parameters;
	}
	
	/**
	 * Gets a list of data types 
	 * @return A list of interpreted data types representing the parameters
	 */
	public ArrayList<InterpreterDataType> getDataTypesList(){
		return this.dataTypeList;
	}
	
	/**
	 * Executes the Left function
	 * @throws Exception 
	 */
	public void Execute() throws Exception {
		String resultString = "";
		if (this.dataTypeList.get(0) instanceof StringDataType && this.dataTypeList.get(1) instanceof IntDataType && this.dataTypeList.get(2) instanceof StringDataType) {
			for(int i = 0; i < Integer.parseInt(this.dataTypeList.get(1).toString()); i++) {
				resultString += this.dataTypeList.get(0).toString().charAt(i);
			}
			this.dataTypeList.set(2, new StringDataType(resultString));	
		} else {
			throw new Exception("Incorrect parameter types to Left");
		}
	}	
	
	/**
	 * Returns this node as a string
	 */
	public String toString() {
		return this.parameters + "\n" + this.dataTypeList;
	}
}
