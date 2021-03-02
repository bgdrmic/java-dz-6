package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class SymbolCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.stripLeading();
		String[] s = arguments.split("\\s");
		if(s.length < 2) {
			arguments = arguments.strip();
			switch(arguments) {
			case "PROMPT":
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
				return ShellStatus.CONTINUE;
			case "MORELINES":
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
				return ShellStatus.CONTINUE;
			case "MULTILINE":
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
				return ShellStatus.CONTINUE;
			default:
				env.writeln("Wrong format");
				return ShellStatus.CONTINUE;
			}
		}		
		
		if(s.length > 2 || s[1].length() > 1) {
			env.writeln("Wrong format");
			return ShellStatus.CONTINUE;
		}
		switch(s[0]) {
		case "PROMPT":
			env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() + "' to '" + s[1] + "'");
			env.setPromptSymbol(s[1].charAt(0));
			return ShellStatus.CONTINUE;
		case "MORELINES":
			env.writeln("Symbol for MORELINES changed from '" + env.getMorelinesSymbol() + "' to '" + s[1] + "'");
			env.setMorelinesSymbol(s[1].charAt(0));
			return ShellStatus.CONTINUE;
		case "MULTILINE":
			env.writeln("Symbol for MULTILINE changed from '" + env.getMultilineSymbol() + "' to '" + s[1] + "'");
			env.setMultilineSymbol(s[1].charAt(0));
			return ShellStatus.CONTINUE;
		default:
			env.writeln("Wrong format");
			return ShellStatus.CONTINUE;
		}
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		var list = new ArrayList<String>();
		list.add("Takes one or two arguments. First argument must be symbol name and second argument can be one character.");
		list.add("If one argument was entered than the current value of that symbol is printed.");
		list.add("If second argument was entered than the current value of that symbol is changed to that character");
		return Collections.unmodifiableList(list);
	}

}
