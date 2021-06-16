package main.java.dependencies;

import java.io.File;
import java.util.Set;

import sun.tools.jar.CommandLine;

public class Dependencies {

	public static void main(String[] args) {

		CommandLine line = parseArguments(args);
		File projectRootFolder = new File();
		Set<File> projectJars = new Set();
		DependenciesAnalizer da = new DependenciesAnalizer(projectRootFolder, projectJars);

	}

}
