package planning.DataAdapter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import planning.Data.Login;
import planning.Data.People;


public class LoginSqlAdapter {
	
	public static Login selectLogin(DatabaseConnection mConnection, People mPeople){
		
		if(mConnection.isConnected()){
			try {
				Statement mStatement = mConnection.getConnection().createStatement();			
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM Login WHERE idPeople = '" + mPeople.getId() + "'");
				
				try {			
					while(queryResult.next()){
						Login mLogin = new Login();
											
						mLogin.setUser(queryResult.getString("User"));
						mLogin.setPassword(queryResult.getString("Password"));
						mLogin.setPermissionLevel(queryResult.getInt("PermissionLevel"));
						
						return mLogin;
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}
		
		return null;
	}
	
	public static boolean insertLogin(DatabaseConnection mConnection, Login mLogin, People mPeople){
		if(mConnection.isConnected()){			
			try {
				
				PreparedStatement mPreparedStatement;
				mPreparedStatement = mConnection.getConnection().prepareStatement("INSERT INTO Login(idPeople, User, Password, PermissionLevel) VALUES(?,?,?,?)");
				mPreparedStatement.setString(1, mPeople.getId());
				mPreparedStatement.setString(2, mLogin.getUser());
				mPreparedStatement.setString(3, mLogin.getPassword());
				mPreparedStatement.setInt(4, mLogin.getPermissionLevel());
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
