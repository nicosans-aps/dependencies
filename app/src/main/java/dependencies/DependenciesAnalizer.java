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
 * Cette classe est chargée de vérifier les paramètres
 * 
 * @author nicol
 *
 */
public class DependenciesAnalizer {
	private Path projectRootPath;
	private Set<Path> jarFilePaths = new HashSet<>();

	private void checkJarFilePaths() throws DependenciesAnalizerException {
		for (Path p : this.jarFilePaths) {
			if (Files.notExists(p)) {
				throw new DependenciesAnalizerException("Le fichier .jar");
			}
		}
	}

	private void checkProjectRootPath() throws DependenciesAnalizerException {
		if (Files.notExists(this.projectRootPath)) {
			throw new DependenciesAnalizerException(
					"Le dossier racine du projet " + this.projectRootPath.toString() + " n'existe pas");
		}
	}

	/**
	 * Retourne un set de Path des fichiers .jar présents dans l'arborescence du
	 * projet
	 * 
	 * @return
	 */
	private Set<Path> getClassPathsFromRootPath() {
		Set<Path> classPaths = new HashSet<>();
		try (Stream<Path> pathStream = Files.walk(this.projectRootPath)) {
			classPaths = pathStream.filter(x -> x.getFileName().endsWith(".jar")).collect(Collectors.toSet());
		} catch (IOException e) {
		}
		return classPaths;
	}

	DependenciesAnalizer(String projectRootPath, String[] jarFilePaths) {
		this.projectRootPath = Paths.get(projectRootPath);
		this.jarFilePaths = Arrays.stream(jarFilePaths).map(x -> Paths.get(x)).collect(Collectors.toSet());
	}

	public void analize() throws DependenciesAnalizerException {
		this.checkJarFilePaths();

		this.checkProjectRootPath();
		try {
			// defini le chemin de la commande jdeps dans le constructeur
			JdepsWrapper jw = new JdepsWrapper(Paths.get("jdeps"));

			// ajout des fichiers jar détectés dans le dossier projet
			for (Path p : this.getClassPathsFromRootPath()) {
				jw.addClassPath(p);
			}

			// appel de la commande pour chaque jar
			for (Path p : this.jarFilePaths) {

				jw.analize(p);
			}
		} catch (JdepsWrapperException e) {
			throw new DependenciesAnalizerException("Erreur lors de l'appel à la commande jdeps");
		}

	}
}
