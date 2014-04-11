import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
	
	private String URL;
	private String login;
	private String password;
	private boolean connected;
	public Connection mConnection;
	
	public Connection getConnection(){
		return mConnection;
	}
	
	public void setConnection(Connection mConnection){
		this.mConnection = mConnection;
	}	
	
    public DatabaseConnection(String url, String user, String pass){
    	this.URL = url;
    	this.login = user;
    	this.password = pass;
    }
    
    public boolean isConnected(){
    	return connected;
    }    
    
    public void openConnection(){
    	try{
    		//Class.forName("com.mysql.jdbc.Driver").newInstance();
    		mConnection = DriverManager.getConnection(URL, login, password);
    		
    		if(null != this.mConnection){
    			connected = true;
    		}else{
    			connected = false;
    		}
    		
    	}catch(Exception e){
    		System.out.println("Connection Error.");
    		e.printStackTrace();
    		System.out.println(e.getMessage());    		
    	}
    }	
    
	public void closeConnection() {
		try {
			mConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mConnection = null;
		connected = false;
	}
	
}
