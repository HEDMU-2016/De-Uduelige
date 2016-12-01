package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class dbaccess {
    static final String db = "jdbc:hsqldb:hsql://localhost/mydb";
    static final String dbuser = "SA";
    static final String dbpass = "";
    private Connection connection = null;

	public dbaccess() {
		try {
			connection = DriverManager.getConnection(db, dbuser, dbpass);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException("DataAccess not created", e);
		}
	}
	
	public void close() {
		if (this.connection != null) {
			try {
				this.connection.close();
			} catch (SQLException e) {
				throw new RuntimeException("Close failed", e);
			}
			this.connection = null;
		} else {
			throw new RuntimeException("Connection not available (null). Not closed");
		}
	}
	
	public void commit() {
		if (this.connection != null) {
			try {
				this.connection.commit();
			} catch (SQLException e) {
				throw new RuntimeException("Commit failed", e);
			}
		} else {
			throw new RuntimeException("Connection not available (null). Not committed");
		}
	}

	public void rollback() {
		if (this.connection != null) {
			try {
				this.connection.rollback();
			} catch (SQLException e) {
				throw new RuntimeException("Rollback failed", e);
			}
		} else {
			throw new RuntimeException("Connection not available (null). Not rolled back");
		}
	}

	Connection getConnection() {
		return connection;
	}

}
