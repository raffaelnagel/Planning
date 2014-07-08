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
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
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
import planning.Data.Activity;
import planning.Data.People;
import planning.Data.Planning;
import planning.Data.Project;
import planning.Data.Schedule;
import planning.DataAdapter.ActivitySqlAdapter;
import planning.DataAdapter.DatabaseConnection;
import planning.DataAdapter.ProjectSqlAdapter;
import planning.DataAdapter.ScheduleSqlAdapter;

@SuppressWarnings("serial")
public class PanelScheduleNew extends JPanel{
	
	Object [] columnNames = {"Project","idSchedule","Site","Area","Stage","Unit","Action Plan","Demanded Quantity","Cycle","Notes","Date","Lead Time","Status","Accomplished Date"};
	JTable mTable = new JTable(new DefaultTableModel(columnNames, 0)){
		 @Override
		public boolean getScrollableTracksViewportWidth() {
		   return getPreferredSize().width < getParent().getWidth();
		}
	};
	
	DefaultTableModel mModel = (DefaultTableModel) mTable.getModel();
	
	private JLabel lbSite = new JLabel(), lbArea = new JLabel(), lbStage = new JLabel(), lbActivity = new JLabel(), lbDemandedQtty = new JLabel(), lbUnit = new JLabel(), lbCycle = new JLabel(), lbNotes = new JLabel(), lbDate = new JLabel(), lbLeadTime = new JLabel();
	private JTextField tfDemandedQtty = new JTextField(15), tfCycle = new JTextField(15), tfNotes = new JTextField(20), tfLeadTime = new JTextField(15);
	private JComboBox cbSite, cbArea = new JComboBox(), cbStage = new JComboBox(), cbUnit = new JComboBox(), cbAction = new JComboBox();
	private JLabel lbProjectCode = new JLabel(), lbProjectName = new JLabel(), lbProjectCategory = new JLabel();
	private JTextField tfProjectName = new JTextField(20), tfProjectCategory = new JTextField();
	private JButton btnDelete = new JButton("");
	private JButton btnEdit = new JButton("");
	private JButton btnSave = new JButton("Save");
	private JButton btnNew = new JButton("");
	
	private List<Activity> mListActivity = new ArrayList<Activity>();
	private List<String> mListSite = new ArrayList<String>();
	private List<String> mListArea = new ArrayList<String>();
	private List<String> mListStage = new ArrayList<String>();
	private List<String> mListAction = new ArrayList<String>();
	private List<String> mListUnit = new ArrayList<String>();
	
	private enum Operation {CREATE, EDIT};
	private Operation operation = null;
	private String oldScheduleId;
	
	SqlDateModel DateModel = new SqlDateModel();
	JDatePanelImpl DatePanel = new JDatePanelImpl(DateModel);
  	final JDatePickerImpl DatePicker = new JDatePickerImpl(DatePanel);
  	
  	private JComboBox cbProjectCode;
	
	private void fieldReset(){
		cbSite.setSelectedIndex(0);
		tfDemandedQtty.setText(null);
		tfCycle.setText(null);
		tfNotes.setText(null);
		DateModel.setValue(null);
		tfLeadTime.setText(null);
	}
	
	private void setEnableFields(boolean value){
		cbSite.setEnabled(value);
		cbArea.setEnabled(value);
		cbStage.setEnabled(value);
		cbUnit.setEnabled(value);
		cbAction.setEnabled(value);
		tfDemandedQtty.setEnabled(value);
		tfCycle.setEnabled(value);
		tfNotes.setEnabled(value);
		DatePicker.setEnabled(value);
		tfLeadTime.setEnabled(value);
		btnSave.setEnabled(value);
	}
	
	private boolean isFieldEmpty(){
		if(cbSite.getSelectedIndex() <= 0) return true;
		if(cbArea.getSelectedIndex() <= 0) return true;
		if(cbStage.getSelectedIndex() <= 0)return true;
		if(tfDemandedQtty.getText() == null) return true;
		if(tfCycle.getText() == null) return true;		
		//if(DateModel.isSelected()) return true;
		if(tfLeadTime.getText() == null) return true;
		
		return false;
	}
	
