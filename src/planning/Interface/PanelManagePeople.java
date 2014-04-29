package planning.Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
import planning.Planning.People;
import planning.Planning.PeopleSqlAdapter;



@SuppressWarnings("serial")
public class PanelManagePeople extends JPanel{

	JLabel lbName = new JLabel(), lbCode = new JLabel();
	JLabel lbListHeader = new JLabel();
	JTextField tfName = new JTextField(40), tfCode = new JTextField(10);
	final DefaultListModel listPeopleModel = new DefaultListModel();
	JList listPeople = new JList(listPeopleModel);
	JScrollPane scrollPane = new JScrollPane(listPeople, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	JButton btnAdd = new JButton();
	
	private void refreshListItems(){
		//add data to list
		PeopleSqlAdapter mPeopleSqlAdapter = new PeopleSqlAdapter();
		String URL = "jdbc:mysql://localhost:3306/Planning";
		String login = "root";
		String pass = "root";

		DatabaseConnection mData = new DatabaseConnection(URL, login, pass);	
		mData.openConnection();
		List<People> mListPeople = new ArrayList<People>();
		mListPeople = mPeopleSqlAdapter.SelectPeople(mData);
		
		listPeopleModel.clear();
		List<String> employees = new ArrayList<String>();
		for(People p:mListPeople){			
			//listPeopleModel.addElement(p.getName());
			employees.add(p.getName());
		}
		Collections.sort(employees);
		for(String s:employees){			
			listPeopleModel.addElement(s);
		}
	}
	
	public PanelManagePeople(){
	
		this.setVisible(true);
		this.setLayout(new GridBagLayout());
		
		JPanel InputPanel = new JPanel(new GridBagLayout());
		JPanel ListPanel = new JPanel(new GridBagLayout());
		JPanel MainPanel = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
		lbName.setText("Name: ");
		lbCode.setText(" Code: ");
		btnAdd.setText("Add");
		
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
		InputPanel.add(lbCode,c);
		c.gridx = 4;
		c.gridwidth = 2;
		InputPanel.add(tfCode,c);
		
		//button Add
		btnAdd.setPreferredSize(new Dimension(150,32));
		btnAdd.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAdd.setVerticalTextPosition(SwingConstants.CENTER);
		btnAdd.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				String URL = "jdbc:mysql://localhost:3306/Planning";
				String login = "root";
				String pass = "root";

				DatabaseConnection mData = new DatabaseConnection(URL, login, pass);	
				mData.openConnection();
				
				PeopleSqlAdapter mPeopleSqlAdapter = new PeopleSqlAdapter();
				
				if(tfName.getText().length() > 0){
					int option = JOptionPane.showConfirmDialog(getRootPane(), "Confirm?");
					
					if(option == JOptionPane.YES_OPTION){
						People newPeople = new People();
						
						newPeople.setName(tfName.getText());
						newPeople.setCode(tfCode.getText());
						
						if(mPeopleSqlAdapter.Exists(mData, newPeople) == true){
							JOptionPane.showMessageDialog(getRootPane(), "Employee already exists!");
						}else{
							if(mPeopleSqlAdapter.InsertPeople(mData, newPeople)){
								JOptionPane.showMessageDialog(getRootPane(), "Employee sucessfully added");
								refreshListItems();
							}else{
								JOptionPane.showMessageDialog(getRootPane(), "Error!");
							}
						}
						tfName.setText(null);
						tfCode.setText(null);
					}
				}else{
					JOptionPane.showMessageDialog(getRootPane(), "Please, enter a valid Name");		
				}
			}
		});
		buttonPanel.add(btnAdd);
		c.gridy = 5;
		c.ipady = 15;
		InputPanel.add(new JLabel(""),c);
		c.ipady = 5;
		c.gridy = 7;
		c.gridx = 5;		
		InputPanel.add(buttonPanel,c);
		
		InputPanel.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
				"Add New Employee",
                TitledBorder.LEFT,
                TitledBorder.TOP));
		
		
		//----List Panel----------------------
		lbListHeader.setText("Employees");
		listPeople.setCellRenderer(new MyListCellRenderer());
		listPeople.setSelectionBackground(Color.darkGray);
		listPeople.setSelectionForeground(Color.white);
		listPeople.setBorder(new LineBorder(Color.darkGray, 1));
		listPeople.setFixedCellHeight(30);
		listPeople.setFixedCellWidth(100);
		scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		
		c.ipady = 5;
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;		
		ListPanel.add(lbListHeader, c);
		
		c.gridx = 2;
		c.gridwidth = 4;
		ListPanel.add(new JLabel(""), c);
		
		c.gridx = 3;
		c.gridwidth = 4;
		ListPanel.add(new JLabel(""), c);
				
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.ipady = 160;
		c.ipadx = 15;
		ListPanel.add(scrollPane, c);
		c.gridwidth = 1;
		c.ipady = 5;
		
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 4;
		ListPanel.add(new JLabel(""), c);
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 4;
		ListPanel.add(new JLabel(""), c);
		
		ListPanel.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
				"Employees List",
                TitledBorder.LEFT,
                TitledBorder.TOP));
		
		//----Main Panel---------------------
		
		c.gridx = 0;
		c.gridy = 0;		
		MainPanel.add(InputPanel, c);
		c.gridx = 0;
		c.gridy = 1;		
		MainPanel.add(ListPanel, c);
		
		c.anchor = GridBagConstraints.NORTH;
		c.weighty = 1.0;
		this.add(MainPanel, c);
		
		
		refreshListItems();
	}
	
}
