package dependencies;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;

/**
 * Test de la classe Dependencies, responsable du parsage des arguments de la
 * ligne de commande et de la configuration de la sortie standard
 * 
 * @author nicol
 *
 */
class DependenciesTest {
	/**
	 * Retourne la chaine de caractère retourné par la ligne de commande en fontion
	 * de la liste d'arguments passés
	 * 
	 * @param options : Une liste de chaines correspondant à des paramètres passés à
	 *                la ligne de commande
	 * @return Le message affiché sur la sorti standard
	 * @throws UnsupportedEncodingException
	 */
	private String getMessageFromArguments(String[] args) {
		ByteArrayOutputStream outSpy = new ByteArrayOutputStream();
		// redéfinition du flux de sortie de l'application
		try {
			Dependencies.setOutputStream(new PrintStream(outSpy, true, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			Dependencies.setOutputStream(new PrintStream(outSpy));
		}
		Dependencies.main(args);
		return outSpy.toString();
	}

	// Arguments manquants

	@Test
	void whenMissingProjectPathThenPrintWarning() {
		String[] args = { "-j", "file.jar" };

		String expectedMessage = "L'argument -p correspondant au chemin du projet à analyser est manquant.";
		String actualMessage = this.getMessageFromArguments(args);

		assertTrue(actualMessage.contains(expectedMessage), "Attendu:" + expectedMessage + "|Obtenu:" + actualMessage);
	}

	@Test
	void whenMissingJarPathThenPrintWarning() {
		String[] args = { "-p", "projectFolder" };

		String expectedMessage = "L'argument -j correspondant au chemin du ou des fichiers .jar racines est manquant.";
		String actualMessage = this.getMessageFromArguments(args);

		assertTrue(actualMessage.contains(expectedMessage), "Attendu:" + expectedMessage + "|Obtenu:" + actualMessage);
	}

	@Test
	void whenEmptyProjectPathThenPrintWarning() {
		String[] args = { "-p", "-j", "file.jar" };

		String expectedMessage = "L'argument de l'option p est manquant.";
		String actualMessage = this.getMessageFromArguments(args);

		assertTrue(actualMessage.contains(expectedMessage), "Attendu:" + expectedMessage + "|Obtenu:" + actualMessage);
	}

	@Test
	void whenEmptyJarPathThenPrintWarning() {
		String[] args = { "-p", "projectFolder", "-j" };

		String expectedMessage = "L'argument de l'option j est manquant.";
		String actualMessage = this.getMessageFromArguments(args);

		assertTrue(actualMessage.contains(expectedMessage), "Attendu:" + expectedMessage + "|Obtenu:" + actualMessage);
	}

	// Affichage de l'aide

	@Test
	void whenNoParametersThenPrintHelp() {
		String[] args = {};

		String expectedMessage = "usage";
		String actualMessage = this.getMessageFromArguments(args);

		assertTrue(actualMessage.contains(expectedMessage), "Attendu:" + expectedMessage + "|Obtenu:" + actualMessage);
	}

	@Test
	void whenHelpArgumentThenPrintHelp() {
		String[] args = { "-h" };

		String expectedMessage = "usage";
		String actualMessage = this.getMessageFromArguments(args);

		assertTrue(actualMessage.contains(expectedMessage), "Attendu:" + expectedMessage + "|Obtenu:" + actualMessage);
	}

}