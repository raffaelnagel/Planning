package planning.Interface;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import planning.Planning.DatabaseConnection;
import planning.Planning.Login;
import planning.Planning.LoginSqlAdapter;
import planning.Planning.People;
import planning.Planning.PeopleSqlAdapter;



public class FrameLogin {
	
	private Login newLogin = new Login();
	private PeopleSqlAdapter mPeopleSqlAdapter = new PeopleSqlAdapter();
	private LoginSqlAdapter mLoginSqlAdapter = new LoginSqlAdapter();
	
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
	
	public People Log(DatabaseConnection mDataConnection){
		
		newLogin.setUser(txtLogin.getText());
		newLogin.setPassword(new String(passw.getPassword()));
		
		boolean userFound = false;
		
		List<People> mListPeople = mPeopleSqlAdapter.SelectPeople(mDataConnection);
			
		Login pLogin = new Login();
		for(People p:mListPeople){
			
			pLogin = mLoginSqlAdapter.SelectLogin(mDataConnection, p);
			
			if(pLogin.getUser().equals(newLogin.getUser())){
				userFound = true;
				if(pLogin.getPassword().equals(newLogin.getPassword())){
					JOptionPane.showMessageDialog(null,"Access Granted.");
					p.setLogin(pLogin);
					return p;
				}else{
					JOptionPane.showMessageDialog(null,"Wrong Password");
				}
			}
		}
		
		newLogin = null;
		
		if(!userFound)
			JOptionPane.showMessageDialog(null,"Invalid User Name");
		
		return null;
	}

}
