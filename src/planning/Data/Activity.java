package planning.Data;

public class Activity {
	
	private String id;
	private String site;
	private String area;
	private String stage;
	private String action;
	private String unit;
	private int leadtime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getLeadtime() {
		return leadtime;
	}
	public void setLeadtime(int leadtime) {
		this.leadtime = leadtime;
	}
	
}
