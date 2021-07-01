package dependencies.model;

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
	// private Collection<JavaClass> classes = new ArrayList<>();

	public JavaArchive(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JavaArchive other = (JavaArchive) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
