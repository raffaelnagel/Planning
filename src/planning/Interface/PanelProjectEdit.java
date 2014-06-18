package planning.Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
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

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.SqlDateModel;
import planning.Planning.DatabaseConnection;
import planning.Planning.People;
import planning.Planning.Planning;
import planning.Planning.Project;
import planning.Planning.ProjectSqlAdapter;

@SuppressWarnings("serial")
public class PanelProjectEdit extends JPanel{
	
	private JLabel lbCode = new JLabel(), lbName = new JLabel(), lbCategory = new JLabel(), lbBrand = new JLabel(), lbEndMarket = new JLabel(), lbOpCo = new JLabel(), lbComplexity = new JLabel(), lbStart = new JLabel(), lbFinish = new JLabel();
	private JTextField tfName = new JTextField(30), tfBrand = new JTextField(10), tfEndMarket = new JTextField(11), tfOpCo = new JTextField(11);
	private JComboBox cbProjectCode;
	private JComboBox cbCategory = new JComboBox(Project.ProjectCategory.values()), cbComplexity = new JComboBox(Project.ProjectComplexity.values());
	private SqlDateModel StartModel = new SqlDateModel();
	private SqlDateModel FinishModel = new SqlDateModel();
	private JDatePanelImpl StartDatePanel = new JDatePanelImpl(StartModel);
	private JDatePanelImpl FinishDatePanel = new JDatePanelImpl(FinishModel);
	private final JDatePickerImpl StartDatePicker = new JDatePickerImpl(StartDatePanel);
	private final JDatePickerImpl FinishDatePicker = new JDatePickerImpl(FinishDatePanel);
	
	private void FieldReset(){
		//cbProjectCode.setSelectedIndex(0);
		tfName.setText(null);
		cbCategory.setSelectedIndex(0);
		tfBrand.setText(null);
		tfEndMarket.setText(null);
		tfOpCo.setText(null);
		cbComplexity.setSelectedIndex(0);
		StartModel.setValue(null);
		FinishModel.setValue(null);		
	}
	
	private void LoadProjectToEdition(String projectCode){		
		if(projectCode == null)
			return;
		DatabaseConnection mData = Planning.OpenConnection();
		ProjectSqlAdapter mProjectSqlAdapter = new ProjectSqlAdapter();
		List<Project> mListProject = mProjectSqlAdapter.SelectProject(mData, "ProjectCode", projectCode);
		Project projectToEdit = mListProject.get(0);
		
		if(cbProjectCode.getSelectedItem().equals(projectToEdit.getProjectCode()) == false){
			cbProjectCode.setSelectedItem(projectToEdit.getProjectCode().toString());
		}
		tfName.setText(projectToEdit.getName());
		cbCategory.setSelectedItem(projectToEdit.getCategory());
		tfBrand.setText(projectToEdit.getBrand());
		tfEndMarket.setText(projectToEdit.getEndMarket());
		tfOpCo.setText(projectToEdit.getOpco());
		cbComplexity.setSelectedItem(projectToEdit.getComplexity());
		
		if(projectToEdit.getStart() != null){
			StartModel.setValue(new java.sql.Date(projectToEdit.getStart().getTime()));
		}
		if(projectToEdit.getFinish() != null){
			FinishModel.setValue(new java.sql.Date(projectToEdit.getFinish().getTime()));
		}
		mData.closeConnection();
	}
	
