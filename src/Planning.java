
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;


public class Planning {

			
	public static void main(String args[]){
		
		String URL = "jdbc:mysql://localhost:3306/Planning";
		String login = "root";
		String pass = "root";
		
		DatabaseConnection mData = new DatabaseConnection(URL, login, pass);		
		mData.openConnection();
		
		if(!mData.isConnected()){
			JOptionPane.showMessageDialog(null, "Database Not Found.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}		
		
		
		FrameLogin SystemLogin = new FrameLogin();
		SystemLogin.Show();
		boolean logged = SystemLogin.Log(mData);		
		if(logged){
			System.out.println("Logged");
			//FrameSearch newFrame = new FrameSearch();
			//newFrame.setVisible(true);
			
			System.out.println("---Teste Classes---");
			
			Project mProject = new Project();
			mProject.setName("Projeto 3");
			mProject.setCategory("CONSUMER");
			mProject.setBrand("Lucky Strike Tepic");
			mProject.setComplexity("2");
			mProject.setApproval(false);
			java.util.Date date = new java.util.Date();
			mProject.setDate(new Timestamp(date.getTime()));
			
			ProjectSqlAdapter mProjectSqlAdapter = new ProjectSqlAdapter();
			
			//mProjectSqlAdapter.InsertProject(mData, mProject);
			mProject.setId("1");
			//mProjectSqlAdapter.DeleteProject(mData, mProject);
			mProject.setCategory("BAU");
			mProjectSqlAdapter.UpdateProject(mData, mProject);
			
			List<Project> mListProject = new ArrayList<Project>();			
			mListProject = mProjectSqlAdapter.SelectProject(mData);			
			for(Project p:mListProject){
				System.out.println("Project Code: " + p.getProjectCode() + " Name: " + p.getName() + " Category: " + p.getCategory() + " Brand: " + p.getBrand());
			}
			
			//Resource
			ResourceAllocation mResourceAllocation = new ResourceAllocation(null, null, 0);
			Resource newResource = new Resource();
			
			newResource.setName("Sampling");
			List<Resource> mListResource = new ArrayList<Resource>();
			mListResource = mResourceAllocation.SelectResource(mData, newResource);
			
			newResource = mListResource.get(0);
			
			mResourceAllocation.setResource(newResource);
			mResourceAllocation.setProject(mProject);
			mResourceAllocation.setQuantity(500);
			
			mResourceAllocation.InsertResourceAllocation(mData, mResourceAllocation);
			
			List<ResourceAllocation> mListResourceAlloc = new ArrayList<ResourceAllocation>();
			mListResourceAlloc = mResourceAllocation.SelectResourceAllocation(mData, mProject);
			for(ResourceAllocation ra:mListResourceAlloc){
				System.out.println("Resource: " + ra.getResource().getName());
			}
			
			System.out.println("---Fim dos testes---");			
		}
		
		mData.closeConnection();		
	}	
}
