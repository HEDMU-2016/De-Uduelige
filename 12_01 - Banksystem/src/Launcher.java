
import java.sql.SQLException;

import DB.DB;
import domain.Kunde;
import javafx.application.Application;
import javafx.stage.Stage;
import login.Login_IO;

public class Launcher extends Application  {

	public static void main(String[] args)throws SQLException {
	DB db = new DB();
	Kunde dan = new Kunde("dan", "dan@gmail.com");
	db.addKunde(dan);
		
	launch(args);
	}

	public void start(Stage loginStage) throws Exception {
		Login_IO login_IO = new Login_IO();
		login_IO.start(loginStage);
		
	}


}