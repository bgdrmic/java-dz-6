package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CatCommand implements ShellCommand {
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Path file = null;
		Charset charset = Charset.defaultCharset();
		try {
			Pair pair = Parser.readPath(arguments);
			file = pair.getPath();
			arguments = pair.getRemainingString().strip();
			if(arguments != "") {
				charset = Charset.forName(arguments);
			}
		} catch(ShellIOException e) {
			throw e;
		} catch(Exception e) {
			env.writeln("Wrong path format");
			return ShellStatus.CONTINUE;
		}
		
		if(!Files.exists(file) || Files.isDirectory(file)) {
			env.writeln("There is no such file");
			return ShellStatus.CONTINUE;
		}
		
		try {
			var input = new FileInputStream(file.toString());
			InputStreamReader inputStream = new InputStreamReader(input, charset);
			BufferedReader buffReader = new BufferedReader(inputStream);
			String line = "";
			
			while((line = buffReader.readLine()) != null) {
				env.writeln(line);
			}
			
			input.close();
		} catch(ShellIOException e) {
			throw e;
		} catch (Exception e) {
			env.writeln("Something went wrong!");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		var list = new ArrayList<String>();
		list.add("Takes one or two arguments. The first argument is path to some file and is mandatory.");
		list.add("The second argument is charset name that should be used to interpret chars from bytes.");
		list.add("If not provided, a default platform charset should be used. This command opens given file and writes its content to console.");
		return Collections.unmodifiableList(list);
	}

}
