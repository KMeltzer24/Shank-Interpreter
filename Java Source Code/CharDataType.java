
/*
 * Represents a char data type
 * @author Kevin Meltzer
 * @version 1.9
 */
public class CharDataType extends InterpreterDataType {
	
	/**
	 * A char holding the value
	 */
	private char value;
	
	/**
	 * Creates a char data type object
	 * @param value A char to be put in the object
	 */
	public CharDataType(char value) {
		this.value = value;
	}
	
	/**
	 * Creates a empty char data type object
	 */
	public CharDataType() {

	}

	/**
	 * Returns the char as a string
	 * @return The char as a string
	 */
	public String toString() {
		return value + "";
	}
	
	/**
	 * Sets the value of the data type from a string
	 * @param input A string desired to be the value
	 */
	public void fromString(String input) {
		this.value = input.charAt(0);
	}
}
