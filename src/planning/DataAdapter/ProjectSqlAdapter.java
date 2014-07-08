package planning.DataAdapter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import planning.Data.AuxiliarData;
import planning.Data.People;
import planning.Data.Project;


public class ProjectSqlAdapter {

	public static List<Project> selectProject(DatabaseConnection mConnection){
		
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
						
						mProject.setCategory(AuxiliarDataSqlAdapter.selectAuxiliarData(mConnection, AuxiliarData.AuxiliarDataTypes.ProjectCategory, queryResult.getString("idCategory")));						
						mProject.setOpco(AuxiliarDataSqlAdapter.selectAuxiliarData(mConnection, AuxiliarData.AuxiliarDataTypes.OpCo, queryResult.getString("idOpCo")));
						mProject.setEndMarket(AuxiliarDataSqlAdapter.selectAuxiliarData(mConnection, AuxiliarData.AuxiliarDataTypes.EndMarket, queryResult.getString("idEndMarket")));
						
						mProject.setBrand(BrandSqlAdapter.selectBrand(mConnection, queryResult.getString("idBrand")));
						
						if(Project.isProjectComplexity(queryResult.getString("Complexity"))){
							mProject.setComplexity(Project.ProjectComplexity.valueOf(queryResult.getString("Complexity")));
						}else{
							mProject.setComplexity(Project.ProjectComplexity.CAP1);
						}							
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
	
	public static List<Project> selectProject(DatabaseConnection mConnection, String column, String value){
		
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
						mProject.setPitCode(AuxiliarDataSqlAdapter.selectAuxiliarData(mConnection, AuxiliarData.AuxiliarDataTypes.PitCode, queryResult.getString("idPitCode")));
						mProject.setMainProject(AuxiliarDataSqlAdapter.selectAuxiliarData(mConnection, AuxiliarData.AuxiliarDataTypes.MainProject, queryResult.getString("idMainProject")));
						mProject.setCategory(AuxiliarDataSqlAdapter.selectAuxiliarData(mConnection, AuxiliarData.AuxiliarDataTypes.ProjectCategory, queryResult.getString("idCategory")));						
						mProject.setOpco(AuxiliarDataSqlAdapter.selectAuxiliarData(mConnection, AuxiliarData.AuxiliarDataTypes.OpCo, queryResult.getString("idOpCo")));
						mProject.setEndMarket(AuxiliarDataSqlAdapter.selectAuxiliarData(mConnection, AuxiliarData.AuxiliarDataTypes.EndMarket, queryResult.getString("idEndMarket")));
						
						mProject.setBrand(BrandSqlAdapter.selectBrand(mConnection, queryResult.getString("idBrand")));
						if(Project.isProjectComplexity(queryResult.getString("Complexity"))){
							mProject.setComplexity(Project.ProjectComplexity.valueOf(queryResult.getString("Complexity")));
						}else{
							mProject.setComplexity(Project.ProjectComplexity.CAP1);
						}
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
	
	public static List<Project> selectProject(DatabaseConnection mConnection, People p){
		
		if(mConnection.isConnected()){						
			try {
				Statement mStatement = mConnection.getConnection().createStatement();			
				ResultSet queryResult = mStatement.executeQuery("SELECT Project.* from Project JOIN Team ON Project.idProject = Team.idProject WHERE Team.idPeople = "+p.getId() +" AND Team.Responsability = 'LEADER' ");
				
				try {					
					List<Project> mListProject = new ArrayList<Project>();
					while(queryResult.next()){
						
						Project mProject = new Project();
						mProject.setId(queryResult.getString("idProject"));
						mProject.setProjectCode(queryResult.getString("ProjectCode"));
						mProject.setName(queryResult.getString("Name"));
						mProject.setPitCode(AuxiliarDataSqlAdapter.selectAuxiliarData(mConnection, AuxiliarData.AuxiliarDataTypes.PitCode, queryResult.getString("idPitCode")));
						mProject.setMainProject(AuxiliarDataSqlAdapter.selectAuxiliarData(mConnection, AuxiliarData.AuxiliarDataTypes.MainProject, queryResult.getString("idMainProject")));
						mProject.setCategory(AuxiliarDataSqlAdapter.selectAuxiliarData(mConnection, AuxiliarData.AuxiliarDataTypes.ProjectCategory, queryResult.getString("idCategory")));						
						mProject.setOpco(AuxiliarDataSqlAdapter.selectAuxiliarData(mConnection, AuxiliarData.AuxiliarDataTypes.OpCo, queryResult.getString("idOpCo")));
						mProject.setEndMarket(AuxiliarDataSqlAdapter.selectAuxiliarData(mConnection, AuxiliarData.AuxiliarDataTypes.EndMarket, queryResult.getString("idEndMarket")));
						
						mProject.setBrand(BrandSqlAdapter.selectBrand(mConnection, queryResult.getString("idBrand")));
						if(Project.isProjectComplexity(queryResult.getString("Complexity"))){
							mProject.setComplexity(Project.ProjectComplexity.valueOf(queryResult.getString("Complexity")));
						}else{
							mProject.setComplexity(Project.ProjectComplexity.CAP1);
						}
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

	public static boolean insertProject(DatabaseConnection mConnection, Project mProject){
		if(mConnection.isConnected()){			
			try {
				
				PreparedStatement mPreparedStatement;
				mPreparedStatement = mConnection.getConnection().prepareStatement("INSERT INTO Project(Name, idPitCode, idMainProject, idCategory, idBrand, idOpCo, idEndMarket, Complexity, Approval, Start, Finish, Date) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
				mPreparedStatement.setString(1, mProject.getName());
				mPreparedStatement.setString(2, mProject.getPitCode().getId());
				mPreparedStatement.setString(3, mProject.getMainProject().getId());
				mPreparedStatement.setString(4, mProject.getCategory().getId());
				mPreparedStatement.setString(5, mProject.getBrand().getId());
				mPreparedStatement.setString(6, mProject.getOpco().getId());
				mPreparedStatement.setString(7, mProject.getEndMarket().getId());
				mPreparedStatement.setString(8, mProject.getComplexity().toString());
				mPreparedStatement.setBoolean(9, mProject.isApproval());
				mPreparedStatement.setTimestamp(10, mProject.getStart());
				mPreparedStatement.setTimestamp(11, mProject.getFinish());
				java.util.Date date = new java.util.Date();
				mPreparedStatement.setTimestamp(12, new Timestamp(date.getTime()));
				
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

	public static boolean deleteProject(DatabaseConnection mConnection, Project mProject){
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

	public static boolean exists(DatabaseConnection mConnection, Project mProject){
		
		List<Project> mListProject = selectProject(mConnection);
		for(Project p:mListProject){
			if(p.getId().compareTo(mProject.getId()) == 0) 
				return true;
		}
		return false;
	}
	
	public static boolean updateProject(DatabaseConnection mConnection, Project mProject){
		
		if(mConnection.isConnected()){	
			if(exists(mConnection, mProject)){
						
				try {
					
					PreparedStatement mPreparedStatement;
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Project SET Name = ? WHERE idProject = ?");
					mPreparedStatement.setString(1, mProject.getName());
					mPreparedStatement.setString(2, mProject.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Project SET idPitCode = ? WHERE idProject = ?");
					mPreparedStatement.setString(1, mProject.getPitCode().getId());
					mPreparedStatement.setString(2, mProject.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Project SET idMainProject = ? WHERE idProject = ?");
					mPreparedStatement.setString(1, mProject.getMainProject().getId());
					mPreparedStatement.setString(2, mProject.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Project SET idCategory = ? WHERE idProject = ?");
					mPreparedStatement.setString(1, mProject.getCategory().getId());
					mPreparedStatement.setString(2, mProject.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Project SET idBrand = ? WHERE idProject = ?");
					mPreparedStatement.setString(1, mProject.getBrand().getId());
					mPreparedStatement.setString(2, mProject.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Project SET idOpCo = ? WHERE idProject = ?");
					mPreparedStatement.setString(1, mProject.getOpco().getId());
					mPreparedStatement.setString(2, mProject.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Project SET idEndMarket = ? WHERE idProject = ?");
					mPreparedStatement.setString(1, mProject.getEndMarket().getId());
					mPreparedStatement.setString(2, mProject.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Project SET Complexity = ? WHERE idProject = ?");
					mPreparedStatement.setString(1, mProject.getComplexity().toString());
					mPreparedStatement.setString(2, mProject.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Project SET Approval = ? WHERE idProject = ?");
					mPreparedStatement.setBoolean(1, mProject.isApproval());
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
