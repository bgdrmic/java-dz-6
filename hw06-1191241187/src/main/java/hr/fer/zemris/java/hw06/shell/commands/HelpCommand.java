package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class HelpCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {		
		arguments = arguments.strip();
		
		if(arguments == "") {
			for(var command : env.commands().values()) {
				System.out.println(command.getCommandName());
			}
			return ShellStatus.CONTINUE;
		} 
		
		for(var command : env.commands().values()) {
			if(command.getCommandName().equals(arguments)) {
				for(var line : command.getCommandDescription()) {
					env.writeln(line);
				}
				return ShellStatus.CONTINUE;
			}
		}
		
		env.writeln("No such command");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		ArrayList<String> description = new ArrayList<>();
		description.add("If called with no arguments, it lists names of all supported commands.");
		description.add("If called with single argument, it prints name and the description of selected command (or print appropriate error message if no such command exists).");
		return Collections.unmodifiableList(description);
	}

}
