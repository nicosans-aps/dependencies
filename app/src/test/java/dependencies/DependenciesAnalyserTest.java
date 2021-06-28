package dependencies;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test de la classe DependenciesAnalyser, responsable de la vérification des
 * paramètres d'analyse et du séquencement de son éxécution
 * 
 * @author nicol
 *
 */
class DependenciesAnalyserTest {

	static final String NON_EXIST_PATH = "nonexist";
	static String EXIST_PATH = null;
	static String NON_EXIST_JAR_PATH = "nonexist.jar";
	static String EXIST_JAR_PATH = null;

	@BeforeAll
	static void createTestFiles() throws IOException, URISyntaxException {
		EXIST_PATH = Paths.get(DependenciesAnalyserTest.class.getResource("/test").toURI()).toString();
		EXIST_JAR_PATH = Paths.get(DependenciesAnalyserTest.class.getResource("/test/test.jar").toURI()).toString();
	}
	// Vérification des paramètres d'analyse

	@Test
	void whenWrongProjectPathThenThrowException() {
		String[] jars = { EXIST_JAR_PATH };

		Exception exception = assertThrows(DependenciesAnalyserException.class, () -> {
			DependenciesAnalyser da = new DependenciesAnalyser(NON_EXIST_PATH, jars);
			da.analyse();
		});
		String expectedMessage = "Le dossier racine du projet " + NON_EXIST_PATH + " n'existe pas";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage), "Attendu:" + expectedMessage + "|Obtenu:" + actualMessage);
	}

	@Test
	void whenWrongJarPathThenThrowException() {
		String[] jars = { NON_EXIST_JAR_PATH };

		Exception exception = assertThrows(DependenciesAnalyserException.class, () -> {
			DependenciesAnalyser da = new DependenciesAnalyser(EXIST_PATH, jars);
			da.analyse();
		});
		String expectedMessage = "Le fichier .jar " + NON_EXIST_JAR_PATH + " n'existe pas.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage), "Attendu:" + expectedMessage + "|Obtenu:" + actualMessage);
	}

}
