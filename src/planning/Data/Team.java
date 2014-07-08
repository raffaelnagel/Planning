package planning.Data;

public class Team {
	
	private People people;
	private Project project;
	private String responsability;
	
	public Team(People people, Project project, String responsability){
		this.setPeople(people);
		this.setProject(project);
		this.setResponsability(responsability);
	}	
		
	public String getResponsability() {
		return responsability;
	}
	
	public void setResponsability(String responsability) {
		this.responsability = responsability;
	}

	public People getPeople() {
		return people;
	}

	public void setPeople(People people) {
		this.people = people;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}	
		
}
