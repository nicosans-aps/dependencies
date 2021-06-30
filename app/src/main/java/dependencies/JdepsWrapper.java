package dependencies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class JdepsWrapper {

	private static final String JDEPS_EXEC = "jdeps";
	private Set<Path> classPaths = new HashSet<>();

	private void throwExceptionIfPathMissing(Path path) throws JdepsWrapperException {
		if (!Files.exists(path)) {
			throw new JdepsWrapperException(path.getFileName() + " est manquant");
		}
	}

	private void throwExceptionIfJdepsMissing() throws JdepsWrapperException {
		Runtime rt = Runtime.getRuntime();
		int exitValue = 0;

		String[] commandAndArguments = { "cmd", "/C", "jdeps --version" };

		try {
			Process p = rt.exec(commandAndArguments);
			p.waitFor();
			exitValue = p.exitValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (exitValue != 0) {
			throw new JdepsWrapperException("La commande jdeps est inconnue.");
		}
	}

	public JdepsWrapper() throws JdepsWrapperException {
		throwExceptionIfJdepsMissing();
	}

	/**
	 * Appel la commande jdeps du jdk pour obtenir les dépendances des classes
	 * contenues dans une archive jar. L'existence de l'archive rar est vérifiée
	 * avant l'appel de jdeps
	 * 
	 * @param jarPath
	 * @throws JdepsWrapperException
	 */
	public String analyse(Path jarPath) {
		return execute(jarPath);
	}

	public void addClassPath(Path classPath) throws JdepsWrapperException {
		throwExceptionIfPathMissing(classPath);

		if (!classPath.toString().endsWith(".jar")) {
			throw new JdepsWrapperException(classPath.getFileName() + " n'est pas un fichier .jar");
		}
		this.classPaths.add(classPath);
	}

	protected Set<Path> getClassPaths() {
		return this.classPaths;
	}

	private String execute(Path jarPath) {
		Runtime rt = Runtime.getRuntime();
		String response = "";
		String classPathString = this.classPaths.stream().map(Path::toString).collect(Collectors.joining(";"));

		String[] commandAndArguments = { "cmd", "/C", JDEPS_EXEC, "--recursive", "--multi-release", "base",
				"--class-path", classPathString, jarPath.toString() };
		try {
			Process p = rt.exec(commandAndArguments);
			response = readProcessOutput(p);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	/**
	 * Reads the response from the command. Please note that this works only if the
	 * process returns immediately.
	 * 
	 * @param p
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	private String readProcessOutput(Process p) throws IOException {
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
