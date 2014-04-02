import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
	
	private String URL;
	private String login;
	private String password;
	private boolean connected;
	public Connection mConnection;
	
	public static enum SqlCommand {
	    INSERT, DELETE;
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
		
	public List<User> getUsersFromDatabase(){
		if(this.isConnected()){						
			try {
				Statement mStatement = mConnection.createStatement();			
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM User");
				
				try {					
					List<User> mListUser = new ArrayList<User>();
					while(queryResult.next()){
						
						User mUser = new User();
						mUser.setId(queryResult.getString("id"));
						mUser.setName(queryResult.getString("name"));
						mUser.setLogin(queryResult.getString("login"));
						mUser.setPassword(queryResult.getString("password"));
						mListUser.add(mUser);
					}
					return mListUser;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}		
		return null;
	}
	
	public List<User> getUsersFromDatabase(String Column, String Value){
		if(this.isConnected()){						
			try {
				Statement mStatement = mConnection.createStatement();			
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM User WHERE " + Column + " = '" + Value + "'");
				
				try {					
					List<User> mListUser = new ArrayList<User>();
					while(queryResult.next()){
						
						User mUser = new User();
						mUser.setId(queryResult.getString("id"));
						mUser.setName(queryResult.getString("name"));
						mUser.setLogin(queryResult.getString("login"));
						mUser.setPassword(queryResult.getString("password"));
						mListUser.add(mUser);
					}
					return mListUser;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}		
		return null;
	}
	
	public List<Project> getProjectsFromDatabase(){
		if(this.isConnected()){						
			try {
				Statement mStatement = mConnection.createStatement();			
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM Project");
				
				try {					
					List<Project> mListProject = new ArrayList<Project>();
					while(queryResult.next()){
						
						Project mProject = new Project();
						mProject.setId(queryResult.getString("id"));
						mProject.setPid(queryResult.getString("Pid"));
						mProject.setName(queryResult.getString("Name"));
						mProject.setCategory(queryResult.getString("Category"));
						mProject.setBrand(queryResult.getString("Brand"));
						mProject.setLeader(queryResult.getString("Leader"));
						mProject.setDate(queryResult.getString("Date"));
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
	
	public boolean LoginAlreadyExists(String login){
		
		List<User> mList = getUsersFromDatabase();
		for(User u:mList){
			if(u.getLogin().equals(login)) 
				return true;
		}
		
		return false;
	}
	
	public boolean updateUser(SqlCommand Cmd, User mUser){
		if(this.isConnected()){
			if(!LoginAlreadyExists(mUser.getLogin())){				
				PreparedStatement mPreparedStatement;
				try {
					switch(Cmd){
					case INSERT:
						mPreparedStatement = mConnection.prepareStatement("INSERT INTO User(name,login,password) VALUES(?,?,?)");
						mPreparedStatement.setString(1, mUser.getName());
						mPreparedStatement.setString(2, mUser.getLogin());
						mPreparedStatement.setString(3, mUser.getPassword());
						break;
					case DELETE:
						mPreparedStatement = mConnection.prepareStatement("DELETE FROM User WHERE login=?");
						mPreparedStatement.setString(1, mUser.getLogin());
						break;
					default:
						return false;
					}							
					mPreparedStatement.executeUpdate();
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				} 
			}
		}
		return false;
	}	
	
	public boolean updateProject(SqlCommand Cmd, Project mProject){
		if(this.isConnected()){				
				PreparedStatement mPreparedStatement, mStatementUpdate=null;
				try {
					switch(Cmd){
					case INSERT:
						mPreparedStatement = mConnection.prepareStatement("INSERT INTO Project(Name,Category,Brand,Leader) VALUES(?,?,?,?)");
						mStatementUpdate = mConnection.prepareStatement("UPDATE Project SET Pid = CONCAT(YEAR(Date), '_', id) WHERE Pid is NULL");
						mPreparedStatement.setString(1, mProject.getName());
						mPreparedStatement.setString(2, mProject.getCategory());
						mPreparedStatement.setString(3, mProject.getBrand());
						mPreparedStatement.setString(4, mProject.getLeader());
						break;
					case DELETE:
						mPreparedStatement = mConnection.prepareStatement("DELETE FROM Project WHERE Pid=?");
						mPreparedStatement.setString(1, mProject.getPid());
						break;
					default:
						return false;
					}							
					mPreparedStatement.executeUpdate();
					if(null != mStatementUpdate)
						mStatementUpdate.executeUpdate();
					
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				} 			
		}
		return false;
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
