package Planning;
import java.sql.Timestamp;


public class Comments {
	
	private String id;
	private Project project;
	private Timestamp date;
	private String comment;
	
	public Comments(Project project, Timestamp date, String comment){
		this.setProject(project);
		this.setDate(date);
		this.setComment(comment);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public Timestamp getDate() {
		return date;
	}
	
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
