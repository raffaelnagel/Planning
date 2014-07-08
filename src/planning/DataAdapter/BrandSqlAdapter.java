package planning.DataAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import planning.Data.Brand;

public class BrandSqlAdapter {
	
	public static Brand selectBrand(DatabaseConnection mConnection, String idBrand){
		
		if(mConnection.isConnected()){
			try {
								
				Statement mStatement = mConnection.getConnection().createStatement();			
		
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM Brand WHERE idBrand = '" + idBrand + "'");
				
				try {					
					List<Brand> mListBrand = new ArrayList<Brand>();
					while(queryResult.next()){
						
						Brand mBrand = new Brand();
						mBrand.setId(queryResult.getString("idBrand"));	
						mBrand.setName(queryResult.getString("Name"));
						mBrand.setCode(queryResult.getString("Code"));
						mBrand.setRange(queryResult.getString("Range"));
						mListBrand.add(mBrand);
					}
					return mListBrand.get(0);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}
		
		return null;
	}
	
	public static List<Brand> selectBrand(DatabaseConnection mConnection){
		
		if(mConnection.isConnected()){
			try {
								
				Statement mStatement = mConnection.getConnection().createStatement();			
		
				ResultSet queryResult = mStatement.executeQuery("SELECT * FROM Brand");
				
				try {					
					List<Brand> mListBrand = new ArrayList<Brand>();
					while(queryResult.next()){
						
						Brand mBrand = new Brand();
						mBrand.setId(queryResult.getString("idBrand"));	
						mBrand.setName(queryResult.getString("Name"));
						mBrand.setCode(queryResult.getString("Code"));
						mBrand.setRange(queryResult.getString("Range"));
						mListBrand.add(mBrand);
					}
					return mListBrand;
					
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
