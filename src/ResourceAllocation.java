
public class ResourceAllocation {
	
	private Resource resource;
	private Project project;
	private float quantity;
	
	public ResourceAllocation(Resource resource, Project project, float quantity){
		this.setResource(resource);
		this.setProject(project);
		this.setQuantity(quantity);
	}
	
	public Resource getResource() {
		return resource;
	}
	
	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	
}
