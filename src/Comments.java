
public class Comments {
	
	private int idComments;
	private Project project;
	private String date;
	private String comment;
	
	public Comments(Project project, String date, String comment){
		this.setProject(project);
		this.setDate(date);
		this.setComment(comment);
	}
	
	public int getIdComments() {
		return idComments;
	}
	
	public void setIdComments(int idComments) {
		this.idComments = idComments;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
