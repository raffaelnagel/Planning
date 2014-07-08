package planning.Data;

import javax.swing.JOptionPane;

import planning.DataAdapter.DatabaseConnection;
import planning.Interface.FrameLogin;
import planning.Interface.FrameMain;


public class Planning {

	public static DatabaseConnection OpenConnection(){
		String URL = "jdbc:mysql://localhost:3306/Planning";
		String login = "root";
		String pass = "root";
		
		DatabaseConnection mData = new DatabaseConnection(URL, login, pass);		
		mData.openConnection();
		return(mData);
	}
			
	public static void main(String args[]){
		
		DatabaseConnection mData = Planning.OpenConnection();
		
		if(!mData.isConnected()){
			JOptionPane.showMessageDialog(null, "Database Not Found.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}		
		
		
		FrameLogin SystemLogin = new FrameLogin();
		SystemLogin.Show();
		try{
			People loggedUser = new People();
			loggedUser = SystemLogin.Log(mData);
			
			
			if(loggedUser != null){
				System.out.println(loggedUser.getLogin().getPermissionLevel());
				
				
				FrameMain mainFrame = new FrameMain(loggedUser);
				mainFrame.setVisible(true);
					
			}
		}catch(Exception e){			
			//loggin failed
		}
		
		mData.closeConnection();		
	}	
}
