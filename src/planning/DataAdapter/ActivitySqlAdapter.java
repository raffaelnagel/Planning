package planning.DataAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import planning.Data.Activity;

public class ActivitySqlAdapter {
	
	public static List<Activity> selectActivity(DatabaseConnection mConnection, String idActivity){
		
		if(mConnection.isConnected()){
			try {
				Statement mStatement = mConnection.getConnection().createStatement();			
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM Activity WHERE idActivity = '" + idActivity + "'");
				
				try {					
					List<Activity> mListActivity = new ArrayList<Activity>();
					while(queryResult.next()){
						
						Activity mActivity = new Activity();
						mActivity.setId(queryResult.getString("idActivity"));	
						mActivity.setSite(queryResult.getString("Site"));
						mActivity.setArea(queryResult.getString("Area"));
						mActivity.setStage(queryResult.getString("Stage"));
						mActivity.setAction(queryResult.getString("Action"));
						mActivity.setUnit(queryResult.getString("Unit"));
						mActivity.setLeadtime(queryResult.getInt("LeadTime"));
						
						mListActivity.add(mActivity);
					}
					return mListActivity;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}
		
		return null;
	}
	
	public static List<Activity> selectActivity(DatabaseConnection mConnection, Activity mActivity){
		
		if(mConnection.isConnected()){
			try {
				Statement mStatement = mConnection.getConnection().createStatement();	
				
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM Activity WHERE Site = '" + mActivity.getSite() + "'");
				
				if(mActivity.getArea() != null){
					queryResult = mStatement.executeQuery("SELECT * FROM Activity WHERE Site = '" + mActivity.getSite() + "' AND Area = '" + mActivity.getArea() + "'");
				}
				if(mActivity.getStage() != null){
					queryResult = mStatement.executeQuery("SELECT * FROM Activity WHERE Site = '" + mActivity.getSite() + "' AND Area = '" + mActivity.getArea() + "' AND Stage = '" + mActivity.getStage() + "'");
				}
				if(mActivity.getUnit() != null){
					queryResult = mStatement.executeQuery("SELECT * FROM Activity WHERE Site = '" + mActivity.getSite() + "' AND Area = '" + mActivity.getArea() + "' AND Stage = '" + mActivity.getStage() + "' AND Unit = '" + mActivity.getUnit() + "'");
				}
				if(mActivity.getAction() != null){
					queryResult = mStatement.executeQuery("SELECT * FROM Activity WHERE Site = '" + mActivity.getSite() + "' AND Area = '" + mActivity.getArea() + "' AND Stage = '" + mActivity.getStage() + "' AND Unit = '" + mActivity.getUnit() + "' AND Action = '" + mActivity.getAction() + "'");
				}
				try {					
					List<Activity> mListActivity = new ArrayList<Activity>();
					while(queryResult.next()){
						
						Activity selectedActivity = new Activity();
						selectedActivity.setId(queryResult.getString("idActivity"));	
						selectedActivity.setSite(queryResult.getString("Site"));
						selectedActivity.setArea(queryResult.getString("Area"));
						selectedActivity.setStage(queryResult.getString("Stage"));
						selectedActivity.setAction(queryResult.getString("Action"));
						selectedActivity.setUnit(queryResult.getString("Unit"));
						selectedActivity.setLeadtime(queryResult.getInt("LeadTime"));
						
						mListActivity.add(selectedActivity);
					}
					return mListActivity;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}
		
		return null;
	}
		
	public static List<Activity> selectActivity(DatabaseConnection mConnection){
		
		if(mConnection.isConnected()){
			try {
				Statement mStatement = mConnection.getConnection().createStatement();			
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM Activity");
				
				try {					
					List<Activity> mListActivity = new ArrayList<Activity>();
					while(queryResult.next()){
						
						Activity mActivity = new Activity();
						mActivity.setId(queryResult.getString("idActivity"));	
						mActivity.setSite(queryResult.getString("Site"));
						mActivity.setArea(queryResult.getString("Area"));
						mActivity.setStage(queryResult.getString("Stage"));
						mActivity.setAction(queryResult.getString("Action"));
						mActivity.setUnit(queryResult.getString("Unit"));
						mActivity.setLeadtime(queryResult.getInt("LeadTime"));
						
						mListActivity.add(mActivity);
					}
					return mListActivity;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}
		
		return null;
	}
}
