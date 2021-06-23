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
			throw new JdepsWrapperException(path.getFileName() + " est manquant");
		}
	}

	public JdepsWrapper(Path jdepsExecutable) throws JdepsWrapperException {
		throwExceptionIfPathMissing(jdepsExecutable);
		this.jdepsExecutable = jdepsExecutable;
	}

	/**
	 * Appel la commande jdeps du jdk pour obtenir les dépendances des classes
	 * contenues dans une archive jar. L'existence de l'archive rar est vérifiée
	 * avant l'appel de jdeps
	 * 
	 * @param jarPath
	 * @throws JdepsWrapperException
	 */
	public void analize(Path jarPath) throws JdepsWrapperException {
		throwExceptionIfPathMissing(jdepsExecutable);
		run(jarPath);
	}

	public void addClassPath(Path classPath) throws JdepsWrapperException {
		throwExceptionIfPathMissing(classPath);

		if (!classPath.endsWith(".jar")) {
			throw new JdepsWrapperException(classPath.getFileName() + "  n'est pas un fichier .jar");
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
