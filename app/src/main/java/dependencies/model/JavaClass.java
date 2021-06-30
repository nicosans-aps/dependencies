package dependencies.model;

/**
 * Classe représentant une classe Java
 * 
 * @author nicol
 *
 */
public class JavaClass implements Comparable<JavaClass> {
	private String name;

	public JavaClass(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(JavaClass o) {
		return this.name.compareTo(o.name);

	}

}
