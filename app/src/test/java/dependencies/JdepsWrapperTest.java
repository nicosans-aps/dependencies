package dependencies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JdepsWrapperTest {

	static final Path NON_EXIST_PATH = Paths.get("nonexist");
	static Path EXIST_PATH = null;
	static Path EXIST_JAR_PATH = null;

	@BeforeAll
	static void createTestFiles() throws IOException, URISyntaxException {
		EXIST_PATH = Paths.get(JdepsWrapperTest.class.getResource("/test").toURI());
		EXIST_JAR_PATH = Paths.get(JdepsWrapperTest.class.getResource("/test/test.jar").toURI());
	}

	@Test
	void whenNonExistClassPathThenThrowsJdepsWrapperException() throws JdepsWrapperException {
		JdepsWrapper jw = new JdepsWrapper();
		Exception exception = assertThrows(JdepsWrapperException.class, () -> {
			jw.addClassPath(NON_EXIST_PATH);
		});

		String expectedMessage = " est manquant";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage), "Attendu:" + expectedMessage + "|Obtenu:" + actualMessage);
	}

	@Test
	void whenNotEndingByJarClassPathThenThrowsJdepsWrapperException() throws JdepsWrapperException {
		JdepsWrapper jw = new JdepsWrapper();
		Exception exception = assertThrows(JdepsWrapperException.class, () -> {

			jw.addClassPath(EXIST_PATH);
		});
		String expectedMessage = " n'est pas un fichier .jar";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage), "Attendu:" + expectedMessage + "|Obtenu:" + actualMessage);
	}

	@Test
	void whenEndingByJarClassPathThenAddClassPath() throws JdepsWrapperException {
		JdepsWrapper jw = new JdepsWrapper();

		jw.addClassPath(EXIST_JAR_PATH);

		assertEquals(1, jw.getClassPaths().size(), "Le nombre d'occurence de classpath doit être égale à 1");
	}

}
