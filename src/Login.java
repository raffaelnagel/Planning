import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Login {
	
	private String userLogin;
	private String userPwd;
	
	private JPanel panel = new JPanel();
	private JLabel lbPassword = new JLabel("Password:");
	private JLabel lbUser = new JLabel("User:");
	private JTextField txtLogin = new JTextField();
	private JPasswordField passw = new JPasswordField(10);
	
	public void Show(){
		panel.setLayout(new GridLayout(2,2));
		panel.add(lbUser);
		panel.add(txtLogin);
		panel.add(lbPassword);		
		panel.add(passw);
		
		String[] options = new String[]{"OK", "Cancel"};
		
		JOptionPane.showOptionDialog(null, panel, "Login",JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[0]);	
	}
	
	public boolean Log(DatabaseConnection mDataConnection){
		
		setUserLogin(txtLogin.getText());
		setUserPwd(new String(passw.getPassword()));
		
		boolean userFound = false;
		
		List<User> listUsers = mDataConnection.getUsersFromDatabase();
		
		for(User u:listUsers){
			if(u.getLogin().equals(userLogin)){
				userFound = true;
				if(u.getPassword().equals(userPwd)){
					JOptionPane.showMessageDialog(null,"Access Granted.");
					return true;
				}else{
					JOptionPane.showMessageDialog(null,"Wrong Password");
				}
			}
		}
		
		setUserPwd("");
		if(!userFound)
			JOptionPane.showMessageDialog(null,"Invalid User Name");
		
		return false;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
}
