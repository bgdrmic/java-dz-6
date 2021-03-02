package hr.fer.zemris.java.hw06.shell.commands;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CopyCommand implements ShellCommand {

	private static final int BUFFER_LENGTH = 4096;
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Path origin = null, destination = null;

		try {
			Pair pair = Parser.readPath(arguments); 
			origin = pair.getPath();
			pair = Parser.readPath(pair.getRemainingString()); 
			destination = pair.getPath();
			arguments = pair.getRemainingString().strip();
			
			if(Files.exists(destination) && Files.isSameFile(origin, destination)) {
				return ShellStatus.CONTINUE;
			}
		} catch(ShellIOException e) {
			throw e;
		} catch(Exception e) {
			env.writeln("Wrong path");
			return ShellStatus.CONTINUE;
		}
		
		if(arguments != "") {
			env.writeln("Too many arguments");
			return ShellStatus.CONTINUE;
		}
		
		if(Files.exists(destination) && Files.isDirectory(destination)) {
			destination = Paths.get(destination.toString() + "/" + destination.toString());
		}
		if(Files.exists(destination)) {
			env.writeln("This file already exists. Do you want to overwrite? [Y|N]");
			env.write(env.getPromptSymbol().toString());
			
			String s = env.readLine();
			if(s.equals("N")) {
				return ShellStatus.CONTINUE;
			}
		}
		
		try {
			Files.deleteIfExists(destination);
			var input = new FileInputStream(origin.toString());
			var output = new FileOutputStream(destination.toString());
			byte[] buffer = new byte[BUFFER_LENGTH];			
			int a = 0;
			
			while ((a = input.read(buffer)) != -1) {
				output.write(buffer, 0, a);
			}
			
			input.close();
			output.close();
		} catch(ShellIOException e) {
			throw e;
		} catch (Exception e) {
			env.writeln("Something went wrong!");
		}	
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		var list = new ArrayList<String>();
		list.add("takes a single argument – directory – and writes a directory listing (not recursive)");
		return Collections.unmodifiableList(list);
	}

}
