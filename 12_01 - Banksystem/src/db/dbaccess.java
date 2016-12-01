package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class dbaccess {
	static void main(String[] args) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/mydb", "SA", "");
		PreparedStatement statement = connection.prepareStatement("SELECT users");
		}
	}


