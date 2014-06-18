package planning.Interface;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import planning.Planning.DatabaseConnection;
import planning.Planning.People;
import planning.Planning.Plan;
import planning.Planning.PlanSqlAdapter;
import planning.Planning.Planning;
import planning.Planning.Project;
import planning.Planning.ProjectSqlAdapter;


@SuppressWarnings("serial")
public class FrameMain extends JFrame{

	private JLabel lbTitle = new JLabel();
	private JLabel lbUserName = new JLabel();
	private JPanel panel = new JPanel();
	private JButton iconProject = new JButton(), iconPlans = new JButton();
	private JLabel lbNumberProject = new JLabel(), lbNumberPlans = new JLabel();
	
	private JMenuBar Menu = new JMenuBar();	
	private JMenu menuFile = new JMenu();
	//Menu Project
	private JMenu menuProject = new JMenu();
	private JMenuItem menuNewProject = new JMenuItem();
	private JMenuItem menuEditProject = new JMenuItem();
	private JMenuItem menuListProject = new JMenuItem();
	private JMenuItem menuTeamProject = new JMenuItem();
	//Menu People
	private JMenu menuPeople = new JMenu();
	private JMenuItem menuManagePeople = new JMenuItem();
	//Menu Plan
	private JMenu menuPlan = new JMenu();
	private JMenuItem menuNewPlan = new JMenuItem();
	//Menu File
	private JMenuItem menuClose = new JMenuItem(); 
	private JMenuItem menuMain = new JMenuItem(); 
	
	public void OpenPanelEdit(People loggedUser, String pcode){
		getContentPane().removeAll();
        PanelProjectEdit fmEditProject = new PanelProjectEdit(loggedUser, pcode);
        getContentPane().add(fmEditProject);
        getContentPane().validate();
        getContentPane().repaint();
	}
	
	private void RefreshIcons(People loggedUser){
		DatabaseConnection mData = Planning.OpenConnection();
		List<Project> mListProject = new ArrayList<Project>();
		ProjectSqlAdapter mProjectSqlAdapter = new ProjectSqlAdapter();
		mListProject = mProjectSqlAdapter.SelectProject(mData, loggedUser);
		
		lbNumberProject.setText(String.valueOf(mListProject.size()));
		
		PlanSqlAdapter mPlanSqlAdapter = new PlanSqlAdapter();
		List<Plan> mListPlan = new ArrayList<Plan>();
		Integer countPlan = 0;
		
		for(Project p:mListProject){
			mListPlan = mPlanSqlAdapter.SelectPlan(mData, p);
			for(Plan pl:mListPlan){
				if(pl.getStatus() != Plan.PlanStatus.COMPLETE){
					countPlan++;
				}
			}			
		}
		
		lbNumberPlans.setText(countPlan.toString());		
	}
	
