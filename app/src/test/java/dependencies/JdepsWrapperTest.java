package dependencies;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JdepsWrapperTest {

	static final Path NON_EXIST_PATH = Paths.get("nonexist");
	static final Path EXIST_PATH = Paths.get("exist");
	static final Path EXIST_JAR_PATH = Paths.get("test.jar");

	@BeforeAll
	static void createTestFiles() throws IOException {
		Files.createFile(EXIST_PATH);
		Files.createFile(EXIST_JAR_PATH);
	}

	@Test
	void whenNonExistJdepExecutableThenThrowsJdepsWrapperException() {

		Exception exception = assertThrows(JdepsWrapperException.class, () -> {
			JdepsWrapper jw = new JdepsWrapper(NON_EXIST_PATH);
		});
		String expectedMessage = " is missing";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void whenNonExistClassPathThenThrowsJdepsWrapperException() throws JdepsWrapperException {
		JdepsWrapper jw = new JdepsWrapper(EXIST_PATH);
		Exception exception = assertThrows(JdepsWrapperException.class, () -> {
			jw.addClassPath(NON_EXIST_PATH);
		});

		String expectedMessage = " is missing";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void whenNotEndingByJarClassPathThenThrowsJdepsWrapperException() throws JdepsWrapperException {
		JdepsWrapper jw = new JdepsWrapper(EXIST_PATH);
		Exception exception = assertThrows(JdepsWrapperException.class, () -> {

			jw.addClassPath(EXIST_PATH);
		});
		String expectedMessage = " doesn't end with .jar";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@AfterAll
	static void deleteTestFiles() {
		try {
			Files.deleteIfExists(EXIST_PATH);
			Files.deleteIfExists(EXIST_JAR_PATH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
