package test.java.dependencies;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.java.dependencies.JdepsWrapper;
import main.java.dependencies.JdepsWrapperException;

class JdepsWrapperTest {

	static final File NON_EXIST_FILE = new File("nonexist");
	static final File EXIST_FILE = new File("exist");
	static final File EXIST_JAR = new File("test.jar");

	@BeforeAll
	static void createTestFiles() throws IOException {
		EXIST_FILE.createNewFile();
		EXIST_FILE.deleteOnExit();

		EXIST_JAR.createNewFile();
		EXIST_JAR.deleteOnExit();
	}

	@Test
	void whenNonExistJdepExecutableThenThrowsIOException() {

		Assertions.assertThrows(IOException.class, () -> {
			new JdepsWrapper(NON_EXIST_FILE);
		});
	}

	@Test
	void whenNonExistClassPathThenThrowsIOException() {

		Exception exception = Assertions.assertThrows(JdepsWrapperException.class, () -> {
			JdepsWrapper jw = new JdepsWrapper(EXIST_FILE);
			jw.addClassPath(NON_EXIST_FILE);
		});

		String expectedMessage = "For input string";
		String actualMessage = exception.getMessage();

		Assertions.assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void whenNotEndingByJarClassPathThenThrowsIOException() {

		Assertions.assertThrows(IOException.class, () -> {
			JdepsWrapper jw = new JdepsWrapper(EXIST_FILE);
			jw.addClassPath(EXIST_FILE);
		});
	}

	@Test
	void whenEndingByJarClassPathThen() {

		Assertions.assertThrows(IOException.class, () -> {
			JdepsWrapper jw = new JdepsWrapper(EXIST_FILE);
			jw.addClassPath(EXIST_FILE);
		});
	}
}
