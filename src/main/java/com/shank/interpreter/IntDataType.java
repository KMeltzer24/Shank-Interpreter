package com.shank.interpreter;

/*
 * Represents a int data type
 * @author Kevin Meltzer
 * @version 1.6
 */
public class IntDataType extends InterpreterDataType {
	
	/**
	 * A int holding the value
	 */
	private int value;
	
	/**
	 * Creates a int data type object
	 * @param value A integer to be put in the object
	 */
	public IntDataType(int value) {
		this.value = value;
	}
	
	/**
	 * Creates a empty int data type object
	 */
	public IntDataType() {

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
		this.value = Integer.parseInt(input);
	}
}
