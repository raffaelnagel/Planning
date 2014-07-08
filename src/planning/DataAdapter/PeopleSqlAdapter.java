package planning.DataAdapter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import planning.Data.People;


public class PeopleSqlAdapter {
	
	public static List<People> selectPeople(DatabaseConnection mConnection){
		
		if(mConnection.isConnected()){
			try {
				Statement mStatement = mConnection.getConnection().createStatement();			
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM People");
				
				try {					
					List<People> mListPeople = new ArrayList<People>();
					while(queryResult.next()){
						
						People mPeople = new People();
						mPeople.setId(queryResult.getString("idPeople"));
						mPeople.setName(queryResult.getString("Name"));
						mPeople.setCode(queryResult.getString("Code"));
						mPeople.setWorkgroup(queryResult.getString("Workgroup"));
						mPeople.setLogin(null);
						mListPeople.add(mPeople);
					}
					return mListPeople;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}
		
		return null;
	}
	
	public static List<People> selectPeople(DatabaseConnection mConnection, String column, String value){
		
		if(mConnection.isConnected()){
			try {
				Statement mStatement = mConnection.getConnection().createStatement();			
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM People WHERE " + column + " = '" + value + "'");
				
				try {					
					List<People> mListPeople = new ArrayList<People>();
					while(queryResult.next()){
						
						People mPeople = new People();
						mPeople.setId(queryResult.getString("idPeople"));
						mPeople.setName(queryResult.getString("Name"));
						mPeople.setCode(queryResult.getString("Code"));
						mPeople.setWorkgroup(queryResult.getString("Workgroup"));
						mPeople.setLogin(null);
						mListPeople.add(mPeople);
					}
					return mListPeople;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}
		
		return null;
	}

	public static boolean  insertPeople(DatabaseConnection mConnection, People mPeople){
		if(mConnection.isConnected()){			
			try {
				
				PreparedStatement mPreparedStatement;
				mPreparedStatement = mConnection.getConnection().prepareStatement("INSERT INTO People(Name, Code, Workgroup) VALUES(?,?,?)");
				mPreparedStatement.setString(1, mPeople.getName());
				mPreparedStatement.setString(2, mPeople.getCode());
				mPreparedStatement.setString(3, mPeople.getWorkgroup());
				mPreparedStatement.executeUpdate();
				
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} 								
				
		}
		return false;
	}	
	
	public static boolean deletePeople(DatabaseConnection mConnection, People mPeople){
		if(mConnection.isConnected()){			
			try {
				
				PreparedStatement mPreparedStatement;
				mPreparedStatement = mConnection.getConnection().prepareStatement("DELETE FROM People WHERE Name = ? AND Code = ?");
				mPreparedStatement.setString(1, mPeople.getName());
				mPreparedStatement.setString(2, mPeople.getCode());
				mPreparedStatement.executeUpdate();
				
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} 								
				
		}
		return false;
	}	
	
	public static boolean exists(DatabaseConnection mConnection, People mPeople){
		
		List<People> mListPeople = selectPeople(mConnection);
		for(People p:mListPeople){
			if(p.getName().equals(mPeople.getName()) == true && p.getCode().equals(mPeople.getCode())) 
				return true;
		}
		return false;
	}
	
	public static boolean testCodeAvailability(DatabaseConnection mConnection, String people_code){
		
		List<People> mListPeople = selectPeople(mConnection);
		for(People p:mListPeople){
			if(p.getCode().equals(people_code)) 
				return false;
		}
		return true;
	}
	
	public static boolean updatePeople(DatabaseConnection mConnection, People oldPeople, People newPeople){
		
		if(mConnection.isConnected()){	
			if(exists(mConnection, oldPeople) == true){						
				try {					
					if(newPeople.getName().length() > 0){
						PreparedStatement mPreparedStatement;
						mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE People SET Name = ? WHERE idPeople = ?");
						mPreparedStatement.setString(1, newPeople.getName());
						mPreparedStatement.setString(2, oldPeople.getId());
						mPreparedStatement.executeUpdate();
					}
					if(newPeople.getCode().length() > 0){
						PreparedStatement mPreparedStatement;
						mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE People SET Code = ? WHERE idPeople = ?");
						mPreparedStatement.setString(1, newPeople.getCode());
						mPreparedStatement.setString(2, oldPeople.getId());
						mPreparedStatement.executeUpdate();
					}
					if(newPeople.getWorkgroup().length() > 0){
						PreparedStatement mPreparedStatement;
						mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE People SET Workgroup = ? WHERE idPeople = ?");
						mPreparedStatement.setString(1, newPeople.getWorkgroup());
						mPreparedStatement.setString(2, oldPeople.getId());
						mPreparedStatement.executeUpdate();
					}					
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
