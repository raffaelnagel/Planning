
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


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
		
		JPanel panel = new JPanel();
		JLabel lbPassword = new JLabel("Password:");
		JLabel lbUser = new JLabel("User:");
		JTextField txtLogin = new JTextField();
		JPasswordField passw = new JPasswordField(10);
		
		panel.setLayout(new GridLayout(2,2));
		panel.add(lbUser);
		panel.add(txtLogin);
		panel.add(lbPassword);		
		panel.add(passw);
		
		String[] options = new String[]{"OK", "Cancel"};
		
		JOptionPane.showOptionDialog(null, panel, "Login",JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[1]);
		
		String userLogin = txtLogin.getText();
		String userPwd = new String(passw.getPassword());
		
		
		boolean userFound = false;
		
		List<User> listUsers = mData.getUsersFromDatabase();
		
		for(User u:listUsers){
			if(u.getLogin().equals(userLogin)){
				userFound = true;
				if(u.getPassword().equals(userPwd)){
					JOptionPane.showMessageDialog(null,"Access Granted.");
				}else{
					JOptionPane.showMessageDialog(null,"Wrong Password");
				}
			}
		}
		
		userPwd = "";		
		if(!userFound)
			JOptionPane.showMessageDialog(null,"Invalid User Name");
		
		
		
		mData.closeConnection();		
	}
	
}
