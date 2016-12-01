import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login_IO extends Application {
	
	public void start(Stage loginStage) {
		loginStage.setTitle("Lortebank A/S");
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);

		Text username = new Text("Brugernavn:");
		grid.add(username, 0, 0);

		TextField usernameInput = new TextField();
		grid.add(usernameInput, 1, 0, 3, 1);

		Text password = new Text("Kodeord:");
		grid.add(password, 0, 1);

		PasswordField passwordInput = new PasswordField();
		grid.add(passwordInput, 1, 1, 3, 1);
		
		Button glemtkode = new Button("Jeg har glemt min kode!");
		grid.add(glemtkode, 0, 2, 2, 2);
		glemtkode.setId("glemt");

		Button login = new Button("Log ind");
		HBox hbLogin = new HBox(10);
		login.setId("login");
		hbLogin.getChildren().add(login);
		grid.add(hbLogin, 3, 2);

		login.setOnAction(e -> {
		if(usernameInput.getText().isEmpty() == false && passwordInput.getText().isEmpty() == false){
		loginStage.hide();
		}
		else{/*Do noting*/}
		});

		Scene scene = new Scene(grid);
		loginStage.setScene(scene);
		scene.getStylesheets().add(Login_IO.class.getResource("popups.css").toExternalForm());
		loginStage.setResizable(false);
		loginStage.setAlwaysOnTop(true);
		loginStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}