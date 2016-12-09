
import java.sql.SQLException;

import DB.DB;
import javafx.application.Application;
import javafx.stage.Stage;
import login.Login_IO;

public class Launcher extends Application  {

	public static void main(String[] args)throws SQLException {	
		System.out.println(System.currentTimeMillis());
		launch(args);
	}

	public void start(Stage loginStage) throws Exception {
		Login_IO login_IO = new Login_IO();
		login_IO.start(loginStage);
		
	}


}