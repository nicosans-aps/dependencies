package dependencies.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

class JavaArchiveTest {

	@Test
	void whenSameNameThenEqualIsTrue() {
		final JavaArchive ja1 = new JavaArchive("ja");
		final JavaArchive ja2 = new JavaArchive("ja");
		final JavaArchive ja3 = new JavaArchive("ja");

		// reflexive
		assertTrue(ja1.equals(ja1), "Equals is true when same object (reflexive).");
		assertTrue(ja2.equals(ja2), "Equals is true when same object (reflexive).");
		assertTrue(ja3.equals(ja3), "Equals is true when same object (reflexive).");

		// symetric
		assertTrue(ja2.equals(ja1), "Equals is true when same name (symetric).");
		assertTrue(ja1.equals(ja2), "Equals is true when same name (symetric).");

		// transitive
		assertTrue(ja1.equals(ja2), "Equals is true when same name (transitive)."); // rappel
		assertTrue(ja2.equals(ja3), "Equals is true when same name (transitive).");
		assertTrue(ja1.equals(ja3), "Equals is true when same name (transitive).");

	}

	@Test
	void whenSameNameThenEqualIsFalse() {
		final JavaArchive ja = new JavaArchive("ja");
		assertFalse(ja.equals(null), "Equals is false when null.");

	}

	@Test
	public void testHashSet() throws Exception {
		final JavaArchive ja1 = new JavaArchive("ja");
		final JavaArchive ja2 = new JavaArchive("ja");

		final Set<JavaArchive> jas = new HashSet<>();
		jas.add(ja1);

		assertEquals(ja2, jas.iterator().next());
		assertTrue(jas.contains(ja2));
	}

	@Test
	public void testHashMap() throws Exception {
		final JavaArchive ja1 = new JavaArchive("ja");
		final JavaArchive ja2 = new JavaArchive("ja");

		final Map<JavaArchive, Integer> map = new HashMap<>();
		map.put(ja1, 2);

		assertEquals(1, map.size());
		assertEquals(ja2, map.keySet().iterator().next());
		assertEquals(2, map.values().iterator().next());
		assertTrue(map.containsKey(ja2));
	}

	@Test
	public void should_not_contain_doubles() throws Exception {
		final JavaArchive ja1 = new JavaArchive("ja");
		final JavaArchive ja2 = new JavaArchive("ja");

		final Set<JavaArchive> jas = new HashSet<>();
		jas.add(ja1);
		jas.add(ja2);

		assertTrue(ja1.equals(ja2));
		assertEquals(1, jas.size());
	}

}
