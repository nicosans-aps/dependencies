package dependencies.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Représente une archive Java (JAR)
 * 
 * @author nicol
 *
 */
public class JavaArchive {
	private String Name;
	private Collection<JavaClass> classes = new ArrayList<>();

	public JavaArchive(String name) {
		this.Name = name;
	}

}
