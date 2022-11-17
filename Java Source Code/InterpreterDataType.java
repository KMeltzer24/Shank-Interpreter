
/*
 * Represents a interpreter data type
 * @author Kevin Meltzer
 * @version 1.6
 */
public abstract class InterpreterDataType {
	
	/**
	 * Returns the node as a string
	 */
	public abstract String toString();
	
	/**
	 * Sets the value of the data type by parsing the string
	 * @param input A string desired to be the value
	 */
	public abstract void fromString(String input);
}
