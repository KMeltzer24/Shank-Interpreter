/**
 * A token object which has a enum state and a value which holds numbers, operations or reserved words
 * @author Kevin Meltzer
 * @version 1.6
 */
public class Token {
	
	/**
	 * Makes a enum with all of the possible valid input states
	 */
	public enum state {
		NUMBER,
		DECIMAL,
		PLUS,
		MINUS,
		TIMES,
		DIVIDES,
		ENDOFLINE,
		LPAREN,
		RPAREN,
		IDENTIFIER,
		DEFINE,
		INTEGER,
		REAL,
		BEGIN,
		END,
		SEMICOLON,
		COLON,
		EQUAL,
		COMMA,
		VARIABLES,
		CONSTANTS,
		ASSIGNMENT,
		IF, 
		THEN,
		ELSE, 
		ELSIF, 
		FOR, 
		FROM, 
		TO, 
		WHILE, 
		REPEAT, 
		UNTIL, 
		MOD,
		GREATER,
		LESS, 
		GREATEROREQUAL, 
		LESSOREQUAL, 
		NOTEQUAL,
		VAR,
		TRUE,
		FALSE,
		STRING,
		CHAR,
		BOOLEAN,
		STRINGCONTENTS,
		CHARCONTENTS

	}
	/**
	 * Holds the numbers or operator which are store in a token
	 */ 
	private String value;
	
	/**
	 * Holds the state of the token
	 */ 
	private state state;
	
	/**
	 * A empty token constructor 
	 */
	public Token() {}
	
	/**
	 * A token constructor that takes a string and a enum state as input
	 * @param state Current state of token
	 * @param value Numbers, operator or reserved word
	 */
	public Token(state state, String value) {
		this.state = state;
		this.value = value;
	}
	
	/**
	 * Retrieves the state of a token
	 * @return The state of a token
	 */
	public state getState() {
		return this.state;
	}
	
	/**
	 * Sets the state of a token
	 * @param state The state of a token
	 */
	public void setState(state state) {
		this.state = state;
	}
	
	/**
	 * Returns the value of a token
	 * @return Value of the token
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Outputs the value of the token in a string
	 * @output The value of the token in a string
	 */
	public String toString() {
		if (this.state == state.NUMBER) {
			return " NUMBER (" + this.value + ")";
		} else if (this.state == state.PLUS) {
			return " PLUS";
		} else if (this.state == state.MINUS) {
			return " MINUS";
		} else if (this.state == state.TIMES) {
			return " TIMES";
		} else if (this.state == state.DIVIDES) {
			return " DIVDES";
		} else if (this.state == state.LPAREN) {
			return " (";
		} else if (this.state == state.RPAREN) {
			return " )";
		} else if (this.state == state.GREATER) {
			return ">";
		} else if (this.state == state.LESS) {
			return "<";
		} else if (this.state == state.GREATEROREQUAL) {
			return ">=";
		} else if (this.state == state.LESSOREQUAL) {
			return "<=";
		} else if (this.state == state.NOTEQUAL) {
			return "<>";
		} else {
			return this.value;
		}
	}
}
