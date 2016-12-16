
import java.math.BigDecimal;
import java.sql.SQLException;

import DB.DB;
import domain.AdminLogin;
import domain.Konto;
import domain.Kunde;
import domain.Login;
import javafx.application.Application;
import javafx.stage.Stage;
import login.Login_IO;

public class launcher extends Application {
	public static void main(String[] args) throws SQLException{
		DB db = new DB();
		BigDecimal saldo = BigDecimal.valueOf(100.00);
		
		Login login = new AdminLogin("dennis","123");
		Kunde kunde = new Kunde("dennis rosenkilde","dennis@rosenkilde.nu","dennis");
		Konto konto = new Konto(kunde, saldo);
		

		
		System.out.println(System.currentTimeMillis());
		
		launch (args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Login_IO loginstage = new Login_IO();
		loginstage.start(new Stage());
	}
}
