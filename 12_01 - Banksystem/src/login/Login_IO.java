package login;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Login_IO extends Application {

	public void start(Stage loginStage) {
		loginStage.setTitle("Lortebank A/S");
		loginStage.getIcons().add(new Image("login/icon.png"));
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);

		Text navn = new Text("Lortebank A/S");
		HBox hbNavn = new HBox(10);
		grid.add(hbNavn, 0, 0, 3, 1);
		hbNavn.getChildren().add(navn);
		navn.setId("logo");

		Text username = new Text("Brugernavn:");
		grid.add(username, 0, 1);
		username.setId("tekst");

		TextField usernameInput = new TextField();
		usernameInput.setPrefWidth(300);
		grid.add(usernameInput, 1, 1, 3, 1);

		Text password = new Text("Kodeord:");
		grid.add(password, 0, 2);
		password.setId("tekst");

		PasswordField passwordInput = new PasswordField();
		grid.add(passwordInput, 1, 2, 3, 2);

		Text fejl = new Text();
		HBox hbFejl = new HBox(10);
		hbFejl.getChildren().add(fejl);
		grid.add(hbFejl, 0, 3, 3, 3);
		fejl.setId("fejl");

		Button glemtkode = new Button("Jeg har glemt min kode!");
		grid.add(glemtkode, 0, 4, 2, 4);
		glemtkode.setId("glemt");

		glemtkode.setOnAction(e -> {
			fejl.setFill(Color.RED);
			fejl.setText("Det var da dumt af dig hva?");
		});

		Button login = new Button("Log ind");
		HBox hbLogin = new HBox(10);
		login.setId("login");
		hbLogin.getChildren().add(login);
		hbLogin.setAlignment(Pos.BASELINE_RIGHT);
		grid.add(hbLogin, 3, 4);

		login.setOnAction(e -> {
			fejl.setFill(Color.RED);
			if (usernameInput.getText().equals("admin") && passwordInput.getText().equals("password")) {
				fejl.setText("Damn, du er logget ind som admin!");
				usernameInput.setText("");
				passwordInput.setText("");
				fejl.setFill(Color.web("#42f453"));
			} else if (usernameInput.getText().isEmpty() == true && passwordInput.getText().isEmpty() == false) {
				fejl.setText("Du skal lige skrive et brugernavn!");
			} else if (usernameInput.getText().isEmpty() == false && passwordInput.getText().isEmpty() == true) {
				fejl.setText("Du skal lige skrive et kodeord!");
			} else if (usernameInput.getText().isEmpty() == true && passwordInput.getText().isEmpty() == true) {
				fejl.setText("Du skal lige skrive noget i felterne!");
			} else if (usernameInput.getText().isEmpty() == false && passwordInput.getText().isEmpty() == false) {
				fejl.setText("Forkert brugernavn eller adgangskode!");
			}
		});

		Scene scene = new Scene(grid, 550, 290);
		loginStage.setScene(scene);
		scene.getStylesheets().add(Login_IO.class.getResource("login.css").toExternalForm());
		loginStage.setResizable(false);
		loginStage.setAlwaysOnTop(true);
		loginStage.show();
	}

}