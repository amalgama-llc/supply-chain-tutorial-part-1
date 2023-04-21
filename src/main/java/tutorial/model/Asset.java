package tutorial.model;

public abstract class Asset {
	
	private final String id;
	private final String name;

	protected Asset(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}