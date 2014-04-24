package Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Planning.DatabaseConnection;
import Planning.Project;
import Planning.ProjectSqlAdapter;

@SuppressWarnings("serial")
public class PanelProjectList extends JPanel{
	
	
	Object [] columnNames = {"Pid","Name","Category","Brand","End Market"};
	JTable mTable = new JTable(new DefaultTableModel(columnNames, 0));
	DefaultTableModel mModel = (DefaultTableModel) mTable.getModel();
	JButton btnLoadData = new JButton("Load Data");
	JButton btnDeleteData = new JButton("Delete");
	JLabel lbName = new JLabel(), lbCategory = new JLabel(), lbBrand = new JLabel(), lbEndMarket = new JLabel();
	JTextField tfName = new JTextField(30), tfCategory = new JTextField(10), tfBrand = new JTextField(12), tfEndMarket = new JTextField(11);
	
		
	public PanelProjectList(){
		
		
		setLayout(new FlowLayout());		
		
		this.setVisible(true);
		
		JPanel InputPanel = new JPanel(new GridBagLayout());
		JPanel MainPanel = new JPanel(new GridBagLayout()); 
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JScrollPane scrollPane = new JScrollPane(mTable);
		
		//
		lbName.setText("Name: ");
		lbCategory.setText("Category: ");
		lbBrand.setText("Brand: ");
		lbEndMarket.setText("  End Market: ");
		
		GridBagConstraints c = new GridBagConstraints();
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
		c.gridy = 0;
		InputPanel.add(lbCategory,c);
		c.gridx = 4;
		InputPanel.add(tfCategory,c);
		
		c.gridx = 0;
		c.gridy = 1;
		InputPanel.add(lbBrand,c);
		c.gridx = 1;
		InputPanel.add(tfBrand,c);
		
		c.gridx = 2;
		InputPanel.add(lbEndMarket,c);
		c.gridx = 3;
		c.gridwidth = 2;
		InputPanel.add(tfEndMarket,c);
		c.gridwidth = 1;
		//
		
				
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		InputPanel.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
				"Project Search",
                TitledBorder.LEFT,
                TitledBorder.TOP));
		MainPanel.add(InputPanel,c);	
		c.gridwidth = 3;
		c.ipady = 0;
		c.gridy = 1;
		c.gridx = 0;
		scrollPane.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder(),
				"List of Projects",
                TitledBorder.LEFT,
                TitledBorder.TOP));
		MainPanel.add(scrollPane,c);
		
		
		DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
		centerRender.setHorizontalAlignment(JLabel.CENTER);
		
		for(int i=0; i < mTable.getColumnCount(); i++)
			mTable.getColumnModel().getColumn(i).setCellRenderer(centerRender);
				
		mTable.setCellSelectionEnabled(false);
		mTable.setRowSelectionAllowed(true); 
		mTable.setFillsViewportHeight(true);
		mTable.setBackground(Color.lightGray);
		mTable.setSelectionBackground(Color.white);
		
		//button Load
		btnLoadData.setPreferredSize(new Dimension(150,32));
		btnLoadData.setVerticalTextPosition(AbstractButton.CENTER);
		btnLoadData.setHorizontalTextPosition(AbstractButton.CENTER);
		btnLoadData.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String URL = "jdbc:mysql://localhost:3306/Planning";
				String login = "root";
				String pass = "root";

				DatabaseConnection mData = new DatabaseConnection(URL, login, pass);	
				mData.openConnection();
				ProjectSqlAdapter mProjectSqlAdapter = new ProjectSqlAdapter();
				List<Project> mListProject = mProjectSqlAdapter.SelectProject(mData);

				for(int row = mModel.getRowCount() - 1; row >= 0 ; row--)
					mModel.removeRow(row);
				
				int line = 0, column = 0;
				for(Project p:mListProject){
					mModel.addRow(new Object[]{});
					column = 0;
					mTable.setValueAt(p.getProjectCode(), line, column);
					column++;
					mTable.setValueAt(p.getName(), line, column);
					column++;
					mTable.setValueAt(p.getCategory(), line, column);
					column++;
					mTable.setValueAt(p.getBrand(), line, column);
					column++;
					mTable.setValueAt(p.getEndMarket(), line, column);
					line++;
				}
			}
		});
		
				
		//button Delete
		btnDeleteData.setPreferredSize(new Dimension(150,32));
		btnDeleteData.setVerticalTextPosition(AbstractButton.CENTER);
		btnDeleteData.setHorizontalTextPosition(AbstractButton.CENTER);
		btnDeleteData.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String URL = "jdbc:mysql://localhost:3306/Planning";
				String login = "root";
				String pass = "root";

				DatabaseConnection mData = new DatabaseConnection(URL, login, pass);	
				mData.openConnection();
				ProjectSqlAdapter mProjectSqlAdapter = new ProjectSqlAdapter();
				
				int selectedRow = mTable.getSelectedRow();
					
				if(selectedRow > -1){
					int option = JOptionPane.showConfirmDialog(getRootPane(), "Are you sure?");
					
					if(option == JOptionPane.YES_OPTION){
						String pid = mTable.getValueAt(selectedRow, 0).toString();
						Project newProject = new Project();
						newProject.setProjectCode(pid);
						mProjectSqlAdapter.DeleteProject(mData, newProject);
						JOptionPane.showInternalMessageDialog(getRootPane(), "Project Deleted Successfuly.");
					}
				}
				btnLoadData.doClick();
			}
		});
		
		
		buttonPanel.add(btnDeleteData);	
		buttonPanel.add(btnLoadData);	
		c.gridwidth = 3;
		c.ipady = 0;
		c.gridy = 2;
		c.gridx = 0;
		
		MainPanel.add(buttonPanel,c);
		
		this.add(MainPanel);
	}
	
}
