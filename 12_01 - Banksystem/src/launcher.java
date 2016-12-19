
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
		
		//Aktiver det her første gang progammet køres (efter du har indsat timeren i DBen)!
//		DB db = new DB();
//		BigDecimal saldo = BigDecimal.valueOf(100.00);
//		Login login = new AdminLogin("admin","5f4dcc3b5aa765d61d8327deb882cf99");
//		Kunde kunde = new Kunde("admin","admin@admin.dk","admin");
//		Konto konto = new Konto(kunde, saldo);
//		db.addLogin(login);
//		db.addKunde(kunde);
//		db.addKonto(konto);

		
		//System.out.println(System.currentTimeMillis());
		
		launch (args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Login_IO loginstage = new Login_IO();
		loginstage.start(new Stage());
	}
}
