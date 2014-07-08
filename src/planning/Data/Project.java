package planning.Data;
import java.sql.Timestamp;


public class Project {

	private String id;
	private String projectCode;
	private String name;
	private AuxiliarData pitCode;
	private AuxiliarData mainProject;
	private AuxiliarData category;
	private Brand brand;
	private AuxiliarData opco;
	private AuxiliarData endMarket;
	private ProjectComplexity complexity;
	private boolean approval;
	private Timestamp start;
	private Timestamp finish;
	private Timestamp Date;
	public static enum ProjectComplexity{CAP1, CAP2, CAP3};
		
	public static boolean isProjectComplexity(String complexity) {
	    for (ProjectComplexity pc : ProjectComplexity.values())
	        if (pc.name().equals(complexity)) 
	            return true;	        
	    return false;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AuxiliarData getPitCode() {
		return pitCode;
	}

	public void setPitCode(AuxiliarData pitCode) {
		this.pitCode = pitCode;
	}

	public AuxiliarData getMainProject() {
		return mainProject;
	}

	public void setMainProject(AuxiliarData mainProject) {
		this.mainProject = mainProject;
	}

	public AuxiliarData getCategory() {
		return category;
	}

	public void setCategory(AuxiliarData category) {
		this.category = category;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public AuxiliarData getOpco() {
		return opco;
	}

	public void setOpco(AuxiliarData opco) {
		this.opco = opco;
	}

	public AuxiliarData getEndMarket() {
		return endMarket;
	}

	public void setEndMarket(AuxiliarData endMarket) {
		this.endMarket = endMarket;
	}

	public ProjectComplexity getComplexity() {
		return complexity;
	}

	public void setComplexity(ProjectComplexity complexity) {
		this.complexity = complexity;
	}

	public boolean isApproval() {
		return approval;
	}

	public void setApproval(boolean approval) {
		this.approval = approval;
	}

	public Timestamp getStart() {
		return start;
	}

	public void setStart(Timestamp start) {
		this.start = start;
	}

	public Timestamp getFinish() {
		return finish;
	}

	public void setFinish(Timestamp finish) {
		this.finish = finish;
	}

	public Timestamp getDate() {
		return Date;
	}

	public void setDate(Timestamp date) {
		Date = date;
	}	
}
