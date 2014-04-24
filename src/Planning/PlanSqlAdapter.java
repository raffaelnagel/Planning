package Planning;
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
						
						Plan mPlan = new Plan(mProject, null, null);
						mPlan.setId(queryResult.getString("idPlan"));
						mPlan.setDate(queryResult.getTimestamp("Date"));
						mPlan.setActionPlan(queryResult.getString("ActionPlan"));
						
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
	
	public boolean InsertPlan(DatabaseConnection mConnection, Plan mPlan, Project mProject){
		if(mConnection.isConnected()){			
			try {
				
				PreparedStatement mPreparedStatement;
				mPreparedStatement = mConnection.getConnection().prepareStatement("INSERT INTO Plan(idProject, Date, ActionPlan) VALUES(?,?,?)");
				mPreparedStatement.setString(1, mProject.getId());
				mPreparedStatement.setTimestamp(2, mPlan.getDate());
				mPreparedStatement.setString(3, mPlan.getActionPlan());
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
