package dependencies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dependencies.model.DependenciesGraph;

class JdepsParserTest {

	@Test
	void whenNoStringThenReturnEmptyGraph() {
		DependenciesGraph graph = JdepsParser.read("");
		assertTrue(graph instanceof DependenciesGraph, "L'objet graph doit être du type DependenciesGraph");
		assertEquals(0, graph.getArchives().size(), "La liste des archives du graph doit être vide");
	}

}
