import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class FrameSearch extends JFrame{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Object [] columnNames = {"Pid","Name","Category","Brand","Leader"};
	JTable mTable = new JTable(new DefaultTableModel(columnNames, 0));
	DefaultTableModel mModel = (DefaultTableModel) mTable.getModel();
	JButton btnLoadData = new JButton("Load Data");
	JButton btnInsertData = new JButton("Insert");
	JButton btnDeleteData = new JButton("Delete");
	JLabel lbName = new JLabel(), lbCategory = new JLabel(), lbBrand = new JLabel(), lbLeader = new JLabel();
	JTextField tfName = new JTextField(30), tfCategory = new JTextField(10), tfBrand = new JTextField(12), tfLeader = new JTextField(11);
	
		
	public FrameSearch(){
		
		setTitle("Projects");
		
		setSize(600,550);		
		setLocationRelativeTo(null);		
		setResizable(false);
		setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setVisible(true);
		
		JPanel firstPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel mainPanel = new JPanel(); 
		JPanel buttonPanel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(mTable);
		
		//
		lbName.setText("Name: ");
		lbCategory.setText("Category: ");
		lbBrand.setText("Brand: ");
		lbLeader.setText("Leader: ");
		
		firstPanel.add(lbName);
		firstPanel.add(tfName);
		firstPanel.add(lbCategory);
		firstPanel.add(tfCategory);
		firstPanel.add(lbBrand);
		firstPanel.add(tfBrand);
		firstPanel.add(lbLeader);
		firstPanel.add(tfLeader);
		//
		
		
		mainPanel.setLayout(new BorderLayout());			
		mainPanel.add(scrollPane, BorderLayout.SOUTH);
		mainPanel.add(BorderLayout.CENTER,firstPanel);
		DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
		centerRender.setHorizontalAlignment(JLabel.CENTER);
		
		for(int i=0; i < mTable.getColumnCount(); i++)
			mTable.getColumnModel().getColumn(i).setCellRenderer(centerRender);
				
		mTable.setCellSelectionEnabled(false);
		mTable.setFillsViewportHeight(true);
		
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
				List<Project> mListProject = mData.getProjectsFromDatabase();

				for(int row = mModel.getRowCount() - 1; row >= 0 ; row--)
					mModel.removeRow(row);
				
				int line = 0, column = 0;
				for(Project p:mListProject){
					mModel.addRow(new Object[]{});
					column = 0;
					mTable.setValueAt(p.getPid(), line, column);
					column++;
					mTable.setValueAt(p.getName(), line, column);
					column++;
					mTable.setValueAt(p.getCategory(), line, column);
					column++;
					mTable.setValueAt(p.getBrand(), line, column);
					column++;
					mTable.setValueAt(p.getLeader(), line, column);
					line++;
				}
			}
		});
		
		//button Insert
				btnInsertData.setPreferredSize(new Dimension(150,32));
				btnInsertData.setVerticalTextPosition(AbstractButton.CENTER);
				btnInsertData.setHorizontalTextPosition(AbstractButton.CENTER);
				btnInsertData.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						String URL = "jdbc:mysql://localhost:3306/Planning";
						String login = "root";
						String pass = "root";

						DatabaseConnection mData = new DatabaseConnection(URL, login, pass);	
						mData.openConnection();
						
						Project newProject = new Project();
						newProject.setName(tfName.getText().toString());
						newProject.setCategory(tfCategory.getText().toString());
						newProject.setBrand(tfBrand.getText().toString());
						newProject.setLeader(tfLeader.getText().toString());
						
						mData.updateProject(DatabaseConnection.SqlCommand.INSERT, newProject);						
						JOptionPane.showInternalMessageDialog(getContentPane(), "Project Successfully Created.");
						
						btnLoadData.doClick();
					}
				});
		buttonPanel.add(btnLoadData, BorderLayout.EAST);	
		buttonPanel.add(btnInsertData, BorderLayout.WEST);
		
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
				
				int selectedRow = mTable.getSelectedRow();
					
				if(selectedRow > -1){
					int option = JOptionPane.showConfirmDialog(getContentPane(), "Are you sure?");
					
					if(option == JOptionPane.YES_OPTION){
						String pid = mTable.getValueAt(selectedRow, 0).toString();
						Project newProject = new Project();
						newProject.setPid(pid);
						mData.updateProject(DatabaseConnection.SqlCommand.DELETE, newProject);
						JOptionPane.showInternalMessageDialog(getContentPane(), "Project Deleted Successfuly.");
					}
				}
				btnLoadData.doClick();
			}
		});
		
		
		buttonPanel.add(btnDeleteData);	
		buttonPanel.add(btnLoadData);	
		buttonPanel.add(btnInsertData);
		
		//this.add(BorderLayout.NORTH, firstPanel);
		this.add(BorderLayout.CENTER,mainPanel);
		this.add(BorderLayout.SOUTH, buttonPanel);
	}
	
}
