package planning.Planning;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class CommentsSqlAdapter {
	
	public List<Comments> SelectComments(DatabaseConnection mConnection, Project mProject){
		
		if(mConnection.isConnected()){
			try {
				Statement mStatement = mConnection.getConnection().createStatement();			
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM Comments WHERE idProject = '" + mProject.getId() + "'");
				
				try {					
					List<Comments> mListComments = new ArrayList<Comments>();
					while(queryResult.next()){
						
						Comments mComments = new Comments(mProject, null, null);
						mComments.setId(queryResult.getString("idComments"));
						mComments.setDate(queryResult.getTimestamp("Date"));
						mComments.setComment(queryResult.getString("Comment"));
						
						mListComments.add(mComments);
					}
					return mListComments;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}
		
		return null;
	}
	
	public boolean InsertComments(DatabaseConnection mConnection, Comments mComments, Project mProject){
		if(mConnection.isConnected()){			
			try {
				
				PreparedStatement mPreparedStatement;
				mPreparedStatement = mConnection.getConnection().prepareStatement("INSERT INTO Comments(idProject, Date, Comment) VALUES(?,?,?)");
				mPreparedStatement.setString(1, mProject.getId());
				mPreparedStatement.setTimestamp(2, mComments.getDate());
				mPreparedStatement.setString(3, mComments.getComment());
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
