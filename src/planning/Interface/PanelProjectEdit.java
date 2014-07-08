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
import planning.Data.AuxiliarData;
import planning.Data.Brand;
import planning.Data.People;
import planning.Data.Planning;
import planning.Data.Project;
import planning.DataAdapter.AuxiliarDataSqlAdapter;
import planning.DataAdapter.BrandSqlAdapter;
import planning.DataAdapter.DatabaseConnection;
import planning.DataAdapter.ProjectSqlAdapter;

@SuppressWarnings("serial")
public class PanelProjectEdit extends JPanel{
	
	private JLabel lbCode = new JLabel(), lbName = new JLabel(), lbCategory = new JLabel(), lbBrand = new JLabel(), lbEndMarket = new JLabel(), lbOpCo = new JLabel(), lbComplexity = new JLabel(), lbStart = new JLabel(), lbFinish = new JLabel();
	private JLabel lbPitCode = new JLabel(), lbMainProject = new JLabel();
	private JTextField tfName = new JTextField(30);
	private JComboBox cbProjectCode;
	private JComboBox cbCategory, cbBrand, cbEndMarket , cbOpCo, cbPitCode, cbMainProject, cbComplexity = new JComboBox(Project.ProjectComplexity.values());
	private SqlDateModel StartModel = new SqlDateModel();
	private SqlDateModel FinishModel = new SqlDateModel();
	private JDatePanelImpl StartDatePanel = new JDatePanelImpl(StartModel);
	private JDatePanelImpl FinishDatePanel = new JDatePanelImpl(FinishModel);
	private final JDatePickerImpl StartDatePicker = new JDatePickerImpl(StartDatePanel);
	private final JDatePickerImpl FinishDatePicker = new JDatePickerImpl(FinishDatePanel);
	private List<AuxiliarData> mListCategory = new ArrayList<AuxiliarData>();
	private List<AuxiliarData> mListEndMarket = new ArrayList<AuxiliarData>();
	private List<AuxiliarData> mListOpCo = new ArrayList<AuxiliarData>();
	private List<AuxiliarData> mListPitCode = new ArrayList<AuxiliarData>();
	private List<AuxiliarData> mListMainProject = new ArrayList<AuxiliarData>();
	private List<Brand> mListBrand = new ArrayList<Brand>();
	private List<Project> mListProject = new ArrayList<Project>();
	
	private void fieldReset(){
		tfName.setText(null);
		cbPitCode.setSelectedIndex(0);
		cbMainProject.setSelectedIndex(0);
		cbCategory.setSelectedIndex(0);
		cbBrand.setSelectedIndex(0);
		cbEndMarket.setSelectedIndex(0);
		cbOpCo.setSelectedIndex(0);		
		cbComplexity.setSelectedIndex(0);
		StartModel.setValue(null);
		FinishModel.setValue(null);		
	}
	
