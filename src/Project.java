import java.sql.Timestamp;


public class Project {

	private String id;
	private String projectCode;
	private String Name;
	private String Category;
	private String Brand;
	private String opco;
	private String endMarket;
	private String complexity;
	private boolean approval;
	private Timestamp start;
	private Timestamp finish;
	private Timestamp Date;
	
	
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

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
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

	public String getComplexity() {
		return complexity;
	}

	public void setComplexity(String complexity) {
		this.complexity = complexity;
	}

	public boolean getApproval() {
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
