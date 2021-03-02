package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;

/**
 * A class which represents a pair of string and path.
 * @author Božidar Grgur Drmić
 *
 */
public class Pair {

	/**
	 * Read-only property which represents the string part of pair.
	 */
	private String remainingString;
	/**
	 * Read-only property which represents the path part of pair.
	 */
	private Path path;
		
	/**
	 * A getter for the remainingString variable.
	 * @return remainingString
	 */
	public String getRemainingString() {
		return remainingString;
	}
	/**
	 * A getter for the path variable.
	 * @return path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * A constructor which accepts all the relevant fields as arguments.
	 * @param remainingString - remainingString variable is set to this value.
	 * @param path - path variable is set to this value.
	 */
	public Pair(String remainingString, Path path) {
		super();
		this.remainingString = remainingString;
		this.path = path;
	}	
	
}
