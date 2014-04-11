import java.sql.Timestamp;


public class Plan {
	
	private String id;
	private Project project;
	private Timestamp date;
	private String actionPlan;
	
	public Plan(Project project, Timestamp date, String actionPlan){
		this.setProject(project);
		this.setDate(date);
		this.setActionPlan(actionPlan);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Timestamp getDate() {
		return date;
	}
	
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	public String getActionPlan() {
		return actionPlan;
	}
	
	public void setActionPlan(String actionPlan) {
		this.actionPlan = actionPlan;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	
}
