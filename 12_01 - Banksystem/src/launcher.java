
import java.math.BigDecimal;
import java.sql.SQLException;

import DB.DB;
import domain.Konto;

import java.sql.SQLException;

import DB.DB;

import domain.Kunde;
import javafx.application.Application;
import javafx.stage.Stage;
import login.Login_IO;

public class launcher extends Application {
	public static void main(String[] args) throws SQLException{
		System.out.println(System.currentTimeMillis());
		launch (args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Login_IO loginstage = new Login_IO();
		loginstage.start(new Stage());
		
	}
}
