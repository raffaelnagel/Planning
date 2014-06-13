package planning.Planning;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Team {
	
	private People people;
	private Project project;
	private String responsability;
	
	public Team(People people, Project project, String responsability){
		this.setPeople(people);
		this.setProject(project);
		this.setResponsability(responsability);
	}	
		
	public String getResponsability() {
		return responsability;
	}
	
	public void setResponsability(String responsability) {
		this.responsability = responsability;
	}

	public People getPeople() {
		return people;
	}

	public void setPeople(People people) {
		this.people = people;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}	
	
	
	static public List<Team> SelectPeopleTeam(DatabaseConnection mConnection, Project mProject){
		
		if(mConnection.isConnected()){
			try{
				Statement mStatement = mConnection.getConnection().createStatement();			
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM Team WHERE idProject = '" + mProject.getId() + "'");
				
				try {					
					List<Team> mListTeam = new ArrayList<Team>();
					while(queryResult.next()){
						
						People mPeople = new People();
						mPeople.setId(queryResult.getString("idPeople"));		
						
						Statement mStatement_People = mConnection.getConnection().createStatement();		
						ResultSet queryResult1 = mStatement_People.executeQuery("SELECT * FROM People WHERE idPeople = '" + mPeople.getId() + "'");
						while(queryResult1.next()){
							mPeople.setName(queryResult1.getString("Name"));						
							mPeople.setCode(queryResult1.getString("Code"));
						}
						
						Team mTeam = new Team(mPeople, mProject, null);
						mTeam.setResponsability(queryResult.getString("Responsability"));
						
						mListTeam.add(mTeam);
					}
					return mListTeam;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			}catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	static public boolean InsertPeopleTeam(DatabaseConnection mConnection, Team mTeam){
		if(mConnection.isConnected()){			
			try {
				
				Project mProject = mTeam.getProject();
				People mPeople = mTeam.getPeople();
				
				ProjectSqlAdapter mProjectSqlAdapter = new ProjectSqlAdapter();
				PeopleSqlAdapter mPeopleSqlAdapter = new PeopleSqlAdapter();
				
				if(mProjectSqlAdapter.Exists(mConnection, mProject) & mPeopleSqlAdapter.Exists(mConnection, mPeople)){
					
					PreparedStatement mPreparedStatement;
					mPreparedStatement = mConnection.getConnection().prepareStatement("INSERT INTO Team(idProject, idPeople, Responsability) VALUES(?,?,?)");
					mPreparedStatement.setString(1, mProject.getId());
					mPreparedStatement.setString(2, mPeople.getId());
					mPreparedStatement.setString(3, mTeam.getResponsability());
					mPreparedStatement.executeUpdate();
					
					return true;
				}else{
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} 								
				
		}
		return false;
	}
	
	static public boolean RemovePeopleTeam(DatabaseConnection mConnection, Team mTeam){
		if(mConnection.isConnected()){			
			try {
				
				Project mProject = mTeam.getProject();
				People mPeople = mTeam.getPeople();
				
				ProjectSqlAdapter mProjectSqlAdapter = new ProjectSqlAdapter();
				PeopleSqlAdapter mPeopleSqlAdapter = new PeopleSqlAdapter();
				
				if(mProjectSqlAdapter.Exists(mConnection, mProject) & mPeopleSqlAdapter.Exists(mConnection, mPeople)){
					
					PreparedStatement mPreparedStatement;
					mPreparedStatement = mConnection.getConnection().prepareStatement("DELETE FROM Team WHERE idProject = ? AND idPeople = ?");
					mPreparedStatement.setString(1, mProject.getId());
					mPreparedStatement.setString(2, mPeople.getId());
					mPreparedStatement.executeUpdate();
					
					return true;
				}else{
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} 								
				
		}
		return false;
	}
}
