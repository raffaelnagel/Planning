package planning.DataAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import planning.Data.AuxiliarData;

public class AuxiliarDataSqlAdapter {
	
	public static AuxiliarData selectAuxiliarData(DatabaseConnection mConnection, AuxiliarData.AuxiliarDataTypes type, String idAuxiliarData){
		
		if(mConnection.isConnected()){
			try {
								
				Statement mStatement = mConnection.getConnection().createStatement();			
		
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM " + type.toString() + " WHERE " + "id" + type.toString() + "= '" + idAuxiliarData + "'");
				
				try {					
					List<AuxiliarData> mListAuxiliarData = new ArrayList<AuxiliarData>();
					while(queryResult.next()){
						
						AuxiliarData mAuxiliarData = new AuxiliarData();
						mAuxiliarData.setId(queryResult.getString("id" + type.toString()));	
						mAuxiliarData.setName(queryResult.getString("Name"));
						mAuxiliarData.setType(type);
						mListAuxiliarData.add(mAuxiliarData);
					}
					return mListAuxiliarData.get(0);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}
		
		return null;
	}
	
	public static List<AuxiliarData> selectAuxiliarData(DatabaseConnection mConnection, AuxiliarData.AuxiliarDataTypes type){
		
		if(mConnection.isConnected()){
			try {
								
				Statement mStatement = mConnection.getConnection().createStatement();			
		
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM " + type.toString() );
				
				try {					
					List<AuxiliarData> mListAuxiliarData = new ArrayList<AuxiliarData>();
					while(queryResult.next()){
						
						AuxiliarData mAuxiliarData = new AuxiliarData();
						mAuxiliarData.setId(queryResult.getString("id" + type.toString()));	
						mAuxiliarData.setName(queryResult.getString("Name"));
						mAuxiliarData.setType(type);
						mListAuxiliarData.add(mAuxiliarData);
					}
					return mListAuxiliarData;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}
		
		return null;
	}
}
