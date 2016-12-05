import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;

import domain.AdminLogin;
import domain.Login;

public class Test {

	@org.junit.Test
	public void test() {
		Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/mydb", "SA","");
		db db = new db(connection);
		Login dennis = new AdminLogin("Dennis", "123");
		assertEquals (true, db.checkLogin(dennis));
	}

}