	public PanelProjectEdit(final People loggedUser, String projectToEdit){
		
		this.setVisible(true);
		this.setLayout(new GridBagLayout());
		
		JPanel InputPanel = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton btnEdit = new JButton("Edit");
		cbComplexity.setPreferredSize(new Dimension(100,20));
		cbCategory.setPreferredSize(new Dimension(100,20));
		cbCategory.setBackground(Color.white);		
		cbComplexity.setBackground(Color.white);	
		//
		lbCode.setText("Code: ");
		lbName.setText("Name: ");
		lbCategory.setText(" Category: ");
		cbCategory.setSelectedIndex(0);
		lbBrand.setText("Brand: ");
		lbEndMarket.setText(" End Market: ");
		lbOpCo.setText(" OpCo: ");
		lbComplexity.setText("Complexity: ");
		
		lbStart.setText(" Start Date: ");
		lbFinish.setText(" Finish Date: ");
		
		//-- project code
		final DatabaseConnection mData = Planning.OpenConnection();
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
		mData.closeConnection();
		cbProjectCode = new JComboBox(projectCodes.toArray());		
		cbProjectCode.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				
				int projectCode_index = cbProjectCode.getSelectedIndex();
				String Code = projectCodes.get(projectCode_index);
				if(Code.equals(" ") == true){
					FieldReset();
					return;
				}				
				FieldReset();
				LoadProjectToEdition(Code);
			}			
		});
		
		GridBagConstraints c = new GridBagConstraints();
		c.ipady = 5;
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;		
		InputPanel.add(lbCode,c);
		c.gridx = 1;		
		c.gridwidth = 1;
		cbProjectCode.setPreferredSize(new Dimension(50,18));
		InputPanel.add(cbProjectCode,c);
		
		c.gridx = 2;
		InputPanel.add(new JLabel(" "),c);
		c.gridx = 3;
		InputPanel.add(new JLabel(" "),c);
		c.gridx = 4;
		InputPanel.add(new JLabel(" "),c);
		c.gridx = 5;
		InputPanel.add(new JLabel(" "),c);
		
		c.gridy = 1;
		c.ipady = 15;
		InputPanel.add(new JLabel(""),c);
		c.ipady = 5;
		
		c.gridx = 0;
		c.gridy = 2;			
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
		cbCategory.setPreferredSize(new Dimension(50,18));
		InputPanel.add(cbCategory,c);
		c.ipady = 5;
		c.gridwidth = 1;
		c.gridx = 5;			
		
		c.gridy = 3;
		c.ipady = 15;
		InputPanel.add(new JLabel(""),c);
		c.ipady = 5;
		
		c.gridy = 4;
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
		
		c.gridy = 5;
		c.ipady = 15;
		InputPanel.add(new JLabel(""),c);
		c.ipady = 5;
		
		c.gridy = 6;
		c.gridx = 0;
		InputPanel.add(lbComplexity,c);
		c.gridx = 1;
		cbComplexity.setPreferredSize(new Dimension(50,18));
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
		
		c.gridy = 7;
		c.ipady = 15;
		InputPanel.add(new JLabel(""),c);
		c.ipady = 5;
		
		c.gridy = 8;
		
		//				
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		
		InputPanel.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
				"Edit Project",
                TitledBorder.LEFT,
                TitledBorder.TOP));
					
		
		//button Edit
		btnEdit.setPreferredSize(new Dimension(150,32));
		btnEdit.setVerticalTextPosition(SwingConstants.CENTER);
		btnEdit.setHorizontalTextPosition(SwingConstants.CENTER);
		btnEdit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				DatabaseConnection mData = Planning.OpenConnection();
				ProjectSqlAdapter mProjectSqlAdapter = new ProjectSqlAdapter();				
				
					
				if(tfName.getText().length() > 0){
					int option = JOptionPane.showConfirmDialog(getRootPane(), "Confirm?");
					
					if(option == JOptionPane.YES_OPTION){						
						Project newProject = new Project();
						
						int projectCode_index = cbProjectCode.getSelectedIndex();
						String Code = projectCodes.get(projectCode_index);
						if(Code.equals(" ") == true){
							FieldReset();
							return;
						}
						List<Project> mListProject = mProjectSqlAdapter.SelectProject(mData, "ProjectCode", Code);
						newProject = mListProject.get(0);
						
						newProject.setName(tfName.getText());
						newProject.setCategory(Project.ProjectCategory.valueOf(cbCategory.getSelectedItem().toString()));
						newProject.setBrand(tfBrand.getText());
						newProject.setOpco(tfOpCo.getText());
						newProject.setEndMarket(tfEndMarket.getText());
						newProject.setApproval(false);
						newProject.setComplexity(Project.ProjectComplexity.valueOf(cbComplexity.getSelectedItem().toString()));
						
						Date selectedDate = (Date) StartDatePicker.getModel().getValue();
						if(selectedDate != null){
							Timestamp tStartDate = new Timestamp(selectedDate.getTime());  
							newProject.setStart(tStartDate);
						}else{
							newProject.setStart(null);
						}
						selectedDate = (Date) FinishDatePicker.getModel().getValue();
						if(selectedDate != null){
							Timestamp tFinishDate = new Timestamp(selectedDate.getTime());
							newProject.setFinish(tFinishDate);
						}else{
							newProject.setFinish(null);
						}
						Date timeNow = new Date(Calendar.getInstance().getTimeInMillis());
						Timestamp tDate = new Timestamp(timeNow.getTime());
						newProject.setDate(tDate);
						
						boolean ProjectEdited = mProjectSqlAdapter.UpdateProject(mData, newProject);
						if(ProjectEdited){																					
							JOptionPane.showMessageDialog(getRootPane(), "Project Edited!");
							FieldReset();
						}else{
							JOptionPane.showMessageDialog(getRootPane(), "Error!");
							FieldReset();
						}
					}
				}else{
					JOptionPane.showMessageDialog(getRootPane(), "Please, enter a valid Name");					
				}
				mData.closeConnection();
			}
		});
		
		buttonPanel.add(btnEdit);
		c.gridy = 7;
		c.gridx = 5;
		
		InputPanel.add(buttonPanel,c);
		c.anchor = GridBagConstraints.NORTH;
		c.weighty = 1.0;
		this.add(InputPanel, c);
		LoadProjectToEdition(projectToEdit);
	}
	
}
