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
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.SqlDateModel;
import planning.Planning.DatabaseConnection;
import planning.Planning.People;
import planning.Planning.Plan;
import planning.Planning.PlanSqlAdapter;
import planning.Planning.Project;
import planning.Planning.ProjectSqlAdapter;

@SuppressWarnings("serial")
public class PanelPlanNew extends JPanel{
	
	Object [] columnNames = {"Project","Site","Area","Stage","Action Plan","Demanded Quantity","Unit","Cycle","Notes","Date","Lead Time","Status","Accomplished Date"};
	JTable mTable = new JTable(new DefaultTableModel(columnNames, 0)){
		 @Override
		public boolean getScrollableTracksViewportWidth() {
		   return getPreferredSize().width < getParent().getWidth();
		}
	};
	
	DefaultTableModel mModel = (DefaultTableModel) mTable.getModel();
	
	private JLabel lbSite = new JLabel(), lbArea = new JLabel(), lbStage = new JLabel(), lbActivity = new JLabel(), lbDemandedQtty = new JLabel(), lbUnit = new JLabel(), lbCycle = new JLabel(), lbNotes = new JLabel(), lbDate = new JLabel(), lbLeadTime = new JLabel();
	private JTextField tfSite = new JTextField(25), tfArea = new JTextField(5), tfStage = new JTextField(10), tfActivity = new JTextField(10), tfDemandedQtty = new JTextField(10), tfUnit = new JTextField(10), tfCycle = new JTextField(10), tfNotes = new JTextField(20), tfLeadTime = new JTextField(5);
	private JLabel lbProjectCode = new JLabel(), lbProjectName = new JLabel(), lbProjectCategory = new JLabel();
	private JTextField tfProjectName = new JTextField(), tfProjectCategory = new JTextField();
	
	SqlDateModel DateModel = new SqlDateModel();
	JDatePanelImpl DatePanel = new JDatePanelImpl(DateModel);
  	final JDatePickerImpl DatePicker = new JDatePickerImpl(DatePanel);
  	
  	private JComboBox cbProjectCode;
	
	private void FieldReset(){
		tfSite.setText(null);
		tfArea.setText(null);
		tfStage.setText(null);
		tfActivity.setText(null);
		tfDemandedQtty.setText(null);
		tfUnit.setText(null);
		tfCycle.setText(null);
		tfNotes.setText(null);
		DateModel.setValue(null);
		tfLeadTime.setText(null);
	}
	
	private String EmptyField(){
		if(tfSite.getText().isEmpty())
			return "Site";
		if(tfArea.getText().isEmpty())
			return "Area";
		if(tfStage.getText().isEmpty())
			return "Stage";
		if(tfActivity.getText().isEmpty())
			return "Activity";
		if(tfDemandedQtty.getText().isEmpty())
			return "Demanded Quantity";
		if(tfUnit.getText().isEmpty())
			return "Unit";
		if(tfCycle.getText().isEmpty())
			return "Cycle";
		//if(tfNotes.getText().isEmpty())
			//return "Notes";
		if(DateModel.isSelected())
			return "Date";
		if(tfLeadTime.getText().isEmpty())
			return "Lead Time";
		return null;
	}
	
