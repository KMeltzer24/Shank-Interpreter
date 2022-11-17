
/*
 * Represents a char node.
 * @author Kevin Meltzer
 * @version 1.9
 */
public class CharNode extends Node {

	/**
	 * A char which will be the element of a node
	 */
	private char character;
	
	/**
	 * Creates a CharNode object
	 * @param character A char
	 */
	public CharNode(char character) {
		this.character = character;
	}
	
	/**
	 * Creates a empty CharNode object
	 */
	public CharNode() {

	}
	
	/**
	 * Returns the char of a node
	 * @return A char from a node
	 */
	public char getchar() {
		return this.character;
	}
	
	/**
	 * Retrieves the char of a node and returns it as a string
	 * @return The char from a node as a string
	 */
	public String toString() {
		return this.character + "";
	}
}
