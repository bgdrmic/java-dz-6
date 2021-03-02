package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.*;

/**
 * MyShell is a class which represents a shell console for operating with files and folders.
 * Enter "help" command for list of commands and "help commandName"
 * for specifications of some command.
 * @author Božidar Grgur Drmić
 *
 */
public class MyShell {
	
	/**
	 * Status of the shell.
	 */
	private static ShellStatus status = ShellStatus.CONTINUE;
	/**
	 * Collection of registered commands.
	 */
	private static SortedMap<String, ShellCommand> commands;
	/**
	 * Environment through which this shell communicates with the user.
	 */
	private static Environment environment;
	
	/**
	 * MULTILINE symbol variable.
	 */
	private static char multilineSymbol = '|';
	/**
	 * MORELINES symbol variable.
	 */
	private static char morelinesSymbol = '\\';
	/**
	 * PROMPT symbol variable.
	 */
	private static char promptSymbol = '>';
	
	/**
	 * Implementation of Environment interface.
	 * @author Božidar Grgur Drmić
	 *
	 */
	private static class MyEnvironment implements Environment {

		/**
		 * Scanner for reading users input.
		 */
		Scanner scanner = new Scanner(System.in);
		
		@Override
		public String readLine() throws ShellIOException {
			return scanner.nextLine();
		}

		@Override
		public void write(String text) throws ShellIOException {
			System.out.print(text);			
		}

		@Override
		public void writeln(String text) throws ShellIOException {
			System.out.println(text);
		}

		@Override
		public SortedMap<String, ShellCommand> commands() {
			return Collections.unmodifiableSortedMap(commands);
		}

		@Override
		public Character getMultilineSymbol() {
			return multilineSymbol;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			multilineSymbol = symbol;
		}

		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			promptSymbol = symbol;			
		}

		@Override
		public Character getMorelinesSymbol() {
			return morelinesSymbol;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			morelinesSymbol = symbol;
		}
		
	}
	/**
	 * A method which sets up this shell console.
	 */
	private static void setup() {
		commands = new TreeMap<String, ShellCommand>();
		commands.put("exit", new ExitCommand());
		commands.put("charsets", new CharsetsCommand());
		commands.put("copy", new CopyCommand());
		commands.put("help", new HelpCommand());
		commands.put("hexdump", new HexdumpCommand());
		commands.put("ls", new LsCommand());
		commands.put("mkdir", new MkdirCommand());
		commands.put("tree", new TreeCommand());
		commands.put("cat", new CatCommand());
		commands.put("symbol", new SymbolCommand());
		
		environment = new MyEnvironment();
	}
	
	/**
	 * Main method of this console.
	 * It reads commands.
	 * 
	 * @param args - no effect.
	 */
	public static void main(String[] args) {
		setup();
		environment.writeln("Welcome to MyShell v 1.0");
		
		do {
			try {
				environment.write(environment.getPromptSymbol().toString());
				var sb = new StringBuilder();
				String line = null;
				
				while(true) {
					line = environment.readLine();
					if(line.charAt(line.length()-1) == morelinesSymbol) {
						line = line.substring(0,  line.length()-1);
						sb.append(line);
						environment.write(environment.getMultilineSymbol().toString());
						continue;
					} else {
						sb.append(line);
						break;
					}
				}
				
				line = sb.toString().stripLeading();
				status = ShellStatus.CONTINUE;
				
				for(var command : commands.keySet()) {
					if(line.startsWith(command)) {
						status = commands.get(command).executeCommand(environment, line.substring(command.length()));
						break;
					}
				}
			} catch(ShellIOException e) {
				status = ShellStatus.TERMINATE;
			}			
		} while(status != ShellStatus.TERMINATE);
		
	}	
}
