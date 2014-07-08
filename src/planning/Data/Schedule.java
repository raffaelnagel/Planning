package planning.Data;
import java.sql.Timestamp;


public class Schedule {
	
	private String id;
	private Project project;	
	private Activity activity;
	private int demandedQtty;
	private int cycle;
	private String notes;
	private Timestamp date;
	private ScheduleStatus status;
	private Timestamp accomplishedDate;
	
	public static enum ScheduleStatus {COMPLETE, PENDING, RUNNING, CANCELED, UNKNOWN};
	public static boolean isScheduleStatus(String status) {
	    for (ScheduleStatus ps : ScheduleStatus.values())
	        if (ps.name().equals(status)) 
	            return true;	        
	    return false;
	}	
	
	public Schedule(Project project){		
		this.setProject(project);		
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public int getDemandedQtty() {
		return demandedQtty;
	}

	public void setDemandedQtty(int demandedQtty) {
		this.demandedQtty = demandedQtty;
	}

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cicle) {
		this.cycle = cicle;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public ScheduleStatus getStatus() {
		return status;
	}

	public void setStatus(ScheduleStatus status) {
		this.status = status;
	}

	public Timestamp getAccomplishedDate() {
		return accomplishedDate;
	}

	public void setAccomplishedDate(Timestamp accomplishedDate) {
		this.accomplishedDate = accomplishedDate;
	}

}
