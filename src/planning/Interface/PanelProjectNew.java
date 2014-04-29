package planning.Interface;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import planning.Planning.DatabaseConnection;
import planning.Planning.Project;
import planning.Planning.ProjectSqlAdapter;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.SqlDateModel;


@SuppressWarnings("serial")
public class PanelProjectNew extends JPanel{
	
	JLabel lbName = new JLabel(), lbCategory = new JLabel(), lbBrand = new JLabel(), lbEndMarket = new JLabel(), lbOpCo = new JLabel(), lbComplexity = new JLabel(), lbStart = new JLabel(), lbFinish = new JLabel();
	JTextField tfName = new JTextField(30), tfCategory = new JTextField(10), tfBrand = new JTextField(10), tfEndMarket = new JTextField(11), tfOpCo = new JTextField(11), tfComplexity = new JTextField(12);
	SqlDateModel StartModel = new SqlDateModel();
	SqlDateModel FinishModel = new SqlDateModel();
  	JDatePanelImpl StartDatePanel = new JDatePanelImpl(StartModel);
  	JDatePanelImpl FinishDatePanel = new JDatePanelImpl(FinishModel);
	final JDatePickerImpl StartDatePicker = new JDatePickerImpl(StartDatePanel);
	final JDatePickerImpl FinishDatePicker = new JDatePickerImpl(FinishDatePanel);
	
	private void fieldReset(){
		tfName.setText(null);
		tfCategory.setText(null);
		tfBrand.setText(null);
		tfEndMarket.setText(null);
		tfOpCo.setText(null);
		tfComplexity.setText(null);
		StartModel.setValue(null);
		FinishModel.setValue(null);		
	}
	
	public PanelProjectNew(){
		
		this.setVisible(true);
		this.setLayout(new GridBagLayout());
		
		JPanel InputPanel = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton btnCreate = new JButton("Create");

		//
		lbName.setText("Name: ");
		lbCategory.setText(" Category: ");
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
		InputPanel.add(tfCategory,c);
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
		InputPanel.add(tfComplexity,c);
		
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
						newProject.setCategory(tfCategory.getText());
						newProject.setBrand(tfBrand.getText());
						newProject.setOpco(tfOpCo.getText());
						newProject.setEndMarket(tfEndMarket.getText());
						newProject.setApproval(false);
						
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
