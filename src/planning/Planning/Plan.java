package planning.Planning;
import java.sql.Timestamp;


public class Plan {
	
	private String id;
	private Project project;
	private String site;
	private String area;
	private String stage;
	private String actionPlan;
	private int demandedQtty;
	private String unit;
	private int cycle;
	private String notes;
	private Timestamp date;
	private int leadTime;
	private PlanStatus status;
	private Timestamp accomplishedDate;
	public static enum PlanStatus {COMPLETE, PENDING, RUNNING, CANCELED, UNKNOWN};
	public static boolean isPlainStatus(String status) {
	    for (PlanStatus ps : PlanStatus.values())
	        if (ps.name().equals(status)) 
	            return true;	        
	    return false;
	}	
	
	public Plan(Project project){		
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

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public int getDemandedQtty() {
		return demandedQtty;
	}

	public void setDemandedQtty(int demandedQtty) {
		this.demandedQtty = demandedQtty;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	public int getLeadTime() {
		return leadTime;
	}

	public void setLeadTime(int leadTime) {
		this.leadTime = leadTime;
	}

	public PlanStatus getStatus() {
		return status;
	}

	public void setStatus(PlanStatus status) {
		this.status = status;
	}

	public Timestamp getAccomplishedDate() {
		return accomplishedDate;
	}

	public void setAccomplishedDate(Timestamp accomplishedDate) {
		this.accomplishedDate = accomplishedDate;
	}

}
