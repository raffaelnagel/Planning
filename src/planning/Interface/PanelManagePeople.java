package planning.Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import planning.Planning.DatabaseConnection;
import planning.Planning.MyListCellRenderer;
import planning.Planning.People;
import planning.Planning.PeopleSqlAdapter;



@SuppressWarnings("serial")
public class PanelManagePeople extends JPanel{

	private JLabel lbName = new JLabel(), lbCode = new JLabel(), lbWorkgroup = new JLabel();
	private JLabel lbListHeader = new JLabel();
	private JTextField tfPeopleName = new JTextField(), tfPeopleCode = new JTextField(), tfPeopleWorkgroup = new JTextField();	
	private JTextField tfName = new JTextField(40), tfCode = new JTextField(10), tfWorkgroup = new JTextField(20);
	private final DefaultListModel listPeopleModel = new DefaultListModel();
	private JList listPeople = new JList(listPeopleModel);
	private JScrollPane scrollPane = new JScrollPane(listPeople, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	private JButton btnAdd = new JButton(), btnDelete = new JButton(), btnEdit = new JButton();
	

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
			employees.add(p.getName());
		}
		Collections.sort(employees);
		for(String s:employees){			
			listPeopleModel.addElement(s);
		}
		
		tfPeopleName.setText(null);
		tfPeopleCode.setText(null);
		tfPeopleWorkgroup.setText(null);
	}
	
	public PanelManagePeople(){
	
		this.setVisible(true);
		this.setLayout(new GridBagLayout());
		
		JPanel InputPanel = new JPanel(new GridBagLayout());
		JPanel ListPanel = new JPanel(new GridBagLayout());
		JPanel InfoPanel = new JPanel(new GridBagLayout());
		JPanel SubPanel = new JPanel(new GridBagLayout());
		JPanel MainPanel = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
		lbName.setText("Name: ");
		lbCode.setText(" Code: ");
		lbWorkgroup.setText("Workgroup: ");
		btnAdd.setText("");
		
		GridBagConstraints c = new GridBagConstraints();
		c.ipady = 5;
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;			
		InputPanel.add(lbName,c);		
		c.gridx = 1;		
		c.gridwidth = 3;
		InputPanel.add(tfName,c);
		c.gridwidth = 1;		
		c.gridx = 4;
		InputPanel.add(lbCode,c);
		c.gridx = 5;
		c.gridwidth = 2;
		InputPanel.add(tfCode,c);
		c.gridwidth = 1;		
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 1;
		InputPanel.add(new JLabel(" "), c);
		c.ipady = 5;
		c.gridy = 2;		
		InputPanel.add(lbWorkgroup,c);
		c.gridx = 2;
		c.gridwidth = 1;
		InputPanel.add(tfWorkgroup,c);
		c.gridx = 3;
		InputPanel.add(new JLabel(" "), c);
		
		//button Add
		btnAdd.setPreferredSize(new Dimension(150,50));
		btnAdd.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAdd.setVerticalTextPosition(SwingConstants.CENTER);
		
		ImageIcon imgAdd = new ImageIcon(getClass().getClassLoader().getResource("resources/btnAddEmp.png"));
		btnAdd.setBorder(BorderFactory.createEmptyBorder());
		btnAdd.setContentAreaFilled(false);
		btnAdd.setIcon(imgAdd);
		btnAdd.setIconTextGap(10);
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
						newPeople.setCode(tfCode.getText().toUpperCase());
						newPeople.setWorkgroup(tfWorkgroup.getText().toUpperCase());
						
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
						tfWorkgroup.setText(null);
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
		listPeople.setFixedCellHeight(32);
		listPeople.setFixedCellWidth(100);
		listPeople.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                  try{
                	tfPeopleName.setText(listPeople.getSelectedValue().toString());
                  
                  String URL = "jdbc:mysql://localhost:3306/Planning";
                  String login = "root";
  				  String pass = "root";

  				  DatabaseConnection mData = new DatabaseConnection(URL, login, pass);	
  				  mData.openConnection();
  				
  				  PeopleSqlAdapter mPeopleSqlAdapter = new PeopleSqlAdapter();
  				  
  				  List<People> mListPeople = new ArrayList<People>();
  				  mListPeople = mPeopleSqlAdapter.SelectPeople(mData, "Name", listPeople.getSelectedValue().toString());
  				  tfPeopleCode.setText(mListPeople.get(0).getCode());
  				  tfPeopleWorkgroup.setText(mListPeople.get(0).getWorkgroup());
                  }catch(Exception e){
                	  
                  }
                }
            }
        });
		scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		
		c.ipady = 5;
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;		
		ListPanel.add(lbListHeader, c);
						
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.ipady = 160;
		c.ipadx = 15;
		ListPanel.add(scrollPane, c);
		c.gridwidth = 1;
		c.ipady = 5;
		
		//
		tfPeopleName.setEditable(false);
		tfPeopleName.setFont(new Font("Sans Serif", Font.BOLD, 15));
		tfPeopleCode.setEditable(false);
		tfPeopleCode.setFont(new Font("Sans Serif", Font.BOLD, 15));
		tfPeopleWorkgroup.setEditable(false);
		tfPeopleWorkgroup.setFont(new Font("Sans Serif", Font.BOLD, 15));
		tfPeopleName.setBorder(null);
		tfPeopleCode.setBorder(null);
		tfPeopleWorkgroup.setBorder(null);
		//button Delete
		btnDelete.setText("");
		btnDelete.setPreferredSize(new Dimension(100,50));
		btnDelete.setHorizontalTextPosition(SwingConstants.CENTER);
		btnDelete.setVerticalTextPosition(SwingConstants.CENTER);
		ImageIcon imgDelete = new ImageIcon(getClass().getClassLoader().getResource("resources/btnRemoveEmp.png"));
		btnDelete.setBorder(BorderFactory.createEmptyBorder());
		btnDelete.setContentAreaFilled(false);
		btnDelete.setIcon(imgDelete);
		btnDelete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				String URL = "jdbc:mysql://localhost:3306/Planning";
				String login = "root";
				String pass = "root";

				DatabaseConnection mData = new DatabaseConnection(URL, login, pass);	
				mData.openConnection();
				
				PeopleSqlAdapter mPeopleSqlAdapter = new PeopleSqlAdapter();
				People mPeople = new People(tfPeopleName.getText(), tfPeopleCode.getText());
				int option = JOptionPane.showConfirmDialog(getRootPane(), "Confirm?");
				
				if(option == JOptionPane.YES_OPTION){
					if(mPeopleSqlAdapter.DeletePeople(mData, mPeople)){
						JOptionPane.showMessageDialog(getRootPane(), "Employee sucessfully deleted");					
						refreshListItems();
						listPeople.setSelectedIndex(listPeople.getSelectedIndex() + 1);
					}else{
						JOptionPane.showMessageDialog(getRootPane(), "Error!");
					}
				}
			}
		});
		//button Edit
		btnEdit.setPreferredSize(new Dimension(150,50));
		btnEdit.setHorizontalTextPosition(SwingConstants.CENTER);
		btnEdit.setVerticalTextPosition(SwingConstants.CENTER);
		
		ImageIcon imgEdit = new ImageIcon(getClass().getClassLoader().getResource("resources/btnEditEmp.png"));
		btnEdit.setBorder(BorderFactory.createEmptyBorder());
		btnEdit.setContentAreaFilled(false);
		btnEdit.setIcon(imgEdit);
		btnEdit.setIconTextGap(10);
		btnEdit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				String URL = "jdbc:mysql://localhost:3306/Planning";
				String login = "root";
				String pass = "root";

				DatabaseConnection mData = new DatabaseConnection(URL, login, pass);	
				mData.openConnection();
				
				PeopleSqlAdapter mPeopleSqlAdapter = new PeopleSqlAdapter();
				
				
				int option = JOptionPane.showConfirmDialog(getRootPane(), "Confirm?");
				
				if(option == JOptionPane.YES_OPTION){
					People newPeople = new People();
					
					newPeople.setName(tfName.getText());
					newPeople.setCode(tfCode.getText().toUpperCase());
					newPeople.setWorkgroup(tfWorkgroup.getText().toUpperCase());					
					
					People oldPeople = new People();
					List<People> mListPeople = new ArrayList<People>();
	  			    mListPeople = mPeopleSqlAdapter.SelectPeople(mData, "Name", listPeople.getSelectedValue().toString());
	  			    oldPeople = mListPeople.get(0);
	  			    
					if(mPeopleSqlAdapter.Exists(mData, oldPeople) == true){						
		  			    if(mPeopleSqlAdapter.UpdatePeople(mData, oldPeople, newPeople)){
							JOptionPane.showMessageDialog(getRootPane(), "Employee sucessfully edited");
							refreshListItems();
						}else{
							JOptionPane.showMessageDialog(getRootPane(), "Error!");
						}
					}
					tfName.setText(null);
					tfCode.setText(null);
					tfWorkgroup.setText(null);
				}
			}
		});
		c.gridx = 1;
		c.gridy = 1;
		c.ipady = 80;
		InfoPanel.add(new JLabel(""), c);
		c.ipady = 1;
		c.gridx = 0;
		c.gridy = 2;
		InfoPanel.add(new JLabel("         NAME: "), c);
		c.gridx = 0;
		c.gridy = 3;
		InfoPanel.add(new JLabel("         CODE: "), c);
		c.gridx = 0;
		c.gridy = 4;
		InfoPanel.add(new JLabel("         WORKGROUP: "), c);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 4;
		InfoPanel.add(tfPeopleName, c);
		c.gridx = 1;
		c.gridy = 3;
		InfoPanel.add(tfPeopleCode, c);
		c.gridx = 1;
		c.gridy = 4;
		InfoPanel.add(tfPeopleWorkgroup, c);
		c.gridwidth = 1;
		
		c.gridx = 5;
		c.gridy = 1;
		InfoPanel.add(new JLabel(""), c);
		
		c.gridx = 0;
		c.gridy = 5;
		c.ipady = 268;		
		InfoPanel.add(new JLabel(""), c);
		c.ipady = 1;
		
		c.gridx = 4;
		c.gridy = 6;
		InfoPanel.add(btnDelete, c);
		c.gridx = 5;
		InfoPanel.add(btnEdit, c);
		
		InfoPanel.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
				"Employee Info",
                TitledBorder.LEFT,
                TitledBorder.TOP));
		
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.ipadx = 15;
		SubPanel.add(InfoPanel, c);
		c.gridx = 0;
		c.gridy = 0;
		SubPanel.add(ListPanel, c);
		
		ListPanel.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
				"Employees List",
                TitledBorder.LEFT,
                TitledBorder.TOP));
		
		//----Main Panel---------------------
		c.ipady = 1;
		c.gridx = 0;
		c.gridy = 0;		
		MainPanel.add(InputPanel, c);
		c.gridx = 0;
		c.gridy = 1;		
		MainPanel.add(SubPanel, c);
		
		c.anchor = GridBagConstraints.NORTH;
		c.weighty = 1.0;
		this.add(MainPanel, c);		
		
		refreshListItems();	
	}
	
}
