package planning.Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import planning.Planning.DatabaseConnection;
import planning.Planning.People;
import planning.Planning.Project;
import planning.Planning.ProjectSqlAdapter;
import planning.Planning.Team;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.SqlDateModel;


@SuppressWarnings("serial")
public class PanelProjectNew extends JPanel{
	
	JLabel lbName = new JLabel(), lbCategory = new JLabel(), lbBrand = new JLabel(), lbEndMarket = new JLabel(), lbOpCo = new JLabel(), lbComplexity = new JLabel(), lbStart = new JLabel(), lbFinish = new JLabel();
	JTextField tfName = new JTextField(30), tfBrand = new JTextField(10), tfEndMarket = new JTextField(11), tfOpCo = new JTextField(11);
	JComboBox cbCategory = new JComboBox(Project.ProjectCategory.values()), cbComplexity = new JComboBox(new String[] {"","Cap1","Cap2","Cap3"});
	SqlDateModel StartModel = new SqlDateModel();
	SqlDateModel FinishModel = new SqlDateModel();
  	JDatePanelImpl StartDatePanel = new JDatePanelImpl(StartModel);
  	JDatePanelImpl FinishDatePanel = new JDatePanelImpl(FinishModel);
	final JDatePickerImpl StartDatePicker = new JDatePickerImpl(StartDatePanel);
	final JDatePickerImpl FinishDatePicker = new JDatePickerImpl(FinishDatePanel);
	
	private void FieldReset(){
		tfName.setText(null);
		cbCategory.setSelectedIndex(0);
		tfBrand.setText(null);
		tfEndMarket.setText(null);
		tfOpCo.setText(null);
		cbComplexity.setSelectedIndex(0);
		StartModel.setValue(null);
		FinishModel.setValue(null);		
	}
	
