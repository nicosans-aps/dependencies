package dependencies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

class DependenciesTest {

	@Test
	void whenNoParametersThenPrintMessage() throws InterruptedException {

		ByteArrayOutputStream outSpy = new ByteArrayOutputStream();

		Dependencies.setOutputStream(new PrintStream(outSpy));

		String[] args = {};

		Dependencies.main(args);

		outSpy.wait();
		assertEquals(outSpy.toString(), "Erreur lors de la lecture des arguments de la ligne de commande.");

	}

}