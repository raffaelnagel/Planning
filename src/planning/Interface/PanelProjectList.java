package planning.Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
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

import planning.Data.AuxiliarData;
import planning.Data.Brand;
import planning.Data.People;
import planning.Data.Planning;
import planning.Data.Project;
import planning.DataAdapter.AuxiliarDataSqlAdapter;
import planning.DataAdapter.BrandSqlAdapter;
import planning.DataAdapter.DatabaseConnection;
import planning.DataAdapter.ProjectSqlAdapter;


@SuppressWarnings("serial")
public class PanelProjectList extends JPanel{	
	
	Object [] columnNames = {"Project Code","Name","Category","Brand","OpCo","End Market", "Complexity", "Approval", "Start", "Finish", "Created on"};
	JTable mTable = new JTable(new DefaultTableModel(columnNames, 0)){
		 @Override
		public boolean getScrollableTracksViewportWidth() {
			   return getPreferredSize().width < getParent().getWidth();
			 }
			};
	DefaultTableModel mModel = (DefaultTableModel) mTable.getModel();
	JButton btnLoadData = new JButton("");
	JButton btnDeleteData = new JButton("");
	JButton btnSearch = new JButton("");
	JButton btnEdit = new JButton("");
	JButton btnNew = new JButton("");
	JLabel lbName = new JLabel(), lbCategory = new JLabel(), lbBrand = new JLabel(), lbEndMarket = new JLabel(), lbOpCo = new JLabel(), lbComplexity = new JLabel(), lbApproval = new JLabel(), lbStart = new JLabel(), lbFinish = new JLabel(), lbDate = new JLabel();
	JLabel lbPitCode = new JLabel(), lbMainProject = new JLabel();
	JTextField tfName = new JTextField(30), tfApproval = new JTextField(11), tfStart = new JTextField(15), tfFinish = new JTextField(15), tfDate = new JTextField(11);
	JComboBox cbCategory, cbBrand, cbEndMarket , cbOpCo, cbPitCode, cbMainProject, cbComplexity = new JComboBox(new String[] {"","Cap1","Cap2","Cap3"});
	
	private List<AuxiliarData> mListCategory = new ArrayList<AuxiliarData>();
	private List<AuxiliarData> mListEndMarket = new ArrayList<AuxiliarData>();
	private List<AuxiliarData> mListOpCo = new ArrayList<AuxiliarData>();
	private List<AuxiliarData> mListPitCode = new ArrayList<AuxiliarData>();
	private List<AuxiliarData> mListMainProject = new ArrayList<AuxiliarData>();
	private List<Brand> mListBrand = new ArrayList<Brand>();
	
	public PanelProjectList(final People loggedUser){
		
		this.setVisible(true);
		
		JPanel InputPanel = new JPanel(new GridBagLayout());
		JPanel MainPanel = new JPanel(new GridBagLayout()); 
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JScrollPane scrollPane = new JScrollPane(mTable);
		scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		
		//
		lbName.setText("Name: ");
		lbCategory.setText(" Category: ");
		lbBrand.setText("Brand: ");
		lbEndMarket.setText(" End Market: ");
		lbOpCo.setText(" OpCo: ");
		lbComplexity.setText("Complexity: ");		
		lbApproval.setText(" Approval: ");
		lbStart.setText(" Start Date: ");
		lbFinish.setText("Finish Date: ");
		lbDate.setText(" Created On: ");
		
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
		c.ipadx = 15;
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
		InputPanel.add(cbCategory,c);
		c.gridwidth = 1;
		c.gridx = 5;			
		
		c.gridy = 1;
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
		
		c.gridy = 2;
		c.gridx = 0;
		InputPanel.add(lbComplexity,c);
		c.gridx = 1;		
		InputPanel.add(cbComplexity,c);
		c.gridx = 2;
		InputPanel.add(lbApproval,c);
		c.gridx = 3;
		InputPanel.add(tfApproval,c);
		c.gridx = 4;
		InputPanel.add(lbStart,c);
		c.gridx = 5;
		c.gridwidth = 1;
		InputPanel.add(tfStart,c);
		c.gridwidth = 1;
		
		c.gridy = 3;
		c.gridx = 0;
		InputPanel.add(lbFinish,c);
		c.gridx = 1;
		InputPanel.add(tfFinish,c);
		c.gridx = 2;
		InputPanel.add(lbDate,c);
		c.gridx = 3;
		c.gridwidth = 2;
		InputPanel.add(tfDate,c);
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
		c.gridwidth = 5;
		c.ipady = 0;
		c.gridy = 1;
		c.gridx = 0;
		scrollPane.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder(),
				"List of Projects",
                TitledBorder.LEFT,
                TitledBorder.TOP));
		MainPanel.add(scrollPane,c);
		
		
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
		
		//button Load
		btnLoadData.setPreferredSize(new Dimension(150,50));
		btnLoadData.setVerticalTextPosition(SwingConstants.CENTER);
		btnLoadData.setHorizontalTextPosition(SwingConstants.CENTER);
		ImageIcon imgLoad = new ImageIcon(getClass().getClassLoader().getResource("resources/btnRefresh.png"));
		btnLoadData.setBorder(BorderFactory.createEmptyBorder());
		btnLoadData.setContentAreaFilled(false);
		btnLoadData.setIcon(imgLoad);
		btnLoadData.setToolTipText("Refresh List");		
		btnLoadData.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){

				DatabaseConnection mData = Planning.OpenConnection();	
				
				List<Project> mListProject;
				if(loggedUser.getLogin().getPermissionLevel() > 0){
					mListProject = ProjectSqlAdapter.selectProject(mData, loggedUser);
				}else{
					mListProject = ProjectSqlAdapter.selectProject(mData);
				}

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
					mTable.setValueAt(p.getCategory().getName(), line, column);
					column++;
					mTable.setValueAt(p.getBrand().getName(), line, column);
					column++;
					mTable.setValueAt(p.getOpco().getName(), line, column);
					column++;
					mTable.setValueAt(p.getEndMarket().getName(), line, column);
					column++;
					mTable.setValueAt(p.getComplexity(), line, column);
					column++;
					mTable.setValueAt(p.isApproval(), line, column);
					column++;
					mTable.setValueAt(p.getStart(), line, column);
					column++;
					mTable.setValueAt(p.getFinish(), line, column);
					column++;
					mTable.setValueAt(p.getDate(), line, column);
					
					line++;
				}
				mData.closeConnection();
			}			
		});		
				
		//button Delete
		btnDeleteData.setPreferredSize(new Dimension(150,50));
		btnDeleteData.setVerticalTextPosition(SwingConstants.CENTER);
		btnDeleteData.setHorizontalTextPosition(SwingConstants.CENTER);
		ImageIcon imgDelete = new ImageIcon(getClass().getClassLoader().getResource("resources/btnDelete.png"));
		btnDeleteData.setIcon(imgDelete);
		btnDeleteData.setBorder(BorderFactory.createEmptyBorder());
		btnDeleteData.setContentAreaFilled(false);
		btnDeleteData.setToolTipText("Delete Project");
		btnDeleteData.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				DatabaseConnection mData = Planning.OpenConnection();
				int selectedRow = mTable.getSelectedRow();
				
				if(selectedRow > -1){
					int option = JOptionPane.showConfirmDialog(getRootPane(), "Are you sure?");
					
					if(option == JOptionPane.YES_OPTION){
						String pid = mTable.getValueAt(selectedRow, 0).toString();						
						Project newProject = new Project();
						newProject.setProjectCode(pid);
						boolean projectDeleted = ProjectSqlAdapter.deleteProject(mData, newProject);
						if(projectDeleted){
							JOptionPane.showMessageDialog(getRootPane(), "Project Deleted Successfuly.");
						}else{
							JOptionPane.showMessageDialog(getRootPane(), "Error!");
						}
					}
				}
				btnLoadData.doClick();
				mData.closeConnection();
			}
		});
		
		//button Search
		btnSearch.setPreferredSize(new Dimension(150,50));
		btnSearch.setVerticalTextPosition(SwingConstants.CENTER);
		btnSearch.setHorizontalTextPosition(SwingConstants.CENTER);
		ImageIcon imgSearch = new ImageIcon(getClass().getClassLoader().getResource("resources/btnSearch.png"));
		btnSearch.setIcon(imgSearch);
		btnSearch.setBorder(BorderFactory.createEmptyBorder());
		btnSearch.setContentAreaFilled(false);
		btnSearch.setToolTipText("Search Project");
		btnSearch.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				DatabaseConnection mData = Planning.OpenConnection();	
				List<Project> mListProject = ProjectSqlAdapter.selectProject(mData);

				int index;
				for(int row = mModel.getRowCount() - 1; row >= 0 ; row--)
					mModel.removeRow(row);				
				
				//Filter	
			    for (Iterator<Project> i = mListProject.iterator(); i.hasNext(); )  
			    {  
			        Project value = i.next();  
			        
			        //NULL TESTS
			        if( tfName.getText().length() > 0 && value.getName() == null){
			        	i.remove();
			        	continue;
			        }
			        if( cbCategory.getSelectedItem().toString().length() > 0 && value.getCategory() == null) {
			        	i.remove();
			        	continue;
			        }
			        if( cbBrand.getSelectedIndex() > 0 && value.getBrand() == null){
			        	i.remove();
			        	continue;
			        }
			        if( cbOpCo.getSelectedIndex() > 0 && value.getOpco() == null) {
			        	i.remove();
			        	continue;
			        }
			        if( cbEndMarket.getSelectedIndex() > 0 && value.getEndMarket() == null) {
			        	i.remove();
			        	continue;
			        }
			        if( cbComplexity.getSelectedItem().toString().length() > 0 && value.getComplexity() == null) {
			        	i.remove();
			        	continue;
			        }		        
			        if( tfStart.getText().length() > 0 && value.getStart() == null) {
			        	i.remove();
			        	continue;
			        }
			        if( tfFinish.getText().length() > 0 && value.getFinish() == null) {
			        	i.remove();
			        	continue;
			        }
			        if( tfDate.getText().length() > 0 && value.getDate() == null){
			        	i.remove();
			        	continue;
			        }		        
			        
			        // VALUES TESTS
			        if( tfName.getText().length() > 0 && value.getName() != null){
			        	if( value.getName().contains(tfName.getText()) == false){
			            	i.remove();
			            	continue;
			        	}
			        } 
			        if( cbCategory.getSelectedIndex() > 0 && value.getCategory() != null){ 
			        	index = cbCategory.getSelectedIndex() - 1;			        	
			        	if(value.getCategory().getName().equals(mListCategory.get(index).getName()) == false){
			        		i.remove(); 
			        		continue;
			        	}
			        }  
			        if( cbBrand.getSelectedIndex() > 0 && value.getBrand() != null){
			        	index = cbBrand.getSelectedIndex() - 1;	
			        	if(value.getBrand().getName().equals(mListBrand.get(index).getName()) == false){
			        		i.remove();
			        		continue;
			        	}
			        } 
			        if( cbOpCo.getSelectedIndex() > 0 && value.getOpco() != null){
			        	index = cbOpCo.getSelectedIndex() - 1;	
			        	if(value.getOpco().getName().equals(mListOpCo.get(index).getName()) == false){
			        		i.remove();
			        		continue;
			        	}
			        } 
			        if( cbEndMarket.getSelectedIndex() > 0 && value.getEndMarket() != null){
			        	index = cbEndMarket.getSelectedIndex() - 1;	
			        	if(value.getEndMarket().getName().equals(mListEndMarket.get(index).getName()) == false){
			        		i.remove();
			        		continue;
			        	}
			        } 
			        if( cbComplexity.getSelectedItem().toString().length() > 0 && value.getComplexity() != null){
			        	if(value.getComplexity().equals(Project.ProjectComplexity.valueOf(cbComplexity.getSelectedItem().toString().toUpperCase())) == false){
			        		i.remove();
			        		continue;
			        	}
			        } 
			        if( tfApproval.getText().length() > 0){
			        	if(value.isApproval() != Boolean.parseBoolean(tfApproval.getText())){
			        		i.remove();
			        		continue;
			        	}
			        } 
			        if( tfStart.getText().length() > 0 && value.getStart() != null){
			        	if(value.getStart().toString().contains(tfStart.getText()) == false){
			        		i.remove();
			        		continue;
			        	}
			        }
			        if( tfFinish.getText().length() > 0 && value.getFinish() != null){
			        	if(value.getFinish().toString().contains(tfFinish.getText()) == false){
			        		i.remove();
			        		continue;
			        	}
			        }
			        if( tfDate.getText().length() > 0 && value.getDate() != null){
			        	if(value.getDate().toString().contains(tfDate.getText()) == false){
			        		i.remove();
			        		continue;
			        	}
			        }
			    }								
				
			    // POPULATE TABLE
				int line = 0, column = 0;
				for(Project p:mListProject){					
					mModel.addRow(new Object[]{});
					column = 0;
					mTable.setValueAt(p.getProjectCode(), line, column);
					column++;
					mTable.setValueAt(p.getName(), line, column);
					column++;
					mTable.setValueAt(p.getCategory().getName(), line, column);
					column++;
					mTable.setValueAt(p.getBrand().getName(), line, column);
					column++;
					mTable.setValueAt(p.getOpco().getName(), line, column);
					column++;
					mTable.setValueAt(p.getEndMarket().getName(), line, column);
					column++;
					mTable.setValueAt(p.getComplexity(), line, column);
					column++;
					mTable.setValueAt(p.isApproval(), line, column);
					column++;
					mTable.setValueAt(p.getStart(), line, column);
					column++;
					mTable.setValueAt(p.getFinish(), line, column);
					column++;
					mTable.setValueAt(p.getDate(), line, column);
					
					line++;
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
		btnEdit.setToolTipText("Edit Project");
		btnEdit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				
				int selectedRow = mTable.getSelectedRow();				
				if(selectedRow > -1){
					String pid = mTable.getValueAt(selectedRow, 0).toString();
					//send to edition
					FrameMain fm = new FrameMain(loggedUser);
					fm.OpenPanelEdit(loggedUser, pid);
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
		btnNew.setToolTipText("Create New Project");
		btnNew.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				FrameMain fm = new FrameMain(loggedUser);
				fm.OpenPanelNew(loggedUser);								
			}
		});
				
		buttonPanel.add(btnDeleteData);	
		buttonPanel.add(btnLoadData);	
		buttonPanel.add(btnSearch);
		buttonPanel.add(btnEdit);
		buttonPanel.add(btnNew);
		c.gridwidth = 3;
		c.ipady = 0;
		c.gridy = 2;
		c.gridx = 0;		
		
		MainPanel.add(buttonPanel,c);
		this.add(MainPanel);

		btnLoadData.doClick();
	}
	
}
