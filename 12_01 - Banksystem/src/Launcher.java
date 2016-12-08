
import Brugerflade.Brugermenu;
import javafx.application.Application;
import javafx.stage.Stage;
import login.Login_IO;

public class Launcher extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage loginStage) throws Exception {
		Login_IO login_IO = new Login_IO();
//		Brugermenu brugermenu = new Brugermenu();
//		Stage tmpStage = new Stage();
		login_IO.start(loginStage);
//		brugermenu.start(tmpStage);
	}
}