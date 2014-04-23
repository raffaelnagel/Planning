import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class FrameMain extends JFrame{

	JLabel lbTitle = new JLabel();
	JPanel panel = new JPanel();
	
	private JMenuBar Menu = new JMenuBar();;  
	private JMenu menuProject = new JMenu();
	private JMenu menuFile = new JMenu();
	private JMenuItem menuNewProject = new JMenuItem();
	private JMenuItem menuListProject = new JMenuItem(); 
	
	private JMenuItem menuClose = new JMenuItem(); 
	private JMenuItem menuMain = new JMenuItem(); 
	
	public FrameMain(){
		
		setSize(600,550);		
		setTitle("Planning System");
		setLocationRelativeTo(null);		
		setResizable(false);
		setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.setVisible(true);		
		
		//MENU
		menuNewProject.setText("New Project");
		menuListProject.setText("Project's List");
		menuListProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                getContentPane().removeAll();
                FrameProjectList fmListProjects = new FrameProjectList();
                getContentPane().add(fmListProjects);
                getContentPane().validate();
                getContentPane().repaint();
            }
        });
		
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
		
		menuProject.setText("Project");
		menuProject.add(menuNewProject);
		menuProject.add(menuListProject);
		
		
		menuFile.setText("File");
		menuFile.add(menuMain);
		menuFile.addSeparator();
		menuFile.add(menuClose);
		
		Menu.add(menuFile); 
		Menu.add(menuProject);		
		this.setJMenuBar(Menu); 
		
		//-----PANEL----------------------------------------------
		lbTitle.setText("Planning");
		lbTitle.setFont(new Font("Serif", Font.PLAIN, 100));
		lbTitle.setForeground(Color.red);	
		
		panel.setLayout(new GridBagLayout());
	    panel.add(lbTitle);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		this.add(panel);
	}
}
