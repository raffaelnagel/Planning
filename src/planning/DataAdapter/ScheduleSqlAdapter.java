package planning.DataAdapter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import planning.Data.Activity;
import planning.Data.Project;
import planning.Data.Schedule;


public class ScheduleSqlAdapter {

	public static List<Schedule> selectSchedule(DatabaseConnection mConnection){
		
		if(mConnection.isConnected()){
			try {
				Statement mStatement = mConnection.getConnection().createStatement();			
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM Schedule ");
				
				try {					
					List<Schedule> mListSchedule = new ArrayList<Schedule>();
					while(queryResult.next()){
						
						Schedule mSchedule = new Schedule(new Project());
						mSchedule.setId(queryResult.getString("idSchedule"));
						
						//Get Schedule Project
						String idProject = queryResult.getString("idProject");						
						List<Project> mListProject = ProjectSqlAdapter.selectProject(mConnection, "idProject", idProject);
						mSchedule.setProject(mListProject.get(0));
						
						//Get Schedule Activity
						String idActivity = queryResult.getString("idActivity");
						List<Activity> mListActivity = ActivitySqlAdapter.selectActivity(mConnection, idActivity);
						mSchedule.setActivity(mListActivity.get(0));
						
						mSchedule.setDemandedQtty(queryResult.getInt("DemandedQtty"));
						mSchedule.setCycle(queryResult.getInt("Cycle"));
						mSchedule.setNotes(queryResult.getString("Notes"));
						mSchedule.setDate(queryResult.getTimestamp("Date"));
						if(Schedule.isScheduleStatus(queryResult.getString("Status"))){
							mSchedule.setStatus(Schedule.ScheduleStatus.valueOf(queryResult.getString("Status")));
						}else{
							mSchedule.setStatus(Schedule.ScheduleStatus.UNKNOWN);
						}
						mSchedule.setAccomplishedDate(queryResult.getTimestamp("AccomplishedDate"));
						
						
						mListSchedule.add(mSchedule);
					}
					return mListSchedule;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}		
		return null;
	}
	
	public static List<Schedule> selectSchedule(DatabaseConnection mConnection, Project mProject){
		
		if(mConnection.isConnected()){
			try {
				Statement mStatement = mConnection.getConnection().createStatement();			
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM Schedule WHERE idProject = '" + mProject.getId() + "'");
				
				try {					
					List<Schedule> mListSchedule = new ArrayList<Schedule>();
					while(queryResult.next()){
						
						Schedule mSchedule = new Schedule(mProject);
						mSchedule.setId(queryResult.getString("idSchedule"));
						
						//Get Schedule Project
						String idProject = queryResult.getString("idProject");						
						List<Project> mListProject = ProjectSqlAdapter.selectProject(mConnection, "idProject", idProject);
						mSchedule.setProject(mListProject.get(0));
						
						//Get Schedule Activity
						String idActivity = queryResult.getString("idActivity");
						List<Activity> mListActivity = ActivitySqlAdapter.selectActivity(mConnection, idActivity);
						mSchedule.setActivity(mListActivity.get(0));
						
						mSchedule.setDemandedQtty(queryResult.getInt("DemandedQtty"));
						mSchedule.setCycle(queryResult.getInt("Cycle"));
						mSchedule.setNotes(queryResult.getString("Notes"));
						mSchedule.setDate(queryResult.getTimestamp("Date"));
						if(Schedule.isScheduleStatus(queryResult.getString("Status"))){
							mSchedule.setStatus(Schedule.ScheduleStatus.valueOf(queryResult.getString("Status")));
						}else{
							mSchedule.setStatus(Schedule.ScheduleStatus.UNKNOWN);
						}
						mSchedule.setAccomplishedDate(queryResult.getTimestamp("AccomplishedDate"));
						
						
						mListSchedule.add(mSchedule);
					}
					return mListSchedule;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}		
		return null;
	}
	
public static List<Schedule> selectSchedule(DatabaseConnection mConnection, String idSchedule){
		
		if(mConnection.isConnected()){
			try {
				Statement mStatement = mConnection.getConnection().createStatement();			
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM Schedule WHERE idSchedule = '" + idSchedule + "'");
				
				try {					
					List<Schedule> mListSchedule = new ArrayList<Schedule>();
					while(queryResult.next()){
						
						Schedule mSchedule = new Schedule(new Project());
						mSchedule.setId(queryResult.getString("idSchedule"));
						
						//Get Schedule Project
						String idProject = queryResult.getString("idProject");						
						List<Project> mListProject = ProjectSqlAdapter.selectProject(mConnection, "idProject", idProject);
						mSchedule.setProject(mListProject.get(0));
						
						//Get Schedule Activity
						String idActivity = queryResult.getString("idActivity");
						List<Activity> mListActivity = ActivitySqlAdapter.selectActivity(mConnection, idActivity);
						mSchedule.setActivity(mListActivity.get(0));
						
						mSchedule.setDemandedQtty(queryResult.getInt("DemandedQtty"));
						mSchedule.setCycle(queryResult.getInt("Cycle"));
						mSchedule.setNotes(queryResult.getString("Notes"));
						mSchedule.setDate(queryResult.getTimestamp("Date"));
						if(Schedule.isScheduleStatus(queryResult.getString("Status"))){
							mSchedule.setStatus(Schedule.ScheduleStatus.valueOf(queryResult.getString("Status")));
						}else{
							mSchedule.setStatus(Schedule.ScheduleStatus.UNKNOWN);
						}
						mSchedule.setAccomplishedDate(queryResult.getTimestamp("AccomplishedDate"));
						
						
						mListSchedule.add(mSchedule);
					}
					return mListSchedule;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}
		
		return null;
	}

	public static boolean insertSchedule(DatabaseConnection mConnection, Schedule mSchedule){
		if(mConnection.isConnected()){			
			try {
				
				PreparedStatement mPreparedStatement;
				mPreparedStatement = mConnection.getConnection().prepareStatement("INSERT INTO Schedule(idProject, idActivity, DemandedQtty, Cycle, Notes, Date, Status, AccomplishedDate) VALUES(?,?,?,?,?,?,?,?)");
				mPreparedStatement.setString(1, mSchedule.getProject().getId());
				mPreparedStatement.setString(2, mSchedule.getActivity().getId());
				mPreparedStatement.setInt(3, mSchedule.getDemandedQtty());				
				mPreparedStatement.setInt(4, mSchedule.getCycle());
				mPreparedStatement.setString(5, mSchedule.getNotes());
				mPreparedStatement.setTimestamp(6, mSchedule.getDate());
				mPreparedStatement.setString(7, mSchedule.getStatus().toString());
				mPreparedStatement.setTimestamp(8, mSchedule.getAccomplishedDate());
				mPreparedStatement.executeUpdate();
				
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} 	
		}
		return false;
	}	
	
	public static boolean deleteSchedule(DatabaseConnection mConnection, Schedule mSchedule){
		if(mConnection.isConnected()){			
			try {
				
				PreparedStatement mPreparedStatement;
				mPreparedStatement = mConnection.getConnection().prepareStatement("DELETE FROM Schedule WHERE idSchedule = ? ");
				mPreparedStatement.setString(1, mSchedule.getId());			
				
				mPreparedStatement.executeUpdate();
				
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} 
		}
		return false;
	}	
	
	public static boolean exists(DatabaseConnection mConnection, Schedule mSchedule){
		
		List<Schedule> mListSchedule = selectSchedule(mConnection);
		for(Schedule p:mListSchedule){
			if(p.getId().compareTo(mSchedule.getId()) == 0) 
				return true;
		}
		return false;
	}
	
	public static boolean updateSchedule(DatabaseConnection mConnection, Schedule mSchedule){
		
		if(mConnection.isConnected()){	
			if(exists(mConnection, mSchedule)){
						
				try {
					
					PreparedStatement mPreparedStatement;
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Schedule SET idActivity = ? WHERE idSchedule = ?");
					mPreparedStatement.setString(1, mSchedule.getActivity().getId());
					mPreparedStatement.setString(2, mSchedule.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Schedule SET DemandedQtty = ? WHERE idSchedule = ?");
					mPreparedStatement.setInt(1, mSchedule.getDemandedQtty());
					mPreparedStatement.setString(2, mSchedule.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Schedule SET Cycle = ? WHERE idSchedule = ?");
					mPreparedStatement.setInt(1, mSchedule.getCycle());
					mPreparedStatement.setString(2, mSchedule.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Schedule SET Notes = ? WHERE idSchedule = ?");
					mPreparedStatement.setString(1, mSchedule.getNotes());
					mPreparedStatement.setString(2, mSchedule.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Schedule SET Date = ? WHERE idSchedule = ?");
					mPreparedStatement.setTimestamp(1, mSchedule.getDate());
					mPreparedStatement.setString(2, mSchedule.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Schedule SET Status = ? WHERE idSchedule = ?");
					mPreparedStatement.setString(1, mSchedule.getStatus().toString());
					mPreparedStatement.setString(2, mSchedule.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE Schedule SET AccomplishedDate = ? WHERE idSchedule = ?");
					mPreparedStatement.setTimestamp(1, mSchedule.getAccomplishedDate());
					mPreparedStatement.setString(2, mSchedule.getId());
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
