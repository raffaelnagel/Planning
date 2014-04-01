

import java.util.List;

public class Planning {

			
	public static void main(String args[]){
		
		String URL = "jdbc:mysql://localhost:3306/Planning";
		String login = "root";
		String pass = "root";
		
		DatabaseConnection mData = new DatabaseConnection(URL, login, pass);		
		mData.openConnection();
		
		mData.updateUser(DatabaseConnection.SqlCommand.DELETE, new User("Jojo","Jojoxx","123"));
		
		List<User> mResult = mData.getUsersFromDatabase();			
		if(mResult != null){
			System.out.println("-- SQL RESULTS --");
			for(User u:mResult){
				System.out.println("Id: " + u.getId() + " Name: " + u.getName() + " Login: " + u.getLogin() + " Senha: " + u.getPassword());
				System.out.println("----------------------------------------------");
			}
						
		}	
		
		Login SystemLogin = new Login();
		boolean logged = false;
		
		SystemLogin.Show();
		logged = SystemLogin.Log(mData);
		
		System.out.println(logged);
		
		mData.closeConnection();		
	}
	
}
