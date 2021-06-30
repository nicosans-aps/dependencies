package dependencies;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import dependencies.model.DependenciesGraph;

/**
 * Classes responsable de parser le resultat de l'appel à la commande jdeps
 * 
 * @author nicol
 *
 */

public class JdepsParser {
	// Exprssion regulière pour recherche au format "test.jar -> java.base"
	private static final Pattern PATTERN = Pattern.compile("^(\\S+) -> (\\S+)$");

	public static DependenciesGraph read(String result) {
		DependenciesGraph dg = new DependenciesGraph();

		// Filtrage des lignes commençant par des espaces
		List<String> lines = new BufferedReader(new StringReader(result)).lines().filter(x -> !x.startsWith(" "))
				.collect(Collectors.toList());

		// Reste les lignes au format "archive -> archive"
		for (String line : lines) {
			Matcher m = PATTERN.matcher(line);
			if (m.find()) {
				dg.addArchive(m.group(0));
				dg.addArchive(m.group(1));
				dg.addDependency(m.group(0), m.group(1));
			}
		}

		return dg;
	}
}
