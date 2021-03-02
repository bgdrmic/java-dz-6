package hr.fer.zemris.java.hw06.shell;

import java.util.List;

public interface ShellCommand {

	/**
	 * A method which executes a command via some environment.
	 * It returns shell status.
	 * 
	 * @param env - environment through which the shell communicates with the user. 
	 * @param arguments - string which contains everything entered after command name.
	 * @return CONTINUE if another command should be entered, TERMINNATE if not.
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * A getter method for command name.
	 * @return command name.
	 */
	String getCommandName();
	
	/**
	 * A method which returns the command description in the format of list of strings.
	 * @return list of strings which contain command description.
	 */
	List<String> getCommandDescription();
	
}
