import java.util.ArrayList;

/*
 * Represents the real to integer function
 * @author Kevin Meltzer
 * @version 1.7
 */
public class realToInteger extends BuiltInFunctionNode {
	
	/**
	 * A list of datatype nodes
	 */
	private ArrayList<InterpreterDataType> dataTypeList;
	
	/**
	 * Creates a realToInteger node
	 * @param parameters A list of parameters for this function
	 * @param dataTypeList A list of date types
	 */
	public realToInteger(ArrayList<VariableNode> parameters, ArrayList<InterpreterDataType> dataTypeList) {
		super("realToInteger", parameters, false);
		this.dataTypeList = dataTypeList;
	}
	
	/**
	 * Gets the parameters of a function
	 * @return A list of variable nodes representing the parameters
	 */
	public ArrayList<VariableNode> getParameters(){
		return this.parameters;
	}
	
	/**
	 * Gets a list of data types
	 * @return A list of interpreter data types representing the parameters of a function
	 */
	public ArrayList<InterpreterDataType> getDataTypesList(){
		return this.dataTypeList;
	}
	
	/**
	 * Executes the real to integer function
	 * @throws Exception 
	 */
	public void Execute() throws Exception {
		if (this.dataTypeList.get(0) instanceof FloatDataType) {
			if (this.dataTypeList.get(1) instanceof IntDataType) { 
				this.dataTypeList.set(1, new IntDataType((int)Float.parseFloat(this.dataTypeList.get(0).toString())));
			} else {
				throw new Exception("The second parameter in integerToReal is not a integer");
			}		
		} else {
			throw new Exception("The first parameter in realToInteger is not a real");
		}		
	}	
	
	/**
	 * Returns this node as a string
	 */
	public String toString() {
		return this.dataTypeList + "";
	}
}
