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

import planning.Data.AuxiliarData;
import planning.Data.Brand;
import planning.Data.People;
import planning.Data.Planning;
import planning.Data.Project;
import planning.Data.Team;
import planning.DataAdapter.AuxiliarDataSqlAdapter;
import planning.DataAdapter.BrandSqlAdapter;
import planning.DataAdapter.DatabaseConnection;
import planning.DataAdapter.ProjectSqlAdapter;
import planning.DataAdapter.TeamSqlAdapter;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.SqlDateModel;


@SuppressWarnings("serial")
public class PanelProjectNew extends JPanel{
	
	private JLabel lbName = new JLabel(), lbCategory = new JLabel(), lbBrand = new JLabel(), lbEndMarket = new JLabel(), lbOpCo = new JLabel(), lbComplexity = new JLabel(), lbStart = new JLabel(), lbFinish = new JLabel();
	private JLabel lbPitCode = new JLabel(), lbMainProject = new JLabel();
	private JTextField tfName = new JTextField(30);
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
	
	public PanelProjectNew(final People loggedUser){
		
		this.setVisible(true);
		this.setLayout(new GridBagLayout());
		
		JPanel InputPanel = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton btnCreate = new JButton("Create");		
		//
		lbMainProject.setText(" Main Project: ");
		lbPitCode.setText("Pit Code: ");
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
		
		c.gridy = 5;
		c.ipady = 15;
		InputPanel.add(new JLabel(""),c);
		c.ipady = 5;
		
		c.gridy = 6;
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
				DatabaseConnection mData = Planning.OpenConnection();
					
				if(tfName.getText().length() > 0){
					int option = JOptionPane.showConfirmDialog(getRootPane(), "Confirm?");
					
					if(option == JOptionPane.YES_OPTION){						
						Project newProject = new Project();
						
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
						Timestamp tDate = new Timestamp(selectedDate.getTime());  
						newProject.setStart(tDate);
						selectedDate = (Date) FinishDatePicker.getModel().getValue();
						tDate = new Timestamp(selectedDate.getTime());
						newProject.setFinish(tDate);
									
						Date timeNow = new Date(Calendar.getInstance().getTimeInMillis());
						tDate = new Timestamp(timeNow.getTime());
						newProject.setDate(tDate);
						
						boolean ProjectCreated = ProjectSqlAdapter.insertProject(mData, newProject);
						if(ProjectCreated){
							
							//add Project Leader to the Team of the Project
							List<Project> mListProjectTeam = ProjectSqlAdapter.selectProject(mData);
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
							TeamSqlAdapter.insertPeopleTeam(mData, newTeam);
							// --
							
							JOptionPane.showMessageDialog(getRootPane(), "Project Created!");
							fieldReset();
						}else{
							JOptionPane.showMessageDialog(getRootPane(), "Error!");
							fieldReset();
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
