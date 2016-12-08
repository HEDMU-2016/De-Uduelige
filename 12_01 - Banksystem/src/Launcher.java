
import java.sql.SQLException;
import java.util.List;

import Brugerflade.Brugermenu;
import DB.DB;
import domain.Kunde;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

	public static void main(String[] args)throws SQLException {
		
//		DB db = new DB();
//		db.listKunder();
		launch(args);
	}

	public void start(Stage loginStage) throws Exception {
		Login_IO login_IO = new Login_IO();
		login_IO.start(loginStage);
//		Brugermenu brugermenu = new Brugermenu();
//		Stage tmpStage = new Stage();
//		brugermenu.start(tmpStage);
	}
}