import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class FrameProjectList extends JPanel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Object [] columnNames = {"Pid","Name","Category","Brand","End Market"};
	JTable mTable = new JTable(new DefaultTableModel(columnNames, 0));
	DefaultTableModel mModel = (DefaultTableModel) mTable.getModel();
	JButton btnLoadData = new JButton("Load Data");
	JButton btnDeleteData = new JButton("Delete");
	JLabel lbName = new JLabel(), lbCategory = new JLabel(), lbBrand = new JLabel(), lbEndMarket = new JLabel();
	JTextField tfName = new JTextField(30), tfCategory = new JTextField(10), tfBrand = new JTextField(12), tfEndMarket = new JTextField(11);
	
		
	public FrameProjectList(){
		
		
		setLayout(new BorderLayout());
		
		
		this.setVisible(true);
		
		JPanel firstPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel mainPanel = new JPanel(); 
		JPanel buttonPanel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(mTable);
		
		//
		lbName.setText("Name: ");
		lbCategory.setText("Category: ");
		lbBrand.setText("Brand: ");
		lbEndMarket.setText("End Market: ");
		
		firstPanel.add(lbName);
		firstPanel.add(tfName);
		firstPanel.add(lbCategory);
		firstPanel.add(tfCategory);
		firstPanel.add(lbBrand);
		firstPanel.add(tfBrand);
		firstPanel.add(lbEndMarket);
		firstPanel.add(tfEndMarket);
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
		
		//this.add(BorderLayout.NORTH, firstPanel);
		this.add(BorderLayout.CENTER,mainPanel);
		this.add(BorderLayout.SOUTH, buttonPanel);
	}
	
}
