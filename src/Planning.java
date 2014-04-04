
public class Planning {

			
	public static void main(String args[]){
		
		String URL = "jdbc:mysql://localhost:3306/Planning";
		String login = "root";
		String pass = "root";
		
		DatabaseConnection mData = new DatabaseConnection(URL, login, pass);		
		mData.openConnection();
		
		
		
		Login SystemLogin = new Login();
		SystemLogin.Show();
		boolean logged = SystemLogin.Log(mData);		
		if(logged){
		
			FrameSearch newFrame = new FrameSearch();
			newFrame.show();
		
		}
		
		mData.closeConnection();		
	}	
}
