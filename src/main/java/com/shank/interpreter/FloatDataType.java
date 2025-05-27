package com.shank.interpreter;

/*
 * Represents a float data type
 * @author Kevin Meltzer
 * @version 1.7
 */
public class FloatDataType extends InterpreterDataType {
	
	/**
	 * A Float holding the value
	 */
	private Float value;
	
	/**
	 * Creates a float data type
	 * @param value A float to be the value of the data type
	 */
	public FloatDataType(Float value) {
		this.value = value;
	}
	

	/**
	 * Creates a empty float data type
	 */
	public FloatDataType() {
	
	}
	
	
	/**
	 * Returns the object as a string
	 * @return The object as a string
	 */
	@Override
	public String toString() {
		return value + "";
	}
	
	/**
	 * Sets the value of the data type by parsing the string
	 * @param input A string desired to be the value
	 */
	@Override
	public void fromString(String input) {
		this.value = Float.valueOf(input);
	}
}
