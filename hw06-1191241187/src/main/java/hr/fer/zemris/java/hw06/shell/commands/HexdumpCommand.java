package hr.fer.zemris.java.hw06.shell.commands;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.crypto.Util;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class HexdumpCommand implements ShellCommand {
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		Pair pair = Parser.readPath(arguments);
		Path file = pair.getPath();
		arguments = pair.getRemainingString();
		
		try {
			var input = new FileInputStream(file.toString());
			int counter = 0;
			byte[] buffer = new byte[16];
			int bufferLength = 0;
			
			while((bufferLength = input.read(buffer)) != -1) {
				env.write("0".repeat(8 - String.valueOf(counter).length()) + String.format("%d:", counter++));
				String s = Util.bytetohex(buffer);
				
				for(int i = 0; i < 8 && i < bufferLength; i++) {
					env.write(" " + s.substring(2*i, 2*i + 2));
				}
				if(bufferLength < 8) {
					env.write("   ".repeat(8-bufferLength));					
				}
				env.write("|");
				
				for(int i = 8; i < bufferLength; i++) {
					env.write(s.substring(2*i, 2*i + 2) + " ");
				}
				
				if(bufferLength < 16) {
					if(bufferLength <= 8) {
						env.write("   ".repeat(8));
					} else {
						env.write("   ".repeat(16-bufferLength));
					}
				}
				env.write("| ");
				for(int i = 0; i < bufferLength; i++) {
					if(buffer[i] < 32 || buffer[i] > 127) {
						env.write(".");
					} else {
						env.write(String.valueOf((char) buffer[i]));
					}
				}
				env.writeln("");
			}
			input.close();
		} catch(ShellIOException e) {
			throw e;
		} catch(Exception e) {
			env.writeln("Something went wrong");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		var list = new ArrayList<String>();
		list.add("Expects a single argument: file name, and produces hex-output");
		return Collections.unmodifiableList(list);
	}

}