	private void RefreshPlanList(DatabaseConnection mData, List<String> projectCodes, ProjectSqlAdapter mProjectSqlAdapter){
		int projectCode_index = cbProjectCode.getSelectedIndex();
		String Code = projectCodes.get(projectCode_index);
		List<Project> mListProject = mProjectSqlAdapter.SelectProject(mData, "ProjectCode", Code);
		Project mProject = mListProject.get(0);
		tfProjectName.setText(mProject.getName());
		tfProjectCategory.setText(mProject.getCategory().toString());
		
		//refresh plan list
		PlanSqlAdapter mPlanSqlAdapter = new PlanSqlAdapter();
		List<Plan> mListPlan;
		mListPlan = mPlanSqlAdapter.SelectPlan(mData, mProject);
		

		for(int row = mModel.getRowCount() - 1; row >= 0 ; row--){
			mModel.removeRow(row);
		}
		
		int line = 0, column = 0;
		for(Plan p:mListPlan){
			mModel.addRow(new Object[]{});
			
			column = 0;
			mTable.setValueAt(p.getProject().getProjectCode(), line, column);
			column++;
			mTable.setValueAt(p.getSite(), line, column);
			column++;
			mTable.setValueAt(p.getArea(), line, column);
			column++;
			mTable.setValueAt(p.getStage(), line, column);
			column++;
			mTable.setValueAt(p.getActionPlan(), line, column);
			column++;
			mTable.setValueAt(p.getDemandedQtty(), line, column);
			column++;
			mTable.setValueAt(p.getUnit(), line, column);
			column++;
			mTable.setValueAt(p.getCycle(), line, column);
			column++;
			mTable.setValueAt(p.getNotes(), line, column);
			column++;
			mTable.setValueAt(p.getDate(), line, column);
			column++;
			mTable.setValueAt(p.getLeadTime(), line, column);
			column++;
			mTable.setValueAt(p.getStatus(), line, column);
			column++;
			mTable.setValueAt(p.getAccomplishedDate(), line, column);
			
			line++;
		}
	}

	
	public PanelPlanNew(final People loggedUser){
		
		this.setVisible(true);
		this.setLayout(new GridBagLayout());
		
		JPanel InputPanel = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton btnCreate = new JButton("Create");
		JPanel ProjectPanel = new JPanel(new GridBagLayout());
		JPanel MainPanel = new JPanel(new GridBagLayout());
		JPanel PlanPanel = new JPanel(new GridBagLayout());
		
		JScrollPane scrollPane = new JScrollPane(mTable);
		scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		scrollPane.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder(),
				"List of Plans",
                TitledBorder.LEFT,
                TitledBorder.TOP));		
		//
		lbSite.setText("Site: ");
		lbArea.setText("   Area: ");
		lbStage.setText(" Stage: ");
		lbActivity.setText("Activity: ");
		lbDemandedQtty.setText("            Demanded Quantity: ");
		lbUnit.setText("Unit: ");
		lbCycle.setText(" Cycle: ");
		lbNotes.setText(" Notes: ");
		lbDate.setText("   Date: ");
		lbLeadTime.setText("Lead Time: ");		
		lbProjectCode.setText("Project Code: ");
		lbProjectName.setText("Project Name: ");
		lbProjectCategory.setText("  Project Category: ");
		tfProjectName.setEditable(false);
		tfProjectCategory.setEditable(false);
		
		//popular comboboxes
		String URL = "jdbc:mysql://localhost:3306/Planning";
		String login = "root";
		String pass = "root";

		final DatabaseConnection mData = new DatabaseConnection(URL, login, pass);	
		mData.openConnection();
		
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
				RefreshPlanList(mData, projectCodes, mProjectSqlAdapter);
			}
		});
		
		//button Create
		btnCreate.setPreferredSize(new Dimension(150,32));
		btnCreate.setVerticalTextPosition(SwingConstants.CENTER);
		btnCreate.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCreate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				
				if(cbProjectCode.getSelectedIndex() > 0){
					if(EmptyField() == null){
					
						try{
							
							Integer.parseInt(tfDemandedQtty.getText());
							Integer.parseInt(tfCycle.getText());
							Integer.parseInt(tfLeadTime.getText());
							
							int projectCode_index = cbProjectCode.getSelectedIndex();
							String Code = projectCodes.get(projectCode_index);	
							
							int option = JOptionPane.showConfirmDialog(getRootPane(), "Confirm?");
							
							if(option == JOptionPane.YES_OPTION){
								//get Project								
								List<Project> mListProject = mProjectSqlAdapter.SelectProject(mData, "ProjectCode", Code);
								Project mProject = mListProject.get(0);								
								
								Plan newPlan = new Plan(mProject);
								newPlan.setSite(tfSite.getText());
								newPlan.setArea(tfArea.getText());
								newPlan.setStage(tfStage.getText());
								newPlan.setActionPlan(tfActivity.getText());
								newPlan.setDemandedQtty(Integer.parseInt(tfDemandedQtty.getText()));
								newPlan.setCycle(Integer.parseInt(tfCycle.getText()));
								newPlan.setUnit(tfUnit.getText());
								newPlan.setNotes(tfNotes.getText());
								newPlan.setLeadTime(Integer.parseInt(tfLeadTime.getText()));
								Date selectedDate = (Date) DatePicker.getModel().getValue();
								Timestamp tDate = new Timestamp(selectedDate.getTime());  
								newPlan.setDate(tDate);
								newPlan.setStatus(Plan.PlanStatus.PENDING);
								
								PlanSqlAdapter mPlanSqlAdapter = new PlanSqlAdapter();
								mPlanSqlAdapter.InsertPlan(mData, newPlan);
								
								FieldReset();
								RefreshPlanList(mData, projectCodes, mProjectSqlAdapter);
							}
						}					
						catch(Exception ex){
							JOptionPane.showMessageDialog(getRootPane(), "Invalid Input!");
						}
					}else{
						JOptionPane.showMessageDialog(getRootPane(), "Please, fill the required fields");
					}
				}else{
					JOptionPane.showMessageDialog(getRootPane(), "Please, select a Project Code");
				}				
			}		
		});

		//-- table list
		DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
		centerRender.setHorizontalAlignment(SwingConstants.CENTER);
		
		for(int i=0; i < mTable.getColumnCount(); i++)
			mTable.getColumnModel().getColumn(i).setCellRenderer(centerRender);
				
		mTable.setCellSelectionEnabled(false);
		mTable.setRowSelectionAllowed(true); 
		mTable.setFillsViewportHeight(true);
		mTable.setBackground(Color.lightGray);
		mTable.setSelectionBackground(Color.white);
		mTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		mTable.setAutoCreateRowSorter(true);
		
		//-- INTERFACE --
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
		
		//Panel Plan input
		c = new GridBagConstraints();
		c.ipady = 5;
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;			
		InputPanel.add(lbSite,c);		
		c.gridx = 1;		
		c.gridwidth = 2;
		InputPanel.add(tfSite,c);
		c.gridwidth = 1;		
		c.gridx = 3;
		InputPanel.add(lbArea,c);
		c.gridx = 4;	
		c.gridwidth = 2;
		InputPanel.add(tfArea,c);
		c.gridwidth = 1;
		c.gridx = 6;			
		InputPanel.add(lbStage,c);
		c.gridx = 7;
		InputPanel.add(tfStage,c);
		
		c.gridy = 1;
		c.ipady = 15;
		InputPanel.add(new JLabel(""),c);
		c.ipady = 5;
		
		c.gridy = 2;
		c.gridx = 0;
		InputPanel.add(lbActivity,c);
		c.gridx = 1;
		c.gridwidth = 3;
		InputPanel.add(tfActivity,c);		
		c.gridx = 4;
		c.gridwidth = 1;
		InputPanel.add(lbDemandedQtty,c);
		c.gridx = 5;
		InputPanel.add(tfDemandedQtty,c);
		c.gridx = 6;
		InputPanel.add(lbCycle,c);
		c.gridx = 7;
		InputPanel.add(tfCycle,c);
		
		c.gridy = 3;
		c.ipady = 15;
		InputPanel.add(new JLabel(""),c);
		c.ipady = 5;
		
		c.gridy = 4;
		c.gridx = 0;
		InputPanel.add(lbUnit,c);
		c.gridx = 1;
		InputPanel.add(tfUnit,c);				
		c.gridwidth = 1;
		c.gridx = 3;
		InputPanel.add(lbNotes,c);
		c.gridx = 4;
		c.gridwidth = 4;
		InputPanel.add(tfNotes,c);
		c.gridwidth = 1;
		
		c.gridy = 5;
		c.ipady = 15;
		InputPanel.add(new JLabel(""),c);
		c.ipady = 5;
		
		c.gridy = 6;
		c.gridx = 0;
		InputPanel.add(lbLeadTime,c);
		c.gridx = 1;
		InputPanel.add(tfLeadTime,c);
		c.gridx = 3;
		InputPanel.add(lbDate,c);
		c.gridx = 4;
		InputPanel.add(DatePicker,c);
		
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
				"Create New Plan",
                TitledBorder.LEFT,
                TitledBorder.TOP));
					
				
		scrollPane.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder(),
				"List of Plans",
                TitledBorder.LEFT,
                TitledBorder.TOP));
		buttonPanel.add(btnCreate);
		c.gridy = 6;
		c.gridx = 5;		
		InputPanel.add(buttonPanel,c);
		
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.NORTH;
		c.weighty = 1.0;		
		MainPanel.add(ProjectPanel, c);
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		MainPanel.add(InputPanel, c);		
				
		
		
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 1;
		c.anchor = GridBagConstraints.NORTH;
		c.weighty = 1.0;
		PlanPanel.add(MainPanel, c);
		c.gridy = 1;
		c.anchor = GridBagConstraints.NORTH;
		scrollPane.setPreferredSize(new Dimension(450, 300));
		PlanPanel.add(scrollPane, c);
		
		
		c.anchor = GridBagConstraints.NORTH;
		this.add(PlanPanel, c);
	}
}
