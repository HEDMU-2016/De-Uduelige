import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

import db.db;
import domain.AdminLogin;
import domain.Login;

public class Tester {
	@Test
	public void test()throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/mydb", "SA","");
		db db = new db(connection);
		Login dennis = new AdminLogin("Dennis", "123");
		assertEquals (true, db.checkLogin(dennis));
	}

}
