import java.util.ArrayList;

/*
 * Represents the write function
 * @author Kevin Meltzer
 * @version 1.7
 */
public class write extends BuiltInFunctionNode {
	
	/**
	 * A list of datatype nodes
	 */
	private ArrayList<InterpreterDataType> dataTypeList;
	
	/**
	 * Creates a write node
	 * @param parameters A list of parameters for this function
	 * @param dataTypeList A list of date types
	 */
	public write(ArrayList<VariableNode> parameters, ArrayList<InterpreterDataType> dataTypeList) {
		super("write", parameters, true);
		this.dataTypeList = dataTypeList;
	}
	
	/**
	 * Gets the parameters of the function
	 * @return A list of variable nodes representing the parameters
	 */
	public ArrayList<VariableNode> getParameters(){
		return this.parameters;
	}
	
	/**
	 * Executes the write function
	 * @throws Exception 
	 */
	public void Execute() throws Exception {
		for (int i = 0; i < this.dataTypeList.size(); i++) {
			if (!(this.dataTypeList.get(i) instanceof FloatDataType || this.dataTypeList.get(i) instanceof IntDataType || this.dataTypeList.get(i) instanceof StringDataType || this.dataTypeList.get(i) instanceof CharDataType || this.dataTypeList.get(i) instanceof BooleanDataType)) {
				throw new Exception("Not a valid parameter type for write");
			} else {
				System.out.print(this.dataTypeList.get(i).toString() + " ");
			}
			
		}
	}
	
	/**
	 * Returns this node as a string
	 */
	public String toString() {
		return this.dataTypeList + "";
	}
}