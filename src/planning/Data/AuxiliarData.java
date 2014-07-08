package planning.Data;

//For Data Tables: ProjectCategory, PitCode, MainProject, OpCo, EndMarket
public class AuxiliarData {
	private String id;
	private String name;
	private AuxiliarDataTypes type;
	public static enum AuxiliarDataTypes{ProjectCategory, PitCode, MainProject, OpCo, EndMarket};
	
	public String getId(){
		return id;		
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getName(){
		return name;		
	}
	
	public void setName(String name){
		this.name = name;
	}

	public AuxiliarDataTypes getType() {
		return type;
	}

	public void setType(AuxiliarDataTypes type) {
		this.type = type;
	}
}
