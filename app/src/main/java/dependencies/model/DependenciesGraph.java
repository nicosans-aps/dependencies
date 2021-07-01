package dependencies.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cette classe représente un graph de dépendance entre plusieurs JavaArchive
 * 
 * @author nicol
 *
 */

public class DependenciesGraph {
	private Map<JavaArchive, List<JavaArchive>> adjArchives = new HashMap<>();

	public void addArchive(String name) {
		adjArchives.putIfAbsent(new JavaArchive(name), new ArrayList<>());
	}

	public void addArchive(JavaArchive archive) {
		adjArchives.putIfAbsent(archive, new ArrayList<>());
	}

	public void removeArchive(String name) {
		JavaArchive v = new JavaArchive(name);
		adjArchives.values().stream().forEach(e -> e.remove(v));
		adjArchives.remove(new JavaArchive(name));
	}

	public void addDependency(String dependent, String needed) {
		JavaArchive v1 = new JavaArchive(dependent);
		JavaArchive v2 = new JavaArchive(needed);

		adjArchives.get(v1).add(v2);
		adjArchives.get(v2).add(v1);
	}

	public void removeDependency(String label1, String label2) {
		JavaArchive v1 = new JavaArchive(label1);
		JavaArchive v2 = new JavaArchive(label2);
		List<JavaArchive> eV1 = adjArchives.get(v1);
		List<JavaArchive> eV2 = adjArchives.get(v2);
		if (eV1 != null)
			eV1.remove(v2);
		if (eV2 != null)
			eV2.remove(v1);
	}

	public Collection<JavaArchive> getArchives() {
		return adjArchives.keySet();

	}
}
