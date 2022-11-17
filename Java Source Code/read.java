import java.util.ArrayList;
import java.util.Scanner;

/*
 * Represents the read function
 * @author Kevin Meltzer
 * @version 1.9
 */
public class read extends BuiltInFunctionNode {
	
	/**
	 * A list of datatype nodes
	 */
	private ArrayList<InterpreterDataType> dataTypeList;
	
	/**
	 * Creates a read node
	 * @param parameters A list of parameters for this function
	 * @param dataTypeList A list of date types
	 */
	public read(ArrayList<VariableNode> parameters, ArrayList<InterpreterDataType> dataTypeList) {
		super("read", parameters, true);
		this.dataTypeList = dataTypeList;
	}
	
	/**
	 * Gets the list of interpreter data types
	 * @return A list of interpreter data types
	 */
	public ArrayList<InterpreterDataType> getDataTypesList(){
		return this.dataTypeList;
	}
	
	/**
	 * Executes the read function
	 * @throws Exception 
	 */
	public void Execute() throws Exception {
		String number;
		for (int i = 0; i < this.dataTypeList.size(); i++) {
			if (!(this.dataTypeList.get(0) instanceof FloatDataType || this.dataTypeList.get(0) instanceof IntDataType || this.dataTypeList.get(0) instanceof StringDataType || this.dataTypeList.get(0) instanceof CharDataType || this.dataTypeList.get(0) instanceof BooleanDataType)) {
				throw new Exception("Not a valid type for read");
			} 
		}
		Scanner keyboard = new Scanner(System.in);
		for (int i = 0; i < this.dataTypeList.size(); i++) {
			if (this.dataTypeList.get(i) instanceof IntDataType) {
				System.out.print("Enter a integer: ");
			} else if (this.dataTypeList.get(i) instanceof FloatDataType) {
				System.out.print("Enter a real: ");				
			} else if (this.dataTypeList.get(i) instanceof StringDataType) {
				System.out.print("Enter a string: ");
			} else if (this.dataTypeList.get(i) instanceof CharDataType) { 
				System.out.print("Enter a character: ");
			} else {
				System.out.print("Enter a boolean: ");
			}
			number = keyboard.nextLine();
			try {
				this.dataTypeList.get(i).fromString(number);
			} catch (Exception E){
				throw new Exception("Not a valid read input");
			}
			if (this.dataTypeList.get(i) instanceof IntDataType) {
				this.dataTypeList.set(i, new IntDataType(Integer.parseInt(this.dataTypeList.get(i).toString())));
			} else if (this.dataTypeList.get(i) instanceof FloatDataType) {
				this.dataTypeList.set(i, new FloatDataType(Float.parseFloat(this.dataTypeList.get(i).toString())));
			} else if (this.dataTypeList.get(i) instanceof StringDataType) {
				this.dataTypeList.set(i, new StringDataType(this.dataTypeList.get(i).toString()));
			} else if (this.dataTypeList.get(i) instanceof CharDataType) { 
				this.dataTypeList.set(i, new CharDataType(this.dataTypeList.get(i).toString().charAt(0)));
			} else {
				this.dataTypeList.set(i, new BooleanDataType(Boolean.parseBoolean(this.dataTypeList.get(i).toString())));
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