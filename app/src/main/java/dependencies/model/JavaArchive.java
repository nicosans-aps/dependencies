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
	public String getName() {
		return name;
	}

	private String name;
	private Collection<JavaClass> classes = new ArrayList<>();

	public JavaArchive(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

}
