import java.util.ArrayList;

/*
 * Represents the integer to real function
 * @author Kevin Meltzer
 * @version 1.7
 */
public class integerToReal extends BuiltInFunctionNode {
	
	/**
	 * A list of datatype nodes
	 */
	private ArrayList<InterpreterDataType> dataTypeList;
	
	/**
	 * Creates a integerToReal node
	 * @param parameters A list of parameters for this function
	 * @param dataTypeList A list of date types
	 */
	public integerToReal(ArrayList<VariableNode> parameters, ArrayList<InterpreterDataType> dataTypeList) {
		super("integerToReal", parameters, false);
		this.dataTypeList = dataTypeList;
	}
	
	/**
	 * Gets the parameters of the function
	 * @return A list of variable nodes representing parameters
	 */
	public ArrayList<VariableNode> getParameters(){
		return this.parameters;
	}
	
	/**
	 * Gets a list of interpreter data types
	 * @return A list of interpreter data types representing the parameters
	 */
	public ArrayList<InterpreterDataType> getDataTypesList(){
		return this.dataTypeList;
	}
	
	/**
	 * Executes the integer to real function
	 * @throws Exception 
	 */
	public void Execute() throws Exception {
		if (this.dataTypeList.get(0) instanceof IntDataType) {
			if (this.dataTypeList.get(1) instanceof FloatDataType) { 
				this.dataTypeList.set(1, new FloatDataType((float)Integer.parseInt(this.dataTypeList.get(0).toString())));
			} else {
				throw new Exception("The second parameter in integerToReal is not a real");
			}		
		} else {
			throw new Exception("The first parameter in integerToReal is not a integer");
		}		
	}	
	
	/**
	 * Returns this node as a string
	 */
	public String toString() {
		return this.dataTypeList + "";
	}
}
