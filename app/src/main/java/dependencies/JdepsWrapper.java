package dependencies;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class JdepsWrapper {

	private Path jdepsExecutable;
	private Set<Path> classPaths = new HashSet<>();

	private void throwExceptionIfPathMissing(Path path) throws JdepsWrapperException {
		if (!Files.exists(path)) {
			throw new JdepsWrapperException(path.getFileName() + " is missing");
		}
	}

	public JdepsWrapper(Path jdepsExecutable) throws JdepsWrapperException {
		throwExceptionIfPathMissing(jdepsExecutable);
		this.jdepsExecutable = jdepsExecutable;
	}

	/**
	 * Analyse le
	 * 
	 * @param jarPath
	 * @throws JdepsWrapperException
	 */
	public void analyse(Path jarPath) throws JdepsWrapperException {
		throwExceptionIfPathMissing(jdepsExecutable);
		run(jarPath);
	}

	public void addClassPath(Path classPath) throws JdepsWrapperException {
		throwExceptionIfPathMissing(classPath);

		if (!classPath.endsWith(".jar")) {
			throw new JdepsWrapperException(classPath.getFileName() + " doesn't end with .jar");
		}
		this.classPaths.add(classPath);
	}

	private void run(Path jarPath) {
		Runtime rt = Runtime.getRuntime();
		String classPathString = this.classPaths.stream().map(Path::toString).collect(Collectors.joining(";"));

		String[] commandAndArguments = { this.jdepsExecutable.toString(), "--classpath", classPathString,
				jarPath.toString() };
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
		StringBuilder responseBuilder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			responseBuilder.append(line);
			responseBuilder.append("\r\n");

		}
		reader.close();
		return responseBuilder.toString();
	}
}
