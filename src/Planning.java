

import java.util.List;

public class Planning {

			
	public static void main(String args[]){
		
		String URL = "jdbc:mysql://localhost:3306/Planning";
		String login = "root";
		String pass = "root";
		
		DatabaseConnection mData = new DatabaseConnection(URL, login, pass);		
		mData.openConnection();
		
		FrameSearch newFrame = new FrameSearch();
		newFrame.show();
		
		//Login SystemLogin = new Login();
		//SystemLogin.Show();
		//boolean logged = SystemLogin.Log(mData);		
		
		/*
		if(logged){
		
			mData.updateUser(DatabaseConnection.SqlCommand.DELETE, new User("Jojo","Jojoxx","123"));
			
			List<User> mResult = mData.getUsersFromDatabase("name","Jojo");			
			if(mResult != null){
				System.out.println("-- SQL RESULTS --");
				for(User u:mResult){
					System.out.println("Id: " + u.getId() + " Name: " + u.getName() + " Login: " + u.getLogin() + " Senha: " + u.getPassword());
					System.out.println("----------------------------------------------");
				}
							
			}	
					
					
			Project p = new Project("Project 3", "BAU", "Pallmall", "RIL");
			mData.updateProject(DatabaseConnection.SqlCommand.INSERT, p);
			
			List<Project> listP = mData.getProjectsFromDatabase();			
			p = listP.get(listP.size()-1);
			mData.updateProject(DatabaseConnection.SqlCommand.DELETE, p);
			
		}
		*/
		mData.closeConnection();		
	}	
}
