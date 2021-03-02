package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * An interface for communication with the user.
 * @author Božidar Grgur Drmić
 *
 */
public interface Environment {

	/**
	 * A method which reads one line from console.
	 * @return read line.
	 * @throws ShellIOException if error occurred.
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * A method which writes some text to console.
	 * @param text - text to be printed
	 * @throws ShellIOException if error occurred.
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * A method which writes one line to console.
	 * @param text - text to be printed
	 * @throws ShellIOException if error occurred.
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * A getter method for map of commands
	 * available in this shell.
	 * @return commands.
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * A getter for MULTILINE symbol.
	 * @return MULTILINE symbol.
	 */
	Character getMultilineSymbol();	

	/**
	 * A setter for MULTILINE symbol.
	 * @param symbol - MULTILINE symbol is set to this value.
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * A getter for PROMPT symbol.
	 * @return PROMPT symbol.
	 */
	Character getPromptSymbol();
	
	/**
	 * A setter for PROMPT symbol.
	 * @param symbol - PROMPT symbol is set to this value.
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * A getter for MORELINES symbol.
	 * @return MORELINES symbol.
	 */
	Character getMorelinesSymbol();
	
	/**
	 * A setter for MORELINES symbol.
	 * @param symbol - MORELINES symbol is set to this value.
	 */
	void setMorelinesSymbol(Character symbol);
	
}
