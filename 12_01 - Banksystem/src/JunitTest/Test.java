package JunitTest;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import DB.DB;
import domain.AdminLogin;
import domain.Kunde;
import domain.Login;
import domain.NormaltLogin;

public class Test {
Connection connection;
DB db = new DB();
	@org.junit.Test
	public void test() throws SQLException {
		db.start();
		Login dennis = new AdminLogin("Dennis", "123");
		Login morten = new NormaltLogin("Morten", "watup");
		Kunde dennisKunde = new Kunde("Dennis");
		db.findKunder();
		db.findLogin();
		assertEquals (true, db.checkLogin(dennis));
		assertEquals (false, db.checkLogin(morten));
	}

}