	private void LoadProjectToEdition(String projectCode){		
		if(projectCode == null)
			return;
		DatabaseConnection mData = Planning.OpenConnection();		
		List<Project> mListProject = ProjectSqlAdapter.selectProject(mData, "ProjectCode", projectCode);
		Project projectToEdit = mListProject.get(0);
		
		if(cbProjectCode.getSelectedItem().equals(projectToEdit.getProjectCode()) == false){
			cbProjectCode.setSelectedItem(projectToEdit.getProjectCode().toString());
		}
		tfName.setText(projectToEdit.getName());
		cbCategory.setSelectedItem(projectToEdit.getCategory().getName());
		cbBrand.setSelectedItem(projectToEdit.getBrand().getName());
		cbEndMarket.setSelectedItem(projectToEdit.getEndMarket().getName());
		cbOpCo.setSelectedItem(projectToEdit.getOpco().getName());
		cbComplexity.setSelectedItem(projectToEdit.getComplexity());
		cbPitCode.setSelectedItem(projectToEdit.getPitCode().getName());
		cbMainProject.setSelectedItem(projectToEdit.getMainProject().getName());
		
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
		//
		lbMainProject.setText(" Main Project: ");
		lbPitCode.setText("Pit Code: ");
		lbCode.setText("Code: ");
		lbName.setText("Name: ");
		lbCategory.setText(" Category: ");
		lbBrand.setText("Brand: ");
		lbEndMarket.setText(" End Market: ");
		lbOpCo.setText(" OpCo: ");
		lbComplexity.setText("Complexity: ");
		
		lbStart.setText(" Start Date: ");
		lbFinish.setText(" Finish Date: ");
		
		//Populate ComboBoxes		
		//--Category
		DatabaseConnection mData = Planning.OpenConnection();
		mListCategory = AuxiliarDataSqlAdapter.selectAuxiliarData(mData, AuxiliarData.AuxiliarDataTypes.ProjectCategory);
		List<String> mListCategoryNames = new ArrayList<String>();
		for(AuxiliarData c:mListCategory){
			mListCategoryNames.add(c.getName());
		}
		mListCategoryNames.add(0, "");
		cbCategory = new JComboBox(mListCategoryNames.toArray());
		//--EndMarket		
		mListEndMarket = AuxiliarDataSqlAdapter.selectAuxiliarData(mData, AuxiliarData.AuxiliarDataTypes.EndMarket);
		List<String> mListEndMarketNames = new ArrayList<String>();
		for(AuxiliarData e:mListEndMarket){
			mListEndMarketNames.add(e.getName());
		}
		mListEndMarketNames.add(0, "");
		cbEndMarket = new JComboBox(mListEndMarketNames.toArray());
		//--OpCo
		mListOpCo = AuxiliarDataSqlAdapter.selectAuxiliarData(mData, AuxiliarData.AuxiliarDataTypes.OpCo);
		List<String> mListOpCoNames = new ArrayList<String>();
		for(AuxiliarData o:mListOpCo){
			mListOpCoNames.add(o.getName());
		}
		mListOpCoNames.add(0, "");
		cbOpCo = new JComboBox(mListOpCoNames.toArray());
		//--MainProject
		mListMainProject = AuxiliarDataSqlAdapter.selectAuxiliarData(mData, AuxiliarData.AuxiliarDataTypes.MainProject);
		List<String> mListMainProjectNames = new ArrayList<String>();
		for(AuxiliarData mp:mListMainProject){
			mListMainProjectNames.add(mp.getName());
		}
		mListMainProjectNames.add(0, "");
		cbMainProject = new JComboBox(mListMainProjectNames.toArray());
		//--PitCode
		mListPitCode = AuxiliarDataSqlAdapter.selectAuxiliarData(mData, AuxiliarData.AuxiliarDataTypes.PitCode);
		List<String> mListPitCodeNames = new ArrayList<String>();
		for(AuxiliarData p:mListPitCode){
			mListPitCodeNames.add(p.getName());
		}
		mListPitCodeNames.add(0, "");
		cbPitCode = new JComboBox(mListPitCodeNames.toArray());
		//--Brand		
		mListBrand = BrandSqlAdapter.selectBrand(mData);
		List<String> mListBrandNames = new ArrayList<String>();
		for(Brand b:mListBrand){
			mListBrandNames.add(b.getName());
		}
		mListBrandNames.add(0, "");
		cbBrand = new JComboBox(mListBrandNames.toArray());
		//-- project code
		if(loggedUser.getLogin().getPermissionLevel() == 0){
			mListProject = ProjectSqlAdapter.selectProject(mData);
		}else{
			mListProject = ProjectSqlAdapter.selectProject(mData, loggedUser);
		}
		final List<String> projectCodes = new ArrayList<String>();
		projectCodes.add(" ");
		for(Project p:mListProject){
			projectCodes.add(p.getProjectCode());
		}
		cbProjectCode = new JComboBox(projectCodes.toArray());		
		cbProjectCode.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				
				int projectCode_index = cbProjectCode.getSelectedIndex();
				String Code = projectCodes.get(projectCode_index);
				if(Code.equals(" ") == true){
					fieldReset();
					return;
				}				
				fieldReset();
				LoadProjectToEdition(Code);
			}			
		});
		mData.closeConnection();
				
		//INTERFACE		
		cbComplexity.setPreferredSize(new Dimension(100,20));
		cbCategory.setPreferredSize(new Dimension(100,20));
		cbEndMarket.setPreferredSize(new Dimension(100,20));
		cbOpCo.setPreferredSize(new Dimension(100,20));
		cbBrand.setPreferredSize(new Dimension(100,20));
		cbPitCode.setPreferredSize(new Dimension(100,20));
		cbMainProject.setPreferredSize(new Dimension(100,20));
		cbCategory.setBackground(Color.white);		
		cbComplexity.setBackground(Color.white);
		cbEndMarket.setBackground(Color.white);
		cbOpCo.setBackground(Color.white);
		cbBrand.setBackground(Color.white);
		cbPitCode.setBackground(Color.white);
		cbMainProject.setBackground(Color.white);		
		
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
		InputPanel.add(lbPitCode,c);		
		c.gridx = 1;		
		c.gridwidth = 2;
		InputPanel.add(cbPitCode,c);
		c.gridwidth = 1;		
		c.gridx = 3;
		InputPanel.add(lbMainProject,c);
		c.gridx = 4;
		c.gridwidth = 2;
		c.ipady = 3;
		InputPanel.add(cbMainProject,c);
		c.ipady = 5;
		c.gridwidth = 1;
		c.gridx = 5;
		
		c.gridy = 3;
		c.ipady = 15;
		InputPanel.add(new JLabel(""),c);
		c.ipady = 5;
		
		c.gridx = 0;
		c.gridy = 4;			
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
		
		c.gridy = 5;
		c.ipady = 15;
		InputPanel.add(new JLabel(""),c);
		c.ipady = 5;
		
		c.gridy = 6;
		c.gridx = 0;
		InputPanel.add(lbBrand,c);
		c.gridx = 1;
		InputPanel.add(cbBrand,c);
		c.gridx = 2;
		InputPanel.add(lbOpCo,c);
		c.gridx = 3;
		InputPanel.add(cbOpCo,c);
		c.gridx = 4;
		InputPanel.add(lbEndMarket,c);
		c.gridx = 5;
		c.gridwidth = 1;
		InputPanel.add(cbEndMarket,c);
		c.gridwidth = 1;
		
		c.gridy = 7;
		c.ipady = 15;
		InputPanel.add(new JLabel(""),c);
		c.ipady = 5;
		
		c.gridy = 8;
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
		
		c.gridy = 9;
		c.ipady = 15;
		InputPanel.add(new JLabel(""),c);
		c.ipady = 5;
		
		c.gridy = 10;
		
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
				
				if(cbProjectCode.getSelectedIndex() > 0){	
					if(tfName.getText().length() > 0){
						int option = JOptionPane.showConfirmDialog(getRootPane(), "Confirm?");
						
						if(option == JOptionPane.YES_OPTION){						
							Project newProject = new Project();
							
							int projectCode_index = cbProjectCode.getSelectedIndex();
							String Code = projectCodes.get(projectCode_index);
							if(Code.equals(" ") == true){
								fieldReset();
								return;
							}
							List<Project> mListProject = ProjectSqlAdapter.selectProject(mData, "ProjectCode", Code);
							newProject = mListProject.get(0);
							
							newProject.setName(tfName.getText());
							int index;
							
							index = cbCategory.getSelectedIndex();						
							newProject.setCategory(mListCategory.get(index-1));
							
							index = cbBrand.getSelectedIndex();
							newProject.setBrand(mListBrand.get(index-1));
							
							index = cbOpCo.getSelectedIndex();
							newProject.setOpco(mListOpCo.get(index-1));
							
							index = cbEndMarket.getSelectedIndex();
							newProject.setEndMarket(mListEndMarket.get(index-1));
							
							index = cbMainProject.getSelectedIndex();
							newProject.setMainProject(mListMainProject.get(index-1));
							
							index = cbPitCode.getSelectedIndex();
							newProject.setPitCode(mListPitCode.get(index-1));
							
							newProject.setApproval(false);
							newProject.setComplexity(Project.ProjectComplexity.valueOf(cbComplexity.getSelectedItem().toString().toUpperCase()));
							
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
							
							boolean ProjectEdited = ProjectSqlAdapter.updateProject(mData, newProject);
							if(ProjectEdited){																					
								JOptionPane.showMessageDialog(getRootPane(), "Project Edited!");							
							}else{
								JOptionPane.showMessageDialog(getRootPane(), "Error!");
							}
							fieldReset();
						}
					}else{
						JOptionPane.showMessageDialog(getRootPane(), "Please, enter a valid Name");					
					}
					mData.closeConnection();
				}
			}
		});
		
		buttonPanel.add(btnEdit);
		c.gridy = 10;
		c.gridx = 5;
		
		InputPanel.add(buttonPanel,c);
		c.anchor = GridBagConstraints.NORTH;
		c.weighty = 1.0;
		this.add(InputPanel, c);
		LoadProjectToEdition(projectToEdit);
	}
	
}
