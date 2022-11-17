import java.util.ArrayList;

/*
 * Represents the right function
 * @author Kevin Meltzer
 * @version 1.9
 */
public class Right extends BuiltInFunctionNode {
	
	/**
	 * A list of datatype nodes
	 */
	private ArrayList<InterpreterDataType> dataTypeList;
	
	/**
	 * Creates a right node
	 * @param parameters A list of parameters for this function
	 * @param dataTypeList A list of data types
	 */
	public Right(ArrayList<VariableNode> parameters, ArrayList<InterpreterDataType> dataTypeList) {
		super("Right", parameters, false);
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
	 * Executes the right function
	 * @throws Exception 
	 */
	public void Execute() throws Exception {
		String resultString = "";
		if (this.dataTypeList.get(0) instanceof StringDataType && this.dataTypeList.get(1) instanceof IntDataType && this.dataTypeList.get(2) instanceof StringDataType) {
			int strlen = this.dataTypeList.get(0).toString().length();
			for(int i = this.dataTypeList.get(0).toString().length() - Integer.parseInt(this.dataTypeList.get(1).toString()); i < strlen; i++) {
				resultString += this.dataTypeList.get(0).toString().charAt(i);
			}
			this.dataTypeList.set(2, new StringDataType(resultString));	
		} else {
			throw new Exception("Incorrect parameter types for Right");
		}
	}	
	
	/**
	 * Returns this node as a string
	 */
	public String toString() {
		return this.parameters + "\n" + this.dataTypeList;
	}
}
