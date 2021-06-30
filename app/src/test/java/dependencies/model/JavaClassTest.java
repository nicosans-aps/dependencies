package dependencies.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class JavaClassTest {

	@Test
	void whenNewJavaClassThenNotNull() {
		JavaClass jc = new JavaClass("NewJavaClass");

		assertNotNull(jc, "L'object JavaClass ne doit pas être null.");
		assertEquals("NewJavaClass", jc.getName());
		assertEquals("NewJavaClass", jc.toString());
	}

	void whenCompareToJavaClassThenComparaisonValid() {
		JavaClass a1 = new JavaClass("a");
		JavaClass a2 = new JavaClass("a");
		JavaClass b = new JavaClass("b");

		assertEquals(0, a1.compareTo(a2));
		assertEquals(-1, a1.compareTo(b));
	}

}
