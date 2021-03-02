package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class MkdirCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		Path path = Parser.readPath(arguments).getPath();
		
		try {
			new File(path.toString()).mkdirs();
		} catch(ShellIOException e) {
			throw e;
		} catch (Exception e) {
			env.writeln("Wrong path format!");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		var list = new ArrayList<String>();
		list.add("Takes a single argument: directory name, and creates the appropriate directory structure.");
		return Collections.unmodifiableList(list);
	}

}
