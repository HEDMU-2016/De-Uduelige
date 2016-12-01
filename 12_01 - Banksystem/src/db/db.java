package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class db {
    static final String db = "jdbc:hsqldb:hsql://localhost/mydb";
    static final String dbuser = "SA";
    static final String dbpass = "";
    private Connection connection = null;

	public db() {
		try {
			connection = DriverManager.getConnection(db, dbuser, dbpass);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException("DataAccess not created", e);
		}
	}
	
	public void close(Connection connection) throws SQLException{
		connection.close();
	}

	Connection getConnection() {
		return connection;
	}

}
