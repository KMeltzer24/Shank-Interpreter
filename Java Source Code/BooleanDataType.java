
/*
 * Represents a boolean data type
 * @author Kevin Meltzer
 * @version 1.9
 */
public class BooleanDataType extends InterpreterDataType {
	
	/**
	 * A boolean holding the value
	 */
	private boolean value;
	
	/**
	 * Creates a boolean data type object
	 * @param value A boolean to be put in the object
	 */
	public BooleanDataType(boolean value) {
		this.value = value;
	}
	
	/**
	 * Creates a empty boolean data type object
	 */
	public BooleanDataType() {

	}

	/**
	 * Returns the object as a string
	 * @return The object as a string
	 */
	public String toString() {
		return value + "";
	}
	
	/**
	 * Sets the value of the data type from a string
	 * @param input A string desired to be the value
	 */
	public void fromString(String input) {
		this.value = Boolean.parseBoolean(input);
	}
}
