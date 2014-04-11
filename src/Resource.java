
public class Resource {
	
	private String id;
	private String name;
	private String type;
	
	public Resource(String name, String type){
		this.setName(name);
		this.setType(type);
	}
	
	public Resource(){
		new Resource(null, null);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
