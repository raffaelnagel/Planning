package planning.Planning;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class PlanSqlAdapter {

	public List<Plan> SelectPlan(DatabaseConnection mConnection, Project mProject){
		
		if(mConnection.isConnected()){
			try {
				Statement mStatement = mConnection.getConnection().createStatement();			
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM Plan WHERE idProject = '" + mProject.getId() + "'");
				
				try {					
					List<Plan> mListPlan = new ArrayList<Plan>();
					while(queryResult.next()){
						
						Plan mPlan = new Plan(mProject);
						mPlan.setId(queryResult.getString("idPlan"));
						mPlan.setSite(queryResult.getString("Site"));
						mPlan.setArea(queryResult.getString("Area"));
						mPlan.setStage(queryResult.getString("Stage"));
						mPlan.setActionPlan(queryResult.getString("ActionPlan"));
						mPlan.setDemandedQtty(queryResult.getInt("DemandedQtty"));
						mPlan.setUnit(queryResult.getString("Unit"));
						mPlan.setCycle(queryResult.getInt("Cycle"));
						mPlan.setNotes(queryResult.getString("Notes"));
						mPlan.setDate(queryResult.getTimestamp("Date"));
						mPlan.setLeadTime(queryResult.getInt("LeadTime"));
						if(Plan.isPlainStatus(queryResult.getString("Status"))){
							mPlan.setStatus(Plan.PlanStatus.valueOf(queryResult.getString("Status")));
						}else{
							mPlan.setStatus(Plan.PlanStatus.UNKNOWN);
						}
						mPlan.setAccomplishedDate(queryResult.getTimestamp("AccomplishedDate"));
						
						
						mListPlan.add(mPlan);
					}
					return mListPlan;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}
		
		return null;
	}
	
	public boolean InsertPlan(DatabaseConnection mConnection, Plan mPlan){
		if(mConnection.isConnected()){			
			try {
				
				PreparedStatement mPreparedStatement;
				mPreparedStatement = mConnection.getConnection().prepareStatement("INSERT INTO Plan(idProject, Site, Area, Stage, ActionPlan, DemandedQtty, Unit, Cycle, Notes, Date, LeadTime, Status, AccomplishedDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
				mPreparedStatement.setString(1, mPlan.getProject().getId());
				mPreparedStatement.setString(2, mPlan.getSite());
				mPreparedStatement.setString(3, mPlan.getArea());
				mPreparedStatement.setString(4, mPlan.getStage());
				mPreparedStatement.setString(5, mPlan.getActionPlan());
				mPreparedStatement.setInt(6, mPlan.getDemandedQtty());
				mPreparedStatement.setString(7, mPlan.getUnit());
				mPreparedStatement.setInt(8, mPlan.getCycle());
				mPreparedStatement.setString(9, mPlan.getNotes());
				mPreparedStatement.setTimestamp(10, mPlan.getDate());
				mPreparedStatement.setInt(11, mPlan.getLeadTime());
				mPreparedStatement.setString(12, mPlan.getStatus().toString());
				mPreparedStatement.setTimestamp(13, mPlan.getAccomplishedDate());
				mPreparedStatement.executeUpdate();
				
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} 								
				
		}
		return false;
	}	
	
}
