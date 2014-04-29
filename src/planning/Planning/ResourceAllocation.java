package planning.Planning;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ResourceAllocation {
	
	private Resource resource;
	private Project project;
	private float quantity;
	
	public ResourceAllocation(Resource resource, Project project, float quantity){
		this.setResource(resource);
		this.setProject(project);
		this.setQuantity(quantity);
	}
	
	public Resource getResource() {
		return resource;
	}
	
	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	
	public List<ResourceAllocation> SelectResourceAllocation(DatabaseConnection mConnection, Project mProject){
		
		if(mConnection.isConnected()){
			try{
				Statement mStatement = mConnection.getConnection().createStatement();			
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM ResourceAllocation WHERE idProject = '" + mProject.getId() + "'");
				
				try {					
					List<ResourceAllocation> mListResourceAllocation = new ArrayList<ResourceAllocation>();
					while(queryResult.next()){
						
						Resource newResource = new Resource();
						newResource.setId(queryResult.getString("idResource"));						
						
						Statement mStatement1 = mConnection.getConnection().createStatement();		
						ResultSet queryResult1 = mStatement1.executeQuery("SELECT * FROM Resource WHERE idResource = '" + newResource.getId() + "'");
						if(queryResult1.next()){
							newResource.setName(queryResult1.getString("Name"));						
							newResource.setType(queryResult1.getString("Type"));
						}	
						ResourceAllocation mResourceAllocation = new ResourceAllocation(newResource, mProject, -1);
						mResourceAllocation.setQuantity(queryResult.getFloat("Quantity"));
						
						mListResourceAllocation.add(mResourceAllocation);
					
					}
					return mListResourceAllocation;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			}catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public List<Resource> SelectResource(DatabaseConnection mConnection, Resource mResource){
		
		if(mConnection.isConnected()){
			try{
				Statement mStatement = mConnection.getConnection().createStatement();			
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM Resource WHERE Name = '" + mResource.getName() + "'");
				
				try {					
					List<Resource> mListResource = new ArrayList<Resource>();
					while(queryResult.next()){
						
						Resource newResource = new Resource();
						newResource.setId(queryResult.getString("idResource"));
						newResource.setName(queryResult.getString("Name"));
						newResource.setType("Type");
						mListResource.add(newResource);
					}
					return mListResource;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			}catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public boolean InsertResource(DatabaseConnection mConnection, Resource mResource){
		if(mConnection.isConnected()){			
			try {
				
				PreparedStatement mPreparedStatement;
				mPreparedStatement = mConnection.getConnection().prepareStatement("INSERT INTO Resource(Name, Type) VALUES(?,?)");
				mPreparedStatement.setString(1, mResource.getName());
				mPreparedStatement.setString(2, mResource.getType());
				mPreparedStatement.executeUpdate();
				
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} 								
				
		}
		return false;
	}
	
	public boolean InsertResourceAllocation(DatabaseConnection mConnection, ResourceAllocation mResourceAllocation){
		if(mConnection.isConnected()){			
			try {
				
				Project mProject = mResourceAllocation.getProject();
				Resource mResource = mResourceAllocation.getResource();
				
				PreparedStatement mPreparedStatement;
				mPreparedStatement = mConnection.getConnection().prepareStatement("INSERT INTO ResourceAllocation(idProject, idResource, Quantity) VALUES(?,?,?)");
				mPreparedStatement.setString(1, mProject.getId());
				mPreparedStatement.setString(2, mResource.getId());
				mPreparedStatement.setFloat(3, mResourceAllocation.getQuantity());
				mPreparedStatement.executeUpdate();
				
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} 								
				
		}
		return false;
	}
}
