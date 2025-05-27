package com.shank.builtin;

import java.util.ArrayList;
import java.util.Random;

import com.shank.interpreter.IntDataType;
import com.shank.interpreter.InterpreterDataType;
import com.shank.nodes.BuiltInFunctionNode;
import com.shank.nodes.VariableNode;

/*
 * Represents the get random function
 * @author Kevin Meltzer
 * @version 1.7
 */
public class getRandom extends BuiltInFunctionNode {
	
	/**
	 * A list of datatype nodes
	 */
	private final ArrayList<InterpreterDataType> dataTypeList;
	
	/**
	 * Creates a getRandom node
	 * @param parameters A list of parameters for this function
	 * @param dataTypeList A list of date types
	 */
	public getRandom(ArrayList<VariableNode> parameters, ArrayList<InterpreterDataType> dataTypeList) {
		super("getRandom", parameters, false);
		this.dataTypeList = dataTypeList;
	}
	
	/**
	 * Gets the parameters of this function
	 * @return A list of variable nodes representing parameters
	 */
	public ArrayList<VariableNode> getParameters(){
		return this.parameters;
	}
	
	/**
	 * A list of data types representing the parameters of this function
	 * @return A list of interpret data types
	 */
	public ArrayList<InterpreterDataType> getDataTypesList(){
		return this.dataTypeList;
	}
	
	/**
	 * Executes the get random function
	 * @throws Exception 
	 */
    @Override
	public void Execute() throws Exception {
		Random rand = new Random();
		if (this.dataTypeList.get(0) instanceof IntDataType) {
			this.dataTypeList.set(0, new IntDataType(rand.nextInt(Integer.MAX_VALUE)));
		} else {
			throw new Exception("getRandom parameter is not a integer");
		}		
	}	
	
	/**
	 * Returns this node as a string
	 */
	public String toString() {
		return this.dataTypeList + "";
	}
}
