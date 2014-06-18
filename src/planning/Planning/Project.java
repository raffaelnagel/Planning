package planning.Planning;
import java.sql.Timestamp;


public class Project {

	private String id;
	private String projectCode;
	private String Name;
	private ProjectCategory Category;
	private String Brand;
	private String opco;
	private String endMarket;
	private ProjectComplexity complexity;
	private boolean approval;
	private Timestamp start;
	private Timestamp finish;
	private Timestamp Date;
	public static enum ProjectComplexity{CAP1, CAP2, CAP3};
	public static enum ProjectCategory{BAU, BUSINESS, CONSUMER, KNOWLEDGE, PEOPLE, REGULATION, UNKNOWN};
	
	public static boolean isProjectCategory(String category) {
	    for (ProjectCategory pc : ProjectCategory.values())
	        if (pc.name().equals(category)) 
	            return true;	        
	    return false;
	}
	
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
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public ProjectCategory getCategory() {
		return Category;
	}

	public void setCategory(ProjectCategory category) {
		Category = category;
	}

	public String getBrand() {
		return Brand;
	}

	public void setBrand(String brand) {
		Brand = brand;
	}

	public String getOpco() {
		return opco;
	}

	public void setOpco(String opco) {
		this.opco = opco;
	}

	public String getEndMarket() {
		return endMarket;
	}

	public void setEndMarket(String endMarket) {
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
