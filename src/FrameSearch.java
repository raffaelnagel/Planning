import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FrameSearch extends JFrame{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Object [] columnNames = {"Pid","Name","Category","Brand","Leader"};
	JTable mTable = new JTable(new DefaultTableModel(columnNames, 5));
	
		
	public FrameSearch(){
		
		setTitle("Projects");
		
		setSize(500,500);		
		setLocation(0,0);		
		setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setVisible(true);
		this.add(mTable);
		
		String URL = "jdbc:mysql://localhost:3306/Planning";
		String login = "root";
		String pass = "root";
		
		DatabaseConnection mData = new DatabaseConnection(URL, login, pass);		
		mData.openConnection();
		List<Project> mListProject = mData.getProjectsFromDatabase();	
		
		int line = 0, column = 0;
		for(Project p:mListProject){
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
	
}
