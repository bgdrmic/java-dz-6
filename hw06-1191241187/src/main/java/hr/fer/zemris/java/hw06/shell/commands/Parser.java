package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

/**
 * A simple parser class.
 * @author Božidar Grgur Drmić
 *
 */
public class Parser {

	/**
	 * Symbol for escaping.
	 */
	public static final char ESC = '\\';
	
	/**
	 * A method which parses one path from the given string.
	 * @param arg - string which is parsed.
	 * @return pair of path which was parsed and the rest of the string which hasn't yet been parsed.
	 * @throws InvalidPathException if the path is wrong.
	 */
	public static Pair readPath(String arg) {
		int index = arg.length() - arg.stripLeading().length();
		
		boolean escaped = false;
		boolean quote = false;
		
		StringBuilder sb = new StringBuilder();
		
		for(; index < arg.length(); index++) {			
			char c = arg.charAt(index); 	
			
			if(escaped) {
				escaped = false;
				if(c != '"' && c != ESC) {
					sb.append(ESC);
				}
				sb.append(c);
				continue;
			}
			
			if(c == '"') {
				if(quote) {
					quote = false;
				} else {
					quote = true;
				}
				if(quote == false) {
					if(index+1 != arg.length() && arg.charAt(index+1) != ' ') {
						throw new InvalidPathException(null, null);
					}
				}
				continue;
			}
			if(c == ESC) {
				if(quote) {
					escaped = true;
				} else {
					sb.append(ESC);
				}
				continue;
			}
			if(Character.isWhitespace(c) && !quote) {
				index++;
				break;
			}
			sb.append(c);
		}
		arg = arg.substring(index);
		return new Pair(arg, Paths.get(sb.toString()));
	}
}
