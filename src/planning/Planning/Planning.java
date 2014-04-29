package planning.Planning;

import javax.swing.JOptionPane;

import planning.Interface.FrameLogin;
import planning.Interface.FrameMain;


public class Planning {

			
	public static void main(String args[]){
		
		String URL = "jdbc:mysql://localhost:3306/Planning";
		String login = "root";
		String pass = "root";
		
		DatabaseConnection mData = new DatabaseConnection(URL, login, pass);		
		mData.openConnection();
		
		if(!mData.isConnected()){
			JOptionPane.showMessageDialog(null, "Database Not Found.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}		
		
		
		FrameLogin SystemLogin = new FrameLogin();
		SystemLogin.Show();
		int userPermission = SystemLogin.Log(mData);		
		if(userPermission > -1){
			System.out.println(userPermission);
			
			
			FrameMain mainFrame = new FrameMain(userPermission);
			mainFrame.setVisible(true);
				
		}
		
		mData.closeConnection();		
	}	
}
