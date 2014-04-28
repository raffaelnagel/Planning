package Interface;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class FrameMain extends JFrame{

	JLabel lbTitle = new JLabel();
	JLabel lbUserPermission = new JLabel();
	JPanel panel = new JPanel();
	
	private JMenuBar Menu = new JMenuBar();	
	private JMenu menuFile = new JMenu();
	//Menu Project
	private JMenu menuProject = new JMenu();
	private JMenuItem menuNewProject = new JMenuItem();
	private JMenuItem menuListProject = new JMenuItem(); 
	//Menu People
	private JMenu menuPeople = new JMenu();
	private JMenuItem menuNewPeople = new JMenuItem();
	private JMenuItem menuListPeople = new JMenuItem();
	
	//Menu File
	private JMenuItem menuClose = new JMenuItem(); 
	private JMenuItem menuMain = new JMenuItem(); 
	
	public FrameMain(int permissionLevel){
		
		setSize(940,680);		
		setTitle("Planning System");
		setLocationRelativeTo(null);		
		//setResizable(false);
		setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.setVisible(true);		
		
		//MENU Project
		menuNewProject.setText("New Project");
		menuNewProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                getContentPane().removeAll();
                PanelProjectNew fmNewProject = new PanelProjectNew();
                getContentPane().add(fmNewProject);
                getContentPane().validate();
                getContentPane().repaint();
            }
        });
		menuListProject.setText("Projects List");
		menuListProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                getContentPane().removeAll();
                PanelProjectList fmListProjects = new PanelProjectList();
                getContentPane().add(fmListProjects);
                getContentPane().validate();
                getContentPane().repaint();
            }
        });
		menuProject.setText("Project");
		menuProject.add(menuNewProject);
		menuProject.add(menuListProject);
			
		//Menu People
		menuNewPeople.setText("New Employer");
		menuListPeople.setText("Employers List");
		menuPeople.setText("Employer");
		menuPeople.add(menuNewPeople);
		menuPeople.add(menuListPeople);
		
		//Menu File
		menuMain.setText("Home");
		menuMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	getContentPane().removeAll();
            	getContentPane().add(panel);
            	getContentPane().validate();
            	getContentPane().repaint();
            }
        });
		menuClose.setText("Close");
		menuClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });		
		menuFile.setText("File");
		menuFile.add(menuMain);
		menuFile.addSeparator();
		menuFile.add(menuClose);
		
		
		//Menu Toolbar
		Menu.add(menuFile); 
		Menu.add(menuProject);	
		Menu.add(menuPeople);	
		
		this.setJMenuBar(Menu); 
		
		//-----PANEL----------------------------------------------
		lbTitle.setText("Planning");
		lbTitle.setFont(new Font("Droid Sans Fallback", Font.PLAIN, 110));
		lbTitle.setForeground(Color.red);	
		
		if(permissionLevel == 0){
			lbUserPermission.setText("admin");			
		}else{
			lbUserPermission.setText("user");
			menuListProject.setEnabled(false);
		}
		
		lbUserPermission.setForeground(Color.ORANGE);	
		lbUserPermission.setFont(new Font("Ubuntu Condensed", Font.PLAIN, 20));
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 40;      //make this component tall
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		
	    panel.add(lbTitle, c);
	    
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.ipady = 5;       //reset to default
	    c.weighty = 1.0;   //request any extra vertical space
	    c.gridx = 0;
	    c.gridy = 1;
	    c.insets = new Insets(110,295,0,0);  //top padding
	    panel.add(lbUserPermission, c);
        //panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		this.add(panel);
	}
}
