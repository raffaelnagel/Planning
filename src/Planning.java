
import javax.swing.JOptionPane;


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
		boolean logged = SystemLogin.Log(mData);		
		if(logged){
			System.out.println("Logged");
			
			
			FrameMain mainFrame = new FrameMain();
			mainFrame.setVisible(true);
			
				
		}
		
		mData.closeConnection();		
	}	
}
