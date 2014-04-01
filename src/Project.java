
public class Project {

	private String Name;
	private String Category;
	private String Brand;
	private String Leader;
	private String id;
	private String Pid;
	private String Date;
	
		
	public Project(String Name, String Category, String Brand, String Leader){
		this.setName(Name);
		this.setCategory(Category);
		this.setBrand(Brand);
		this.setLeader(Leader);
	}
	
	public Project(){
		new Project(null,null,null,null);
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
	public String getLeader() {
		return Leader;
	}
	public void setLeader(String leader) {
		Leader = leader;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return Pid;
	}
	public void setPid(String pid) {
		Pid = pid;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	
	
}
