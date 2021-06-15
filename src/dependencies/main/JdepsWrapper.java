package dependencies.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;

public class JdepsWrapper {

	private File jdepsExecutable;
	private Set<File> classPaths;

	public JdepsWrapper(File jdepsExecutable) throws JdepsWrapperException {
		if (!jdepsExecutable.isFile()) {
			throw new JdepsWrapperException(jdepsExecutable.getName() + " is missing");
		}
	}

	public void analyse(File jarFile) throws JdepsWrapperException {
		if (!jarFile.isFile()) {
			throw new JdepsWrapperException(jarFile.getName() + " is missing");
		}
		runJdeps(jarFile);

	}

	public void addClassPath(File classPath) {
		this.classPaths.add(classPath);
	}

	private void runJdeps(File jarFile) {
		Runtime rt = Runtime.getRuntime();
		String classPathString = this.classPaths.stream().map(File::getAbsolutePath).collect(Collectors.joining(";"));

		String[] commandAndArguments = { this.jdepsExecutable.getAbsolutePath(), "--classpath", classPathString,
				jarFile.getAbsolutePath() };
		try {
			Process p = rt.exec(commandAndArguments);
			String response = readProcessOutput(p);
			System.out.println(response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Reads the response from the command. Please note that this works only if the
	 * process returns immediately.
	 * 
	 * @param p
	 * @return
	 * @throws Exception
	 */
	private String readProcessOutput(Process p) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String response = "";
		String line;
		while ((line = reader.readLine()) != null) {
			response += line + "\r\n";
		}
		reader.close();
		return response;
	}
}
