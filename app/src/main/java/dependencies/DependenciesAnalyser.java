package dependencies;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Cette classe est chargée de vérifier les paramètres et de gérer l'exécution
 * de l'analyse
 * 
 * @author nicol
 *
 */
public class DependenciesAnalyser {
	private Path projectRootPath;
	private Set<Path> jarFilePaths = new HashSet<>();

	/**
	 * Cette méthode vérifie l'existence de tous les fichiers Jar à analyser. Elle
	 * lève un des fichier jar n'est pas accessible
	 * 
	 * @throws DependenciesAnalyserException
	 */
	private void checkJarFilePaths() throws DependenciesAnalyserException {
		for (Path p : this.jarFilePaths) {
			if (Files.notExists(p)) {
				throw new DependenciesAnalyserException("Le fichier .jar " + p.toString() + " n'existe pas.");
			}
		}
	}

	/**
	 * Cette méthode vérifie l'existence du dossier racine du projet Elle lève une
	 * exception si le dossier n'est pas accessible
	 * 
	 * @throws DependenciesAnalyserException
	 */
	private void checkProjectRootPath() throws DependenciesAnalyserException {
		if (Files.notExists(this.projectRootPath)) {
			throw new DependenciesAnalyserException(
					"Le dossier racine du projet " + this.projectRootPath.toString() + " n'existe pas");
		}
	}

	/**
	 * Retourne un set de Path des fichiers .jar présents dans l'arborescence du
	 * projet (utilisés comme classpath par l'outil jdeps)
	 * 
	 * @return
	 */
	private Set<Path> getClassPathsFromRootPath() {
		Set<Path> classPaths = new HashSet<>();

		try (Stream<Path> pathStream = Files.walk(this.projectRootPath)) {
			classPaths = pathStream.filter(x -> x.endsWith(".jar")).collect(Collectors.toSet());
		} catch (IOException e) {
		}
		return classPaths;
	}

	DependenciesAnalyser(String projectRootPath, String[] jarFilePaths) {
		this.projectRootPath = Paths.get(projectRootPath);
		this.jarFilePaths = Arrays.stream(jarFilePaths).map(x -> Paths.get(x)).collect(Collectors.toSet());
	}

	public void analyse() throws DependenciesAnalyserException {
		this.checkJarFilePaths();

		this.checkProjectRootPath();
		try {

			JdepsWrapper jw = new JdepsWrapper();

			// ajout des fichiers jar détectés dans le dossier projet
			for (Path p : this.getClassPathsFromRootPath()) {
				jw.addClassPath(p);
			}

			// appel de la commande pour chaque jar à analyser
			for (Path p : this.jarFilePaths) {

				System.out.println(jw.analyse(p));
			}
		} catch (JdepsWrapperException e) {
			throw new DependenciesAnalyserException("Erreur lors de l'appel à la commande jdeps : " + e.getMessage());
		}

	}
}
