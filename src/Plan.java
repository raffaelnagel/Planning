
public class Plan {
	
	private int idPlan;
	private Project project;
	private String date;
	private String actionPlan;
	
	public Plan(Project project, String date, String actionPlan){
		this.setProject(project);
		this.setDate(date);
		this.setActionPlan(actionPlan);
	}
	
	public int getIdPlan() {
		return idPlan;
	}
	
	public void setIdPlan(int idPlan) {
		this.idPlan = idPlan;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
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
