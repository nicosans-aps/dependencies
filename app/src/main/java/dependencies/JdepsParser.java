package dependencies;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dependencies.model.JavaArchive;

/**
 * Classes responsable de parser le resultat de l'appel à la commande jdeps
 * 
 * @author nicol
 *
 */

public class JdepsParser {

	public static String parse(String result) {
		List<JavaArchive> archives = new ArrayList<>();
		// Filtrage des lignes commençant par des espaces
		List<String> lines = new BufferedReader(new StringReader(result)).lines().filter(x -> !x.startsWith(" "))
				.collect(Collectors.toList());

		// Reste les lignes au format "archive -> archive"
		for (String line : lines) {

		}

		return lines.toString();
	}
}
