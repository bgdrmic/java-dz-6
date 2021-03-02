package hr.fer.zemris.java.hw06.shell;

/**
 * ShellStatus is an enumeration which represents the status of the shell.
 * @author JohnDoe
 *
 */
public enum ShellStatus {
	
	/**
	 * Another command should be read.
	 */
	CONTINUE,
	
	/**
	 * Shell should be terminated.
	 */
	TERMINATE
}
