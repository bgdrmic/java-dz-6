package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CharsetsCommand implements ShellCommand {
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		for(var charset : Charset.availableCharsets().values()) {
			System.out.println(charset.toString());
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		ArrayList<String> description = new ArrayList<>();
		description.add("Lists names of supported charsets for your Java platform.");
		return Collections.unmodifiableList(description);
	}

}
