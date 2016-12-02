import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import db.db;
import domain.AdminLogin;
import domain.Login;

public class Test {
boolean login;
db db;
Connection connection;
	@org.junit.Test
	public void test() throws SQLException {
		Login dennis = new AdminLogin("dennis", "123");
		db.addLogin(dennis);
		
		connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/mydb", "SA", "");
		db = new db((connection));
		assertEquals(true, db.checkLogin(dennis));
	}

}
