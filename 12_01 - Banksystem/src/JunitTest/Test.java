package JunitTest;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import DB.DB;
import domain.AdminLogin;
import domain.Konto;
import domain.Kunde;
import domain.Login;
import domain.NormaltLogin;

public class Test {
Connection connection;
DB db = new DB();
	@org.junit.Test
	public void test() throws SQLException {
		Login dennissLogin = new AdminLogin("Dennis", "123");
		Login morten = new NormaltLogin("Morten", "watup");
		Kunde dennis = new Kunde("dennis");
		Konto testkonto = new Konto(dennis,"12345678");
		db.addKonto("dennis", 100.00D);
		
		db.findKunder();
		db.findLogin();
		assertEquals (true, db.checkLogin(dennissLogin.getBrugernavn(), dennissLogin.getAdgangskode()));
		assertEquals (false, db.checkLogin(morten.getBrugernavn(), morten.getAdgangskode()));
		assertEquals (1, db.getLoginID(dennissLogin.getBrugernavn(), dennissLogin.getAdgangskode()));
		assertEquals (100.00, db.getSaldo("dennis"));
	
	}

}
