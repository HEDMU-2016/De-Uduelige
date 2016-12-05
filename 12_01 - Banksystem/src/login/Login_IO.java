//Første skærm brugerne mødes af når de åbner banksystemen - Dan

package login;

import java.sql.Connection;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login_IO extends Application {

	public void start(Stage loginStage) throws SQLException {
//		DB db = new DB();
//		db.start();

		loginStage.setTitle("Log ind - Lortebank A/S");
		loginStage.getIcons().add(new Image("login/ico.png"));
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
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
		usernameInput.setPrefWidth(275);
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
			// her skal være et glemt kode vindue i stedet for
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
			System.out.println("Der blev tastet: \"" + usernameInput.getText() + "\" som brugernavn og: \""
					+ passwordInput.getText() + "\" som kodeord og trykket på log ind knappen!");

			if (usernameInput.getText().isEmpty() == false && passwordInput.getText().isEmpty() == false) {
				boolean korekt = CheckUser.check(usernameInput.getText().toLowerCase(), passwordInput.getText());

				if (korekt == true) {
					fejl.setText("Du er nu logget ind som: \"" + usernameInput.getText() + "\"!");
					usernameInput.setText("");
					passwordInput.setText("");
					fejl.setFill(Color.web("#184c18"));
				} else if (korekt == false) {
					fejl.setText("Forkert brugernavn eller adgangskode!");
					passwordInput.setText("");
				}
			} else if (usernameInput.getText().isEmpty() == true && passwordInput.getText().isEmpty() == false) {
				fejl.setText("Du skal lige skrive et brugernavn!");
				passwordInput.setText("");
			} else if (usernameInput.getText().isEmpty() == false && passwordInput.getText().isEmpty() == true) {
				fejl.setText("Du skal lige skrive et kodeord!");
			} else if (usernameInput.getText().isEmpty() == true && passwordInput.getText().isEmpty() == true) {
				fejl.setText("Du skal lige skrive noget i felterne!");
			}

			System.out.println("og det udskrevende resultat blev \"" + fejl.getText() + "\"\n");
		});

		Scene scene = new Scene(grid, 550, 280);
		loginStage.setScene(scene);
		scene.getStylesheets().add(Login_IO.class.getResource("login.css").toExternalForm());
		loginStage.setResizable(false);
		loginStage.setAlwaysOnTop(true);
		loginStage.show();

		// Lyt efter Enter tast
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					// Funktion ved enter tast
					fejl.setFill(Color.RED);
					fejl.setText("Enter funktion er på vej!");
				}
			}
		});
	}
}