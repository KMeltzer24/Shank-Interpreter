package com.shank.builtin;

import java.util.ArrayList;
import java.util.Scanner;

import com.shank.interpreter.BooleanDataType;
import com.shank.interpreter.CharDataType;
import com.shank.interpreter.FloatDataType;
import com.shank.interpreter.IntDataType;
import com.shank.interpreter.InterpreterDataType;
import com.shank.interpreter.StringDataType;
import com.shank.nodes.BuiltInFunctionNode;
import com.shank.nodes.VariableNode;

/*
 * Represents the read function
 * @author Kevin Meltzer
 * @version 1.9
 */
public class read extends BuiltInFunctionNode {
	
	/**
	 * A list of datatype nodes
	 */
	private final ArrayList<InterpreterDataType> dataTypeList;
	
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
	@Override
	public void Execute() throws Exception {
		String number;
		for (InterpreterDataType dataType : this.dataTypeList) {
			if (!(dataType instanceof FloatDataType || dataType instanceof IntDataType || dataType instanceof StringDataType || dataType instanceof CharDataType || dataType instanceof BooleanDataType)) {
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
		}
	}
	
	/**
	 * Returns this node as a string
	 */
	@Override
	public String toString() {
		return this.dataTypeList + "";
	}
}