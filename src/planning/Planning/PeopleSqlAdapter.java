package planning.Planning;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class PeopleSqlAdapter {
	
	public List<People> SelectPeople(DatabaseConnection mConnection){
		
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
	
	public List<People> SelectPeople(DatabaseConnection mConnection, String column, String value){
		
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
						mPeople.setCode("Code");
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

	public boolean InsertPeople(DatabaseConnection mConnection, People mPeople){
		if(mConnection.isConnected()){			
			try {
				
				PreparedStatement mPreparedStatement;
				mPreparedStatement = mConnection.getConnection().prepareStatement("INSERT INTO People(Name, Code) VALUES(?,?)");
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
	
	public boolean DeletePeople(DatabaseConnection mConnection, People mPeople){
		if(mConnection.isConnected()){			
			try {
				
				PreparedStatement mPreparedStatement;
				mPreparedStatement = mConnection.getConnection().prepareStatement("DELETE FROM People WHERE idPeople = ?");
				mPreparedStatement.setString(1, mPeople.getId());
				mPreparedStatement.executeUpdate();
				
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} 								
				
		}
		return false;
	}	
	
	public boolean Exists(DatabaseConnection mConnection, People mPeople){
		
		List<People> mListPeople = SelectPeople(mConnection);
		for(People p:mListPeople){
			if(p.getName().equals(mPeople.getName()) == true && p.getCode().equals(mPeople.getCode())) 
				return true;
		}
		return false;
	}
	
	public boolean UpdatePeople(DatabaseConnection mConnection, People mPeople){
		
		if(mConnection.isConnected()){	
			if(!Exists(mConnection, mPeople)){
						
				try {
					
					PreparedStatement mPreparedStatement;
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE People SET Name = ? WHERE idPeople = ?");
					mPreparedStatement.setString(1, mPeople.getName());
					mPreparedStatement.setString(2, mPeople.getId());
					mPreparedStatement.executeUpdate();
					
					mPreparedStatement = mConnection.getConnection().prepareStatement("UPDATE People SET Code = ? WHERE idPeople = ?");
					mPreparedStatement.setString(1, mPeople.getCode());
					mPreparedStatement.setString(2, mPeople.getId());
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
