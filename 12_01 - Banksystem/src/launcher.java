import java.math.BigDecimal;
import java.sql.SQLException;

import DB.DB;
import domain.Konto;
import domain.Kunde;
import javafx.application.Application;
import javafx.stage.Stage;
import login.Login_IO;

public class launcher extends Application {
	public static void main(String[] args) throws SQLException{
		double saldo=100;
		
		DB db = new DB();
		Kunde dennis = new Kunde("Dennis Rosenkilde", "dennis@rosenkilde.nu","dennis");
		Konto dennisskonto = new Konto(dennis,BigDecimal.valueOf(saldo));
		db.addKonto(dennisskonto);
		launch (args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Login_IO loginstage = new Login_IO();
		loginstage.start(new Stage());
		
	}
}