	public PanelProjectNew(final People loggedUser){
		
		this.setVisible(true);
		this.setLayout(new GridBagLayout());
		
		JPanel InputPanel = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton btnCreate = new JButton("Create");
		cbComplexity.setPreferredSize(new Dimension(100,20));
		cbCategory.setPreferredSize(new Dimension(100,20));
		cbCategory.setBackground(Color.white);		
		cbComplexity.setBackground(Color.white);		
		//
		lbName.setText("Name: ");
		lbCategory.setText(" Category: ");
		cbCategory.insertItemAt("", 0);
		cbCategory.setSelectedIndex(0);
		lbBrand.setText("Brand: ");
		lbEndMarket.setText(" End Market: ");
		lbOpCo.setText(" OpCo: ");
		lbComplexity.setText("Complexity: ");
		
		lbStart.setText(" Start Date: ");
		lbFinish.setText(" Finish Date: ");
		
		
		GridBagConstraints c = new GridBagConstraints();
		c.ipady = 5;
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;			
		InputPanel.add(lbName,c);		
		c.gridx = 1;		
		c.gridwidth = 2;
		InputPanel.add(tfName,c);
		c.gridwidth = 1;		
		c.gridx = 3;
		InputPanel.add(lbCategory,c);
		c.gridx = 4;
		c.gridwidth = 2;
		c.ipady = 3;
		InputPanel.add(cbCategory,c);
		c.ipady = 5;
		c.gridwidth = 1;
		c.gridx = 5;			
		
		c.gridy = 1;
		c.ipady = 15;
		InputPanel.add(new JLabel(""),c);
		c.ipady = 5;
		
		c.gridy = 2;
		c.gridx = 0;
		InputPanel.add(lbBrand,c);
		c.gridx = 1;
		InputPanel.add(tfBrand,c);
		c.gridx = 2;
		InputPanel.add(lbOpCo,c);
		c.gridx = 3;
		InputPanel.add(tfOpCo,c);
		c.gridx = 4;
		InputPanel.add(lbEndMarket,c);
		c.gridx = 5;
		c.gridwidth = 1;
		InputPanel.add(tfEndMarket,c);
		c.gridwidth = 1;
		
		c.gridy = 3;
		c.ipady = 15;
		InputPanel.add(new JLabel(""),c);
		c.ipady = 5;
		
		c.gridy = 4;
		c.gridx = 0;
		InputPanel.add(lbComplexity,c);
		c.gridx = 1;
		InputPanel.add(cbComplexity,c);
		
		c.gridx = 2;
		InputPanel.add(lbStart,c);
		c.gridx = 3;
		c.gridwidth = 1;
		InputPanel.add(StartDatePicker,c);
		c.gridwidth = 1;
		c.gridx = 4;
		InputPanel.add(lbFinish,c);
		c.gridx = 5;
		InputPanel.add(FinishDatePicker,c);
		
		c.gridy = 5;
		c.ipady = 15;
		InputPanel.add(new JLabel(""),c);
		c.ipady = 5;
		
		c.gridy = 6;
		
		//
				
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		
		InputPanel.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
				"Create New Project",
                TitledBorder.LEFT,
                TitledBorder.TOP));
					
		
		//button Create
		btnCreate.setPreferredSize(new Dimension(150,32));
		btnCreate.setVerticalTextPosition(SwingConstants.CENTER);
		btnCreate.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCreate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				String URL = "jdbc:mysql://localhost:3306/Planning";
				String login = "root";
				String pass = "root";

				DatabaseConnection mData = new DatabaseConnection(URL, login, pass);	
				mData.openConnection();
				ProjectSqlAdapter mProjectSqlAdapter = new ProjectSqlAdapter();				
				
					
				if(tfName.getText().length() > 0){
					int option = JOptionPane.showConfirmDialog(getRootPane(), "Confirm?");
					
					if(option == JOptionPane.YES_OPTION){						
						Project newProject = new Project();
						
						newProject.setName(tfName.getText());
						newProject.setCategory(Project.ProjectCategory.valueOf(cbCategory.getSelectedItem().toString()));
						newProject.setBrand(tfBrand.getText());
						newProject.setOpco(tfOpCo.getText());
						newProject.setEndMarket(tfEndMarket.getText());
						newProject.setApproval(false);
						newProject.setComplexity(cbComplexity.getSelectedItem().toString());
						
						Date selectedDate = (Date) StartDatePicker.getModel().getValue();
						Timestamp tDate = new Timestamp(selectedDate.getTime());  
						newProject.setStart(tDate);
						selectedDate = (Date) FinishDatePicker.getModel().getValue();
						tDate = new Timestamp(selectedDate.getTime());
						newProject.setFinish(tDate);
									
						Date timeNow = new Date(Calendar.getInstance().getTimeInMillis());
						tDate = new Timestamp(timeNow.getTime());
						newProject.setDate(tDate);
						
						boolean ProjectCreated = mProjectSqlAdapter.InsertProject(mData, newProject);
						if(ProjectCreated){
							
							//add Project Leader to the Team of the Project
							List<Project> mListProjectTeam = mProjectSqlAdapter.SelectProject(mData);
							Project mProjectTeam = new Project();
							
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							java.util.Date pDate = null, newDate = null;
							for(Project p:mListProjectTeam){
								try {
									pDate = df.parse(p.getDate().toString());
									newDate = df.parse(newProject.getDate().toString());
								} catch (ParseException e1) {									
									e1.printStackTrace();
								}
								
								if(p.getName().equals(newProject.getName()) == true && pDate.equals(newDate) == true){
									mProjectTeam = p;
								}
							}
														
							Team newTeam = new Team(loggedUser, mProjectTeam, "Leader");
							Team.InsertPeopleTeam(mData, newTeam);
							// --
							
							JOptionPane.showMessageDialog(getRootPane(), "Project Created!");
							FieldReset();
						}else{
							JOptionPane.showMessageDialog(getRootPane(), "Error!");
							FieldReset();
						}
					}
				}else{
					JOptionPane.showMessageDialog(getRootPane(), "Please, enter a valid Name");					
				}
			}
		});
		
		buttonPanel.add(btnCreate);
		c.gridy = 7;
		c.gridx = 5;
		
		InputPanel.add(buttonPanel,c);
		c.anchor = GridBagConstraints.NORTH;
		c.weighty = 1.0;
		this.add(InputPanel, c);
	}
	
}
