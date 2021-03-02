package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class TreeCommand implements ShellCommand {

	private void printTree(Environment env, Path path, int depth) {		
		if(!Files.exists(path) || !Files.isDirectory(path)) {
			env.writeln("There's no such direcotry.");
			return;
		}
		
		env.writeln(" ".repeat(depth) + path.getFileName());
		
		File[] children = path.toFile().listFiles();
		
		if(children == null) {
			return;
		}
		
		for(var child : children) {
			path = child.toPath();
			if(Files.isDirectory(path)) {
				printTree(env, path, depth + 2);
				continue;
			} 
			
			env.writeln(" ".repeat(depth+2) + path.getFileName());
		}
	}
	
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Path path = null;
		try {
			path = Parser.readPath(arguments).getPath();
		} catch(ShellIOException e) {
			throw e;
		} catch(Exception e) {
			env.writeln("Something went wrong");
		}

		printTree(env, path, 0);
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		var list = new ArrayList<String>();
		list.add("Expects a single argument: directory name and prints it's tree structure.");
		return Collections.unmodifiableList(list);
	}

}
