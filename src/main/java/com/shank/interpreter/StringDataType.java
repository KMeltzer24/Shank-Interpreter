package com.shank.interpreter;

/*
 * Represents a String data type
 * @author Kevin Meltzer
 * @version 1.9
 */
public class StringDataType extends InterpreterDataType {
	
	/**
	 * A String holding the value
	 */
	private String value;
	
	/**
	 * Creates a String data type object
	 * @param value A String to be put in the object
	 */
	public StringDataType(String value) {
		this.value = value;
	}
	
	/**
	 * Creates a empty String data type object
	 */
	public StringDataType() {

	}

	/**
	 * Returns the object as a string
	 * @return The object as a string
	 */
	@Override
	public String toString() {
		return value;
	}
	
	/**
	 * Sets the value of the data type from a string
	 * @param input A string desired to be the value
	 */
	@Override
	public void fromString(String input) {
		this.value = input;
	}
}
