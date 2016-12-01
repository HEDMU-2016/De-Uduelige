package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchEngine {
private Connection connection;
private PreparedStatement statement;
private ResultSet resultset;
	
	public SearchEngine(Connection connection)throws SQLException{
		this.connection=connection;
	}
	public void Kontakt() throws SQLException{
		statement = connection.prepareStatement("SELECT navn FROM kunde");
		while(resultset.next()){
			String stmp = resultset.getString("navn");
			System.out.println(stmp);
		}
	}
	public void Kontakt(String s) throws SQLException{
		statement = connection.prepareStatement("SELECT navn FROM kunde");
		while(resultset.next()){
			String stmp = resultset.getString("navn");
			if(stmp.contains(s))
			System.out.println(stmp);
		}
	}
	

	
	
}
