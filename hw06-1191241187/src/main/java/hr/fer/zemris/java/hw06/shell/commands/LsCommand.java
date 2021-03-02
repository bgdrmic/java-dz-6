package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class LsCommand implements ShellCommand {
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Path path;
		try {
			Pair pair = Parser.readPath(arguments);
			path = pair.getPath();
			arguments = pair.getRemainingString();
		} catch(ShellIOException e) {
			throw e;
		} catch(Exception e) {
			env.writeln("Wrong path");
			return ShellStatus.CONTINUE;
		}
		
		if(!Files.exists(path) || !Files.isDirectory(path)) {
			env.writeln("There is no such directory");
			return ShellStatus.CONTINUE;
		}
		
		File[] children = path.toFile().listFiles();
		
		if(children == null) {
			return ShellStatus.CONTINUE;
		}
		
		for(var child : children) {
			path = child.toPath();
			BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
			BasicFileAttributes attributes = null;
			try {
				attributes = faView.readAttributes();
			} catch(ShellIOException e) {
				throw e;
			} catch (IOException e) {
				System.out.println("Something went wrong!");
				return ShellStatus.CONTINUE;
			}
			
			System.out.print(Files.isDirectory(path) ? "d" : "-");
			System.out.print(Files.isReadable(path) ? "r" : "-");
			System.out.print(Files.isWritable(path) ? "w" : "-");
			System.out.print(Files.isExecutable(path) ? "x " : "- ");
			
			System.out.print(" ".repeat(10 - ((Long) attributes.size()).toString().length()) + attributes.size() + " ");
			
			FileTime fileTime = attributes.creationTime();
			String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
			System.out.print(formattedDateTime + " ");
			System.out.println(path.getFileName());
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		var list = new ArrayList<String>();
		list.add("Expects two arguments: source file name and destination file name (i.e. paths and names).");
		list.add("If destination file exists, premission to overwrite it is requested. Works only with files (no directories).");
		list.add("If the second argument is directory, the original file is copied into that directory using the original file name.");
		return Collections.unmodifiableList(list);
	}

}
