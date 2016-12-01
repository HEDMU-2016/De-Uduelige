import javafx.stage.Stage;
import login.Login_IO;

public class Launcher {

	public void start(Stage loginStage) throws Exception {
		Login_IO login_IO = new Login_IO();
		login_IO.start(loginStage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}

//EN ELLER ANDEN FIX DET HER LORT, JEG KAN IKKE FÅ DET TIL AT VIRKE