
public class Resource {
	
	private int idResource;
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

	public int getIdResource() {
		return idResource;
	}

	public void setIdResource(int idResource) {
		this.idResource = idResource;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
