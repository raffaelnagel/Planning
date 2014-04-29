package planning.Planning;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class ProjectSqlAdapter {

	public List<Project> SelectProject(DatabaseConnection mConnection){
		
		if(mConnection.isConnected()){						
			try {
				Statement mStatement = mConnection.getConnection().createStatement();			
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM Project");
				
				try {					
					List<Project> mListProject = new ArrayList<Project>();
					while(queryResult.next()){
						
						Project mProject = new Project();
						mProject.setId(queryResult.getString("idProject"));
						mProject.setProjectCode(queryResult.getString("ProjectCode"));
						mProject.setName(queryResult.getString("Name"));
						mProject.setCategory(queryResult.getString("Category"));
						mProject.setBrand(queryResult.getString("Brand"));
						mProject.setOpco(queryResult.getString("OpCo"));
						mProject.setEndMarket(queryResult.getString("EndMarket"));
						mProject.setComplexity(queryResult.getString("Complexity"));
						mProject.setApproval(queryResult.getBoolean("Approval"));
						mProject.setStart(queryResult.getTimestamp("Start"));
						mProject.setFinish(queryResult.getTimestamp("Finish"));
						mProject.setDate(queryResult.getTimestamp("Date"));

						mListProject.add(mProject);
					}
					return mListProject;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}		
		return null;
	}
	
	public List<Project> SelectProject(DatabaseConnection mConnection, String column, String value){
		
		if(mConnection.isConnected()){						
			try {
				Statement mStatement = mConnection.getConnection().createStatement();			
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM Project WHERE " + column + " = '" + value + "'");
				
				try {					
					List<Project> mListProject = new ArrayList<Project>();
					while(queryResult.next()){
						
						Project mProject = new Project();
						mProject.setId(queryResult.getString("idProject"));
						mProject.setProjectCode(queryResult.getString("ProjectCode"));
						mProject.setName(queryResult.getString("Name"));
						mProject.setCategory(queryResult.getString("Category"));
						mProject.setBrand(queryResult.getString("Brand"));
						mProject.setOpco(queryResult.getString("OpCo"));
						mProject.setEndMarket(queryResult.getString("EndMarket"));
						mProject.setComplexity(queryResult.getString("Complexity"));
						mProject.setApproval(queryResult.getBoolean("Approval"));
						mProject.setStart(queryResult.getTimestamp("Start"));
						mProject.setFinish(queryResult.getTimestamp("Finish"));
						mProject.setDate(queryResult.getTimestamp("Date"));

						mListProject.add(mProject);
					}
					return mListProject;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}		
		return null;
	}
	
	public boolean InsertProject(DatabaseConnection mConnection, Project mProject){
		if(mConnection.isConnected()){			
			try {
				
				PreparedStatement mPreparedStatement;
				mPreparedStatement = mConnection.getConnection().prepareStatement("INSERT INTO Project(Name, Category, Brand, OpCo, EndMarket, Complexity, Approval, Start, Finish, Date) VALUES(?,?,?,?,?,?,?,?,?,?)");
				mPreparedStatement.setString(1, mProject.getName());
				mPreparedStatement.setString(2, mProject.getCategory());
				mPreparedStatement.setString(3, mProject.getBrand());
				mPreparedStatement.setString(4, mProject.getOpco());
				mPreparedStatement.setString(5, mProject.getEndMarket());
				mPreparedStatement.setString(6, mProject.getComplexity());
				mPreparedStatement.setBoolean(7, mProject.getApproval());
				mPreparedStatement.setTimestamp(8, mProject.getStart());
				mPreparedStatement.setTimestamp(9, mProject.getFinish());
				java.util.Date date = new java.util.Date();
				mPreparedStatement.setTimestamp(10, new Timestamp(date.getTime()));
				
				mPreparedStatement.executeUpdate();
				
				mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Project SET ProjectCode = CONCAT(Year(Date), '_', idProject) WHERE ProjectCode is NULL");
		
				mPreparedStatement.executeUpdate();
				
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} 								
				
		}
		return false;
	}	

	public boolean DeleteProject(DatabaseConnection mConnection, Project mProject){
		if(mConnection.isConnected()){			
			try {
				
				PreparedStatement mPreparedStatement;
				mPreparedStatement = mConnection.getConnection().prepareStatement("DELETE FROM Project WHERE ProjectCode = ?");
				mPreparedStatement.setString(1, mProject.getProjectCode());
				mPreparedStatement.executeUpdate();
				
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} 								
				
		}
		return false;
	}

	public boolean Exists(DatabaseConnection mConnection, Project mProject){
		
		List<Project> mListProject = SelectProject(mConnection);
		for(Project p:mListProject){
			if(p.getId() == mProject.getId()) 
				return true;
		}
		return false;
	}
	
	public boolean UpdateProject(DatabaseConnection mConnection, Project mProject){
		
		if(mConnection.isConnected()){	
			if(!Exists(mConnection, mProject)){
						
				try {
					
					PreparedStatement mPreparedStatement;
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Project SET Name = ? WHERE idProject = ?");
					mPreparedStatement.setString(1, mProject.getName());
					mPreparedStatement.setString(2, mProject.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Project SET Category = ? WHERE idProject = ?");
					mPreparedStatement.setString(1, mProject.getCategory());
					mPreparedStatement.setString(2, mProject.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Project SET Brand = ? WHERE idProject = ?");
					mPreparedStatement.setString(1, mProject.getBrand());
					mPreparedStatement.setString(2, mProject.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Project SET OpCo = ? WHERE idProject = ?");
					mPreparedStatement.setString(1, mProject.getOpco());
					mPreparedStatement.setString(2, mProject.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Project SET EndMarket = ? WHERE idProject = ?");
					mPreparedStatement.setString(1, mProject.getEndMarket());
					mPreparedStatement.setString(2, mProject.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Project SET Complexity = ? WHERE idProject = ?");
					mPreparedStatement.setString(1, mProject.getComplexity());
					mPreparedStatement.setString(2, mProject.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Project SET Approval = ? WHERE idProject = ?");
					mPreparedStatement.setBoolean(1, mProject.getApproval());
					mPreparedStatement.setString(2, mProject.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Project SET Start = ? WHERE idProject = ?");
					mPreparedStatement.setTimestamp(1, mProject.getStart());
					mPreparedStatement.setString(2, mProject.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Project SET Finish = ? WHERE idProject = ?");
					mPreparedStatement.setTimestamp(1, mProject.getFinish());
					mPreparedStatement.setString(2, mProject.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Project SET Date = ? WHERE idProject = ?");
					mPreparedStatement.setTimestamp(1, mProject.getDate());
					mPreparedStatement.setString(2, mProject.getId());
					mPreparedStatement.executeUpdate();
					
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				} 								
			}
			return false;
		}
		return false;
	}
}
