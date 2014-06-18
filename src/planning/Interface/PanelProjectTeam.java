package planning.Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import planning.Planning.DatabaseConnection;
import planning.Planning.MyListCellRenderer;
import planning.Planning.People;
import planning.Planning.PeopleSqlAdapter;
import planning.Planning.Planning;
import planning.Planning.Project;
import planning.Planning.ProjectSqlAdapter;
import planning.Planning.Team;

@SuppressWarnings("serial")
public class PanelProjectTeam extends JPanel{

		private JLabel lbProjectCode = new JLabel(), lbProjectName = new JLabel(), lbProjectCategory = new JLabel(), lbEmployeeName = new JLabel(), lbResponsability = new JLabel(), lbListHeader = new JLabel();
		private JTextField tfProjectName = new JTextField(), tfProjectCategory = new JTextField();
		private JComboBox cbProjectCode, cbEmployeeName, cbResponsability;
		private JButton btnAddToTeam = new JButton(), btnRemoveFromTeam = new JButton();
		
		//Team employee list
		private final DefaultListModel listEmployeeModel = new DefaultListModel();
		private JList listEmployee = new JList(listEmployeeModel);
		private JScrollPane scrollPane = new JScrollPane(listEmployee, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		private void refreshListItems(Project p){
			//add data to list
			DatabaseConnection mData = Planning.OpenConnection();
			
			List<Team> mListTeam = new ArrayList<Team>();
			mListTeam = Team.SelectPeopleTeam(mData, p);
			
			listEmployeeModel.clear();
			List<String> employees = new ArrayList<String>();
			for(Team t:mListTeam){			
				//listPeopleModel.addElement(p.getName());
				employees.add(t.getPeople().getName() + " [" + t.getResponsability() + "] ");
			}
			Collections.sort(employees);
			for(String s:employees){			
				listEmployeeModel.addElement(s);
			}
			
			cbEmployeeName.setSelectedIndex(0);
			cbResponsability.setSelectedIndex(0);		
		}
		
		public PanelProjectTeam(People loggedUser){
			
			this.setVisible(true);
			this.setLayout(new GridBagLayout());
			
			JPanel ProjectPanel = new JPanel(new GridBagLayout());
			JPanel TeamPanel = new JPanel(new GridBagLayout());
			JPanel TeamInfoPanel = new JPanel(new GridBagLayout());
			JPanel TeamListPanel = new JPanel(new GridBagLayout());
			
			lbProjectCode.setText("Project Code: ");
			lbProjectName.setText("Project Name: ");
			lbProjectCategory.setText("  Project Category: ");
			lbEmployeeName.setText("Employee Name: ");
			lbResponsability.setText("Responsability: ");
			tfProjectName.setEditable(false);
			tfProjectCategory.setEditable(false);
			
			//popular comboboxes
			final DatabaseConnection mData = Planning.OpenConnection();
			
			//-- project code
			final ProjectSqlAdapter mProjectSqlAdapter = new ProjectSqlAdapter();
			List<Project> mListProject;
			if(loggedUser.getLogin().getPermissionLevel() == 0){
				mListProject = mProjectSqlAdapter.SelectProject(mData);
			}else{
				mListProject = mProjectSqlAdapter.SelectProject(mData, loggedUser);
			}
			final List<String> projectCodes = new ArrayList<String>();
			projectCodes.add(" ");
			for(Project p:mListProject){
				projectCodes.add(p.getProjectCode());
			}
			
			cbProjectCode = new JComboBox(projectCodes.toArray());
			//define function to populate textfields for name and category
			cbProjectCode.addItemListener(new ItemListener(){

				@Override
				public void itemStateChanged(ItemEvent arg0) {
					int projectCode_index = cbProjectCode.getSelectedIndex();
					String Code = projectCodes.get(projectCode_index);
					DatabaseConnection mData = Planning.OpenConnection();
					List<Project> mListProject = mProjectSqlAdapter.SelectProject(mData, "ProjectCode", Code);
					Project mProject = mListProject.get(0);
					tfProjectName.setText(mProject.getName());
					tfProjectCategory.setText(mProject.getCategory().toString());
					refreshListItems(mProject);
					mData.closeConnection();
				}
				
			});
			
			//-- employee name
			final PeopleSqlAdapter mPeopleSqlAdapter = new PeopleSqlAdapter();
			List<People> mListPeople = new ArrayList<People>();
			mListPeople = mPeopleSqlAdapter.SelectPeople(mData);
						
			List<String> employees = new ArrayList<String>();
			employees.add(" ");
			for(People p:mListPeople){			
				//listPeopleModel.addElement(p.getName());
				employees.add(p.getName());
			}
			Collections.sort(employees);
			
			cbEmployeeName = new JComboBox(employees.toArray());
			mData.closeConnection();
			
			//-- responsability
			String[]  listResponsability = {" ","ASA","Blending","Casing_Flavours","Packaging_Materials","PD","PMM","Product_Technology","Statistics","Trainee"};
			cbResponsability = new JComboBox(listResponsability);
			
			
			//BUTTONS DEFINITIONS
			//-- addToTeam
				btnAddToTeam.setText("Add to Team");
				//button Add
				btnAddToTeam.setPreferredSize(new Dimension(150,32));
				btnAddToTeam.setHorizontalTextPosition(SwingConstants.CENTER);
				btnAddToTeam.setVerticalTextPosition(SwingConstants.CENTER);
				btnAddToTeam.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e){
						//incluir pessoa na tabela de Team com idProject e idPeople
												
						if(cbProjectCode.getSelectedIndex() > 0 && cbEmployeeName.getSelectedIndex() > 0 && cbResponsability.getSelectedIndex() > 0){
							
							int projectCode_index = cbProjectCode.getSelectedIndex();
							String Code = projectCodes.get(projectCode_index);	
							String peopleName = (String)cbEmployeeName.getSelectedItem();
							String resp = (String)cbResponsability.getSelectedItem();
							
							int option = JOptionPane.showConfirmDialog(getRootPane(), "Confirm?");
							
							if(option == JOptionPane.YES_OPTION){
								DatabaseConnection mData = Planning.OpenConnection();
								//get Project								
								List<Project> mListProject = mProjectSqlAdapter.SelectProject(mData, "ProjectCode", Code);
								Project mProject = mListProject.get(0);								
								
								//get People								
								List<People> mListPeople = mPeopleSqlAdapter.SelectPeople(mData, "Name", peopleName);
								People mPeople = mListPeople.get(0);			
								
								Team mTeam = new Team(mPeople, mProject, resp);
								
								Team.InsertPeopleTeam(mData, mTeam);
								
								refreshListItems(mProject);
								mData.closeConnection();
							}
						}
					}
				});
			
			//-- removeFromTeam
				btnRemoveFromTeam.setText("Remove From Team");
				//button Add
				btnRemoveFromTeam.setPreferredSize(new Dimension(150,32));
				btnRemoveFromTeam.setHorizontalTextPosition(SwingConstants.CENTER);
				btnRemoveFromTeam.setVerticalTextPosition(SwingConstants.CENTER);
				btnRemoveFromTeam.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e){
						//remover pessoa da tabela de Team 
						DatabaseConnection mData = Planning.OpenConnection();
						
						if(listEmployee.getSelectedIndex() > -1){							
							
							int projectCode_index = cbProjectCode.getSelectedIndex();
							String Code = projectCodes.get(projectCode_index);	
							String peopleNameAndCode = listEmployee.getSelectedValue().toString();
							String peopleName = peopleNameAndCode.split("\\[")[0];
								peopleName = peopleName.substring(0, peopleName.length() -1);
							
							String peopleCode = peopleNameAndCode.split("\\[")[1];
								peopleCode = peopleCode.substring(0, peopleCode.length() -2);
							
							if(peopleCode.equals("Leader")){
								JOptionPane.showMessageDialog(getRootPane(), "Sorry, you cant remove the project Leader.", "Unable to Remove", JOptionPane.WARNING_MESSAGE);
								return;
							}
							int option = JOptionPane.showConfirmDialog(getRootPane(), "Confirm?");
							
							if(option == JOptionPane.YES_OPTION){
								
								//get Project								
								List<Project> mListProject = mProjectSqlAdapter.SelectProject(mData, "ProjectCode", Code);
								Project mProject = mListProject.get(0);								
								
								//get People								
								List<People> mListPeople = mPeopleSqlAdapter.SelectPeople(mData, "Name", peopleName);
								People mPeople = mListPeople.get(0);			
								
								Team mTeam = new Team(mPeople, mProject, "");
								Team.RemovePeopleTeam(mData, mTeam);
																
								refreshListItems(mProject);
							}
						}
						mData.closeConnection();
					}
				});
			//INTERFACE DEFINITIONS
			//Panel Project Selection
			GridBagConstraints c = new GridBagConstraints();
			c.ipady = 5;
			c.weightx = 0.5;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 1;
			c.gridy = 1;			
			ProjectPanel.add(lbProjectCode,c);		
			c.gridx = 2;	
			ProjectPanel.add(cbProjectCode,c);
			c.gridx = 3;
			ProjectPanel.add(new JLabel("   "),c);
			c.gridx = 4;
			ProjectPanel.add(new JLabel("   "),c);
			c.gridx = 5;
			ProjectPanel.add(new JLabel("   "),c);
			c.gridx = 6;
			ProjectPanel.add(new JLabel("   "),c);
			c.gridx = 1;
			c.gridy = 2;
			ProjectPanel.add(lbProjectName,c);
			c.gridx = 2;
			c.gridwidth = 2;
			ProjectPanel.add(tfProjectName,c);
			c.gridwidth = 1;
			c.gridx = 4;
			ProjectPanel.add(lbProjectCategory, c);
			c.gridx = 5;
			c.gridwidth = 2;
			ProjectPanel.add(tfProjectCategory, c);
			c.gridwidth = 1;
			
			ProjectPanel.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
					"Project Selection",
	                TitledBorder.LEFT,
	                TitledBorder.TOP));
			
			//Panel Team Definition
			//team info
			c = new GridBagConstraints();
			c.ipady = 5;
			c.weightx = 0.5;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 1;
			c.gridy = 1;			
			c.gridwidth = 2;
			TeamInfoPanel.add(lbEmployeeName, c);
			c.gridy = 2;
			TeamInfoPanel.add(cbEmployeeName, c);
			c.gridy = 3;
			TeamInfoPanel.add(lbResponsability, c);
			c.gridy = 4;
			TeamInfoPanel.add(cbResponsability, c);
			c.gridy = 5;
			TeamInfoPanel.add(new JLabel(" "), c);
			c.gridy = 6;
			TeamInfoPanel.add(btnAddToTeam, c);
			c.gridwidth = 1;
			//team list
			//----List Panel----------------------
			listEmployee.setCellRenderer(new MyListCellRenderer());
			listEmployee.setSelectionBackground(Color.darkGray);
			listEmployee.setSelectionForeground(Color.white);
			listEmployee.setBorder(new LineBorder(Color.darkGray, 1));
			listEmployee.setFixedCellHeight(30);
			listEmployee.setFixedCellWidth(100);
			scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		
			c.ipady = 5;
			c.weightx = 0.5;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 0;		
			TeamListPanel.add(lbListHeader, c);
							
			c.gridx = 0;
			c.gridy = 3;
			c.gridwidth = 1;
			c.ipady = 142;
			c.ipadx = 15;
			TeamListPanel.add(scrollPane, c);
			c.gridwidth = 1;
			c.ipady = 5;
			c.gridy = 4;
			TeamListPanel.add(btnRemoveFromTeam, c);
			TeamListPanel.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
					"Employees List",
	                TitledBorder.LEFT,
	                TitledBorder.TOP));
			
			c.anchor = GridBagConstraints.NORTH;
			c.gridx = 1;
			c.gridy = 0;
			c.gridwidth = 1;
			c.ipadx = 15;
			TeamPanel.add(TeamListPanel, c);
			c.gridx = 0;
			c.gridy = 0;
			TeamPanel.add(TeamInfoPanel, c);
			TeamPanel.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
					"Team Definitions",
	                TitledBorder.LEFT,
	                TitledBorder.TOP));
			
			c.anchor = GridBagConstraints.NORTH;
			c.weighty = 1.0;
			this.add(ProjectPanel, c);
			c.anchor = GridBagConstraints.CENTER;
			this.add(TeamPanel, c);
		}
}
