	private void refreshScheduleList(List<String> projectCodes){
		if(cbProjectCode.getSelectedIndex() > 0){
			DatabaseConnection mData = Planning.OpenConnection();	
			
			int projectCode_index = cbProjectCode.getSelectedIndex();
			String Code = projectCodes.get(projectCode_index);
			List<Project> mListProject = ProjectSqlAdapter.selectProject(mData, "ProjectCode", Code);
			Project mProject = mListProject.get(0);
			tfProjectName.setText(mProject.getName());
			tfProjectCategory.setText(mProject.getCategory().getName().toString());
			
			//refresh plan list
			List<Schedule> mListSchedule;
			mListSchedule = ScheduleSqlAdapter.selectSchedule(mData, mProject);		
	
			for(int row = mModel.getRowCount() - 1; row >= 0 ; row--){
				mModel.removeRow(row);
			}
			
			int line = 0, column = 0;
			for(Schedule s:mListSchedule){
				mModel.addRow(new Object[]{});
				
				column = 0;
				mTable.setValueAt(s.getProject().getProjectCode(), line, column);
				column++;
				mTable.setValueAt(s.getId(), line, column);
				column++;
				mTable.setValueAt(s.getActivity().getSite(), line, column);
				column++;
				mTable.setValueAt(s.getActivity().getArea(), line, column);
				column++;
				mTable.setValueAt(s.getActivity().getStage(), line, column);
				column++;
				mTable.setValueAt(s.getActivity().getUnit(), line, column);
				column++;
				mTable.setValueAt(s.getActivity().getAction(), line, column);
				column++;
				mTable.setValueAt(s.getDemandedQtty(), line, column);
				column++;			
				mTable.setValueAt(s.getCycle(), line, column);
				column++;
				mTable.setValueAt(s.getNotes(), line, column);
				column++;
				mTable.setValueAt(s.getDate(), line, column);
				column++;
				mTable.setValueAt(s.getActivity().getLeadtime(), line, column);
				column++;
				mTable.setValueAt(s.getStatus(), line, column);
				column++;
				mTable.setValueAt(s.getAccomplishedDate(), line, column);
				
				line++;
			}		
			mData.closeConnection();
		}
	}

	
	public PanelScheduleNew(final People loggedUser){
		
		this.setVisible(true);
		this.setLayout(new GridBagLayout());
		
		JPanel InputPanel = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JPanel bottomPanel = new JPanel(new FlowLayout());		
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
		lbArea.setText("          Area: ");
		lbStage.setText("     Stage: ");
		lbActivity.setText("      Activity: ");
		lbDemandedQtty.setText(" Quantity: ");
		lbUnit.setText("Unit: ");
		lbCycle.setText("Cycle: ");
		lbNotes.setText("Notes: ");
		lbDate.setText("        Date: ");
		lbLeadTime.setText(" Lead Time: ");		
		lbProjectCode.setText("Project Code: ");
		lbProjectName.setText("Project Name: ");
		lbProjectCategory.setText("  Project Category: ");
		tfProjectName.setEditable(false);
		tfProjectCategory.setEditable(false);
		
		//Populate ComboBoxes		
		DatabaseConnection mData = Planning.OpenConnection();
		mListActivity = ActivitySqlAdapter.selectActivity(mData);
		for(Activity a:mListActivity){
			if(!mListSite.contains(a.getSite())){
				mListSite.add(a.getSite());
			}
		}		
		//--Site
		java.util.Collections.sort(mListSite);
		mListSite.add(0, "");
		cbSite = new JComboBox(mListSite.toArray());
		cbSite.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				DatabaseConnection mData = Planning.OpenConnection();
				Activity mActivity = new Activity();
				mActivity.setSite(cbSite.getSelectedItem().toString());
				mListActivity = ActivitySqlAdapter.selectActivity(mData, mActivity);
				mListArea.clear();
				for(Activity a:mListActivity){
					if(!mListArea.contains(a.getArea())){
						mListArea.add(a.getArea());
					}
				}
				java.util.Collections.sort(mListArea);
				mListArea.add(0, "");
				DefaultComboBoxModel mModel = new DefaultComboBoxModel(mListArea.toArray());
				cbArea.setModel(mModel);
				DefaultComboBoxModel mNullModel = new DefaultComboBoxModel();
				cbStage.setModel(mNullModel);
				cbUnit.setModel(mNullModel);
				cbAction.setModel(mNullModel);
				mData.closeConnection();
			}			
		});
		//-- Area
		cbArea.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				DatabaseConnection mData = Planning.OpenConnection();
				Activity mActivity = new Activity();
				mActivity.setSite(cbSite.getSelectedItem().toString());
				mActivity.setArea(cbArea.getSelectedItem().toString());
				mListActivity = ActivitySqlAdapter.selectActivity(mData, mActivity);
				mListStage.clear();
				for(Activity a:mListActivity){
					if(!mListStage.contains(a.getStage())){
						mListStage.add(a.getStage());
					}
				}
				java.util.Collections.sort(mListStage);
				mListStage.add(0, "");
				DefaultComboBoxModel mModel = new DefaultComboBoxModel(mListStage.toArray());
				cbStage.setModel(mModel);
				DefaultComboBoxModel mNullModel = new DefaultComboBoxModel();
				cbUnit.setModel(mNullModel);
				cbAction.setModel(mNullModel);
				mData.closeConnection();
			}
			
		});
		//-- Stage
		cbStage.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				DatabaseConnection mData = Planning.OpenConnection();
				Activity mActivity = new Activity();
				mActivity.setSite(cbSite.getSelectedItem().toString());
				mActivity.setArea(cbArea.getSelectedItem().toString());
				mActivity.setStage(cbStage.getSelectedItem().toString());
				mActivity.setUnit("");
				mListActivity = ActivitySqlAdapter.selectActivity(mData, mActivity);
				if(mListActivity.size() == 0){
					mActivity.setUnit(null);
					mListActivity = ActivitySqlAdapter.selectActivity(mData, mActivity);
				}
				mListUnit.clear();
				mListAction.clear();
				for(Activity a:mListActivity){
					if(!mListAction.contains(a.getAction())){
						mListAction.add(a.getAction());
					}
				}
				mActivity.setUnit(null);
				mListActivity = ActivitySqlAdapter.selectActivity(mData, mActivity);
				for(Activity a:mListActivity){
					if(!mListUnit.contains(a.getUnit())){
						mListUnit.add(a.getUnit());
					}
				}
				java.util.Collections.sort(mListUnit);	
				java.util.Collections.sort(mListAction);		
				//mListUnit.add(0, "");
				DefaultComboBoxModel mModel = new DefaultComboBoxModel(mListUnit.toArray());
				cbUnit.setModel(mModel);
				mModel = new DefaultComboBoxModel(mListAction.toArray());
				cbAction.setModel(mModel);
				mData.closeConnection();
			}
			
		});
		//--Unit
		cbUnit.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				DatabaseConnection mData = Planning.OpenConnection();
				Activity mActivity = new Activity();
				mActivity.setSite(cbSite.getSelectedItem().toString());
				mActivity.setArea(cbArea.getSelectedItem().toString());
				mActivity.setStage(cbStage.getSelectedItem().toString());
				mActivity.setUnit(cbUnit.getSelectedItem().toString());				
				mListActivity = ActivitySqlAdapter.selectActivity(mData, mActivity);
				mListAction.clear();
				for(Activity a:mListActivity){
					if(!mListAction.contains(a.getAction())){
						mListAction.add(a.getAction());
					}
				}
				java.util.Collections.sort(mListAction);				
				DefaultComboBoxModel mModel = new DefaultComboBoxModel(mListAction.toArray());
				cbAction.setModel(mModel);
				mData.closeConnection();				
			}
			
		});
		//-- Project Code		
		List<Project> mListProject;
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
		//define function to populate textfields for name and category
		cbProjectCode.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				refreshScheduleList(projectCodes);
			}
		});
		mData.closeConnection();
		
		//button save
		btnSave.setPreferredSize(new Dimension(150,32));
		btnSave.setVerticalTextPosition(SwingConstants.CENTER);
		btnSave.setHorizontalTextPosition(SwingConstants.CENTER);
		btnSave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				
				if(cbProjectCode.getSelectedIndex() > 0){
					if(!isFieldEmpty()){						
						try{
							
							Integer.parseInt(tfDemandedQtty.getText());
							Integer.parseInt(tfCycle.getText());
							Integer.parseInt(tfLeadTime.getText());
							
							int projectCode_index = cbProjectCode.getSelectedIndex();
							String Code = projectCodes.get(projectCode_index);	
							
							int option = JOptionPane.showConfirmDialog(getRootPane(), "Confirm?");
							
							if(option == JOptionPane.YES_OPTION){
								//get Project								
								DatabaseConnection mData = Planning.OpenConnection();		
								List<Project> mListProject = ProjectSqlAdapter.selectProject(mData, "ProjectCode", Code);
								Project mProject = mListProject.get(0);								
								
								Schedule newSchedule = new Schedule(mProject);
								//Activity
								Activity newActivity = new Activity();
								newActivity.setSite(cbSite.getSelectedItem().toString());
								newActivity.setArea(cbArea.getSelectedItem().toString());
								newActivity.setStage(cbStage.getSelectedItem().toString());
								newActivity.setAction(cbAction.getSelectedItem().toString());
								newActivity.setUnit(cbUnit.getSelectedItem().toString());
								newActivity.setLeadtime(Integer.parseInt(tfLeadTime.getText()));
								
								//Test If Activity Exists In DataBase								
								List<Activity> mListActivity = ActivitySqlAdapter.selectActivity(mData, newActivity);
								Activity mActivity = mListActivity.get(0);
								if(mActivity != null){
									newSchedule.setActivity(mActivity);
									newSchedule.setDemandedQtty(Integer.parseInt(tfDemandedQtty.getText()));
									newSchedule.setCycle(Integer.parseInt(tfCycle.getText()));								
									newSchedule.setNotes(tfNotes.getText());								
									Date selectedDate = (Date) DatePicker.getModel().getValue();
									Timestamp tDate = new Timestamp(selectedDate.getTime());  
									newSchedule.setDate(tDate);
									newSchedule.setStatus(Schedule.ScheduleStatus.PENDING);
									
									switch(operation){
									case CREATE:
										if(ScheduleSqlAdapter.insertSchedule(mData, newSchedule))
											JOptionPane.showMessageDialog(getRootPane(), "Planning Created Successfully!");
										operation = null;
										setEnableFields(false);
										break;
									case EDIT:
										newSchedule.setId(oldScheduleId);
										if(ScheduleSqlAdapter.updateSchedule(mData, newSchedule)){
											JOptionPane.showMessageDialog(getRootPane(), "Planning Edited Successfully!");
										}
										oldScheduleId = null;
										operation = null;
										setEnableFields(false);
										break;
									}
									
									fieldReset();
									refreshScheduleList(projectCodes);
								}
								mData.closeConnection();
							}
						}					
						catch(Exception ex){
							JOptionPane.showMessageDialog(getRootPane(), "Invalid Input!");
							System.out.println(ex.getMessage());
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
		mTable.getColumnModel().getColumn(1).setMinWidth(0);
		mTable.getColumnModel().getColumn(1).setMaxWidth(0);
		mTable.getColumnModel().getColumn(1).setWidth(0);
		
		//-- BUTTONS
		//button Delete
		btnDelete.setPreferredSize(new Dimension(150,50));
		btnDelete.setVerticalTextPosition(SwingConstants.CENTER);
		btnDelete.setHorizontalTextPosition(SwingConstants.CENTER);
		ImageIcon imgDelete = new ImageIcon(getClass().getClassLoader().getResource("resources/btnDelete.png"));
		btnDelete.setIcon(imgDelete);
		btnDelete.setBorder(BorderFactory.createEmptyBorder());
		btnDelete.setContentAreaFilled(false);
		btnDelete.setToolTipText("Delete Plan");
		btnDelete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				DatabaseConnection mData = Planning.OpenConnection();
				int selectedRow = mTable.getSelectedRow();
				
				if(selectedRow > -1){
					int option = JOptionPane.showConfirmDialog(getRootPane(), "Are you sure?");
					
					if(option == JOptionPane.YES_OPTION){
						String pid = mTable.getValueAt(selectedRow, 0).toString();						
						Project mProject = new Project();
						mProject.setProjectCode(pid);
						Schedule mSchedule = new Schedule(mProject);
						
						mSchedule.setId(mTable.getValueAt(selectedRow, 1).toString());
					
						boolean scheduleDeleted = ScheduleSqlAdapter.deleteSchedule(mData, mSchedule);
						if(scheduleDeleted){
							JOptionPane.showMessageDialog(getRootPane(), "Plan Deleted Successfuly.");
							refreshScheduleList(projectCodes);
						}else{
							JOptionPane.showMessageDialog(getRootPane(), "Error!");
						}
					}
				}
				mData.closeConnection();
			}
		});
		
		//button Edit
		btnEdit.setPreferredSize(new Dimension(150,50));
		btnEdit.setVerticalTextPosition(SwingConstants.CENTER);
		btnEdit.setHorizontalTextPosition(SwingConstants.CENTER);
		ImageIcon imgEdit = new ImageIcon(getClass().getClassLoader().getResource("resources/btnEdit.png"));
		btnEdit.setBorder(BorderFactory.createEmptyBorder());
		btnEdit.setContentAreaFilled(false);
		btnEdit.setIcon(imgEdit);
		btnEdit.setToolTipText("Edit Plan");
		btnEdit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				
				int selectedRow = mTable.getSelectedRow();				
				if(selectedRow > -1){
					String pid = mTable.getValueAt(selectedRow, 0).toString();
					cbProjectCode.setSelectedItem(pid);
					//send to edition
					operation = Operation.EDIT;
					setEnableFields(true);
					Schedule oldSchedule = new Schedule(new Project());					
					
					oldSchedule.setId(mTable.getValueAt(selectedRow, 1).toString());
					
					oldScheduleId = oldSchedule.getId();
					
					DatabaseConnection mData = Planning.OpenConnection();		
					oldSchedule = ScheduleSqlAdapter.selectSchedule(mData, oldSchedule.getId()).get(0);
					
					cbSite.setSelectedItem(oldSchedule.getActivity().getSite());
					cbArea.setSelectedItem(oldSchedule.getActivity().getArea());
					cbStage.setSelectedItem(oldSchedule.getActivity().getStage());
					cbUnit.setSelectedItem(oldSchedule.getActivity().getUnit());
					cbAction.setSelectedItem(oldSchedule.getActivity().getAction());
					tfDemandedQtty.setText(String.valueOf(oldSchedule.getDemandedQtty()));
					tfCycle.setText(String.valueOf(oldSchedule.getCycle()));
					tfNotes.setText(oldSchedule.getNotes());
					tfLeadTime.setText(String.valueOf(oldSchedule.getActivity().getLeadtime()));
					DateModel.setValue(new java.sql.Date(oldSchedule.getDate().getTime()));
					mData.closeConnection();					
				}				
			}
		});
				
		//button New
		btnNew.setPreferredSize(new Dimension(150,50));
		btnNew.setVerticalTextPosition(SwingConstants.CENTER);
		btnNew.setHorizontalTextPosition(SwingConstants.CENTER);
		ImageIcon imgNew = new ImageIcon(getClass().getClassLoader().getResource("resources/btnNew.png"));
		btnNew.setBorder(BorderFactory.createEmptyBorder());
		btnNew.setContentAreaFilled(false);
		btnNew.setIcon(imgNew);
		btnNew.setToolTipText("Create New Plan");
		btnNew.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				
				if(cbProjectCode.getSelectedIndex() > 0){
					operation = Operation.CREATE;
					setEnableFields(true);
				}								
			}
		});
		//-- INTERFACE --
		cbProjectCode.setPreferredSize(new Dimension(80,20));
		cbSite.setPreferredSize(new Dimension(240,20));
		cbArea.setPreferredSize(new Dimension(240,20));
		cbStage.setPreferredSize(new Dimension(240,20));
		cbUnit.setPreferredSize(new Dimension(240,20));
		cbAction.setPreferredSize(new Dimension(150,20));
		
		cbSite.setBackground(Color.white);		
		cbArea.setBackground(Color.white);
		cbUnit.setBackground(Color.white);
		cbStage.setBackground(Color.white);
		cbAction.setBackground(Color.white);	
		cbProjectCode.setBackground(Color.white);
		
		setEnableFields(false);
		
		//Panel Project Selection
		GridBagConstraints c = new GridBagConstraints();
		c.ipady = 5;
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
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
		c.gridx = 7;
		ProjectPanel.add(new JLabel("   "),c);
		c.gridx = 8;
		ProjectPanel.add(new JLabel("   "),c);
		c.gridx = 9;
		ProjectPanel.add(new JLabel("   "),c);
		c.gridx = 10;
		ProjectPanel.add(new JLabel("   "),c);
		c.gridx = 0;
		c.gridy = 2;
		ProjectPanel.add(lbProjectName,c);
		c.gridx = 1;
		c.gridwidth = 3;
		ProjectPanel.add(tfProjectName,c);
		c.gridwidth = 1;
		c.gridx = 4;
		ProjectPanel.add(lbProjectCategory, c);
		c.gridx = 5;
		c.gridwidth = 6;
		ProjectPanel.add(tfProjectCategory, c);
		c.gridwidth = 1;
		
		ProjectPanel.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
				"Project Selection",
                TitledBorder.LEFT,
                TitledBorder.TOP));
		
		//Panel Plan input
		c = new GridBagConstraints();
		c.ipady = 5;
		c.weightx = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.gridx = 0;
		c.gridy = 0;			
		InputPanel.add(lbSite,c);		
		c.gridx = 1;		
		c.gridwidth = 2;
		InputPanel.add(cbSite,c);
		c.gridwidth = 1;		
		c.gridx = 3;
		InputPanel.add(lbArea,c);
		c.gridx = 4;	
		c.gridwidth = 1;
		InputPanel.add(cbArea,c);		
		c.gridx = 5;
		InputPanel.add(lbStage,c);
		c.gridx = 6;
		c.gridwidth = 2;
		InputPanel.add(cbStage,c);
		c.gridwidth = 1;
		
		c.gridy = 1;
		c.ipady = 15;
		InputPanel.add(new JLabel(""),c);
		c.ipady = 5;
		
		c.gridy = 2;
		c.gridx = 0;
		InputPanel.add(lbUnit,c);
		c.gridx = 1;
		InputPanel.add(cbUnit,c);
		c.gridx = 3;
		InputPanel.add(lbActivity,c);
		c.gridx = 4;
		InputPanel.add(cbAction,c);
		c.gridx = 5;
		InputPanel.add(lbDemandedQtty,c);
		c.gridx = 6;
		InputPanel.add(tfDemandedQtty,c);
		
		c.gridy = 3;
		c.ipady = 15;
		InputPanel.add(new JLabel(""),c);
		c.ipady = 5;
		
		c.gridy = 4;
		c.gridx = 0;			
		InputPanel.add(lbCycle,c);		
		c.gridx = 1;	
		InputPanel.add(tfCycle,c);				
		c.gridx = 3;
		InputPanel.add(lbLeadTime,c);
		c.gridx = 4;	
		c.gridwidth = 1;
		InputPanel.add(tfLeadTime,c);		
		c.gridx = 5;
		InputPanel.add(lbDate,c);
		c.gridx = 6;
		InputPanel.add(DatePicker,c);
		c.gridwidth = 1;
		//
				
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		
		InputPanel.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
				"Plan Info",
                TitledBorder.LEFT,
                TitledBorder.TOP));
					
				
		scrollPane.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder(),
				"List of Plans",
                TitledBorder.LEFT,
                TitledBorder.TOP));
		buttonPanel.add(btnSave);
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
		
		
		bottomPanel.add(btnDelete);
		bottomPanel.add(btnNew);
		bottomPanel.add(btnEdit);
		c.gridwidth = 3;
		c.ipady = 0;
		c.gridy = 3;
		c.gridx = 0;		
		
		PlanPanel.add(bottomPanel,c);
		
		c.anchor = GridBagConstraints.NORTH;
		this.add(PlanPanel, c);
	}
}
