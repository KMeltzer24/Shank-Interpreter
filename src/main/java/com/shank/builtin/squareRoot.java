package com.shank.builtin;

import java.util.ArrayList;

import com.shank.interpreter.FloatDataType;
import com.shank.interpreter.InterpreterDataType;
import com.shank.nodes.BuiltInFunctionNode;
import com.shank.nodes.VariableNode;

/*
 * Represents the square root function
 * @author Kevin Meltzer
 * @version 1.6
 */
public class squareRoot extends BuiltInFunctionNode {
	
	/**
	 * A list of datatype nodes
	 */
	private final ArrayList<InterpreterDataType> dataTypeList;
	
	/**
	 * Creates a squareRoot node
	 * @param parameters A list of parameters for this function
	 * @param dataTypeList A list of date types
	 */
	public squareRoot(ArrayList<VariableNode> parameters, ArrayList<InterpreterDataType> dataTypeList) {
		super("squareRoot", parameters, false);
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
	 * Executes the square root function
	 * @throws Exception 
	 */
	@Override
	public void Execute() throws Exception {
		if (this.dataTypeList.get(0) instanceof FloatDataType) {
			if (this.dataTypeList.get(1) instanceof FloatDataType) {
				try {
					this.dataTypeList.set(1, new FloatDataType((float)Math.sqrt(Float.parseFloat(this.dataTypeList.get(0).toString()))));
				} catch (Exception E) {
					throw new Exception ("Cannot take squareRoot");
				}
			} else {
				throw new Exception("The second parameter in squareRoot is not a real");
			}		
		} else {
			throw new Exception("The first parameter in squareRoot is not a real");
		}
	}	
	
	/**
	 * Returns this node as a string
	 */
	@Override
	public String toString() {
		return this.parameters + "\n" + this.dataTypeList;
	}
}