	public FrameMain(final People loggedUser){
		
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
                PanelProjectNew fmNewProject = new PanelProjectNew(loggedUser);
                getContentPane().add(fmNewProject);
                getContentPane().validate();
                getContentPane().repaint();
            }
        });
		menuEditProject.setText("Edit Project");
		menuEditProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	OpenPanelEdit(loggedUser, null);
            }
        });
		menuListProject.setText("Projects List");
		menuListProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                getContentPane().removeAll();
                PanelProjectList fmListProjects = new PanelProjectList(loggedUser);
                getContentPane().add(fmListProjects);
                getContentPane().validate();
                getContentPane().repaint();
            }
        });
		menuTeamProject.setText("Project Teams");
		menuTeamProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                getContentPane().removeAll();
                PanelProjectTeam fmTeamProjects = new PanelProjectTeam(loggedUser);
                getContentPane().add(fmTeamProjects);
                getContentPane().validate();
                getContentPane().repaint();
            }
        });
		menuProject.setText("Project");
		menuProject.add(menuNewProject);
		menuProject.add(menuEditProject);
		menuProject.add(menuListProject);
		menuProject.add(menuTeamProject);
			
		//Menu People
		menuManagePeople.setText("Manage Employees");
		menuManagePeople.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                getContentPane().removeAll();
                PanelManagePeople fmManagePeople = new PanelManagePeople();
                getContentPane().add(fmManagePeople);
                getContentPane().validate();
                getContentPane().repaint();
            }
        });
		menuPeople.setText("Employee");
		menuPeople.add(menuManagePeople);
		//Menu Plan
		menuPlan.setText("Planning");
		menuNewPlan.setText("New Plan");
		menuNewPlan.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getContentPane().removeAll();
				PanelPlanNew fmPlanNew = new PanelPlanNew(loggedUser);
            	getContentPane().add(fmPlanNew);
            	getContentPane().validate();
            	getContentPane().repaint();				
			}
			
		});		
		menuPlan.add(menuNewPlan);
		
		//Menu File
		menuMain.setText("Home");
		menuMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	getContentPane().removeAll();
            	getContentPane().add(panel);
            	getContentPane().validate();
            	getContentPane().repaint();
            	RefreshIcons(loggedUser);
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
		Menu.add(menuPlan);
		
		this.setJMenuBar(Menu); 
		
		//-----PANEL----------------------------------------------
		lbTitle.setText("<html><font color='#CC0000' face='Ubuntu Condensed'>PC</font><font color='#F87A17' face='Droid Sans Fallback'>Planning</font></html>");
		lbTitle.setFont(new Font("Droid Sans Fallback", Font.PLAIN, 110));
		lbTitle.setForeground(Color.red);	
		
		
		if(loggedUser.getName().length() > 15){
			StringBuilder UserName = new StringBuilder();
			String[] nameParts = loggedUser.getName().split(" ");
			UserName.append(nameParts[0]);
			for(int i=1; i < nameParts.length; i++){				
				if(nameParts[i].equals("da") == false && nameParts[i].equals("de") == false && nameParts[i].equals("do") == false){
					UserName.append(" " + nameParts[i].substring(0,1) + ".");
				}
			}
			lbUserName.setText(UserName.toString());
		}else{
			lbUserName.setText(loggedUser.getName());	
		}
		
		if(loggedUser.getLogin().getPermissionLevel() > 0){				
			menuPeople.setEnabled(false);
		}
		
		lbUserName.setForeground(Color.ORANGE);	
		lbUserName.setFont(new Font("Ubuntu Condensed", Font.PLAIN, 20));
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 30;      //make this component tall
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
	    c.insets = new Insets(110,345,0,0);  
	    panel.add(lbUserName, c);     
	    
	    
	    //--Icons Definitions
	    lbNumberProject.setFont(new Font("Ubuntu Condensed", Font.PLAIN, 20));
	    lbNumberPlans.setFont(new Font("Ubuntu Condensed", Font.PLAIN, 20));
	    
	    iconProject.setPreferredSize(new Dimension(150,50));
	    iconProject.setFocusable(false);
	    iconProject.setHorizontalTextPosition(SwingConstants.CENTER);
	    iconProject.setVerticalTextPosition(SwingConstants.CENTER);
		
		ImageIcon imgIconProject = new ImageIcon(getClass().getClassLoader().getResource("resources/iconProject.png"));
		iconProject.setBorder(BorderFactory.createEmptyBorder());
		iconProject.setContentAreaFilled(false);
		iconProject.setIcon(imgIconProject);
		iconProject.setIconTextGap(10);
		c.insets = new Insets(507,250,0,0);
		panel.add(iconProject, c);
		c.insets = new Insets(500,450,0,0);
		panel.add(lbNumberProject, c);
		
		
		iconPlans.setPreferredSize(new Dimension(150,50));
		iconPlans.setHorizontalTextPosition(SwingConstants.CENTER);
		iconPlans.setVerticalTextPosition(SwingConstants.CENTER);
		
		ImageIcon imgIconPlans = new ImageIcon(getClass().getClassLoader().getResource("resources/iconPlans.png"));
		iconPlans.setBorder(BorderFactory.createEmptyBorder());
		iconPlans.setContentAreaFilled(false);
		iconPlans.setIcon(imgIconPlans);
		iconPlans.setIconTextGap(10);
		c.insets = new Insets(500,450,0,0);
		panel.add(iconPlans, c);
		c.insets = new Insets(500,550,0,0);
		panel.add(lbNumberPlans, c);
				
		//--
		
		this.add(panel);
		RefreshIcons(loggedUser);
	}
}
