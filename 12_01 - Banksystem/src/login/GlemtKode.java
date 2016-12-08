package login;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import Brugerflade.Brugermenu;
import DB.DB;
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
import javafx.stage.StageStyle;

public class GlemtKode {
	public void start(Stage glemtStage) {
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		glemtStage.setTitle("Glemt kode - Lortebank A/S");
		glemtStage.getIcons().add(new Image("login/ico.png"));

		Text navn = new Text("Lortebank A/S");
		HBox hbNavn = new HBox(10);
		grid.add(hbNavn, 0, 0, 2, 1);
		hbNavn.getChildren().add(navn);
		navn.setId("logo");

		Button luk = new Button("X");
		HBox hbLuk = new HBox(10);
		hbLuk.getChildren().add(luk);
		hbLuk.setAlignment(Pos.TOP_RIGHT);
		grid.add(hbLuk, 3, 0);
		luk.setId("luk");

		luk.setOnAction(e -> {
			glemtStage.close();
		});

		Text username = new Text("Brugernavn:");
		grid.add(username, 0, 1);
		username.setId("tekst");

		TextField usernameInput = new TextField();
		usernameInput.setPrefWidth(275);
		grid.add(usernameInput, 1, 1);

		Text password = new Text("Nyt kodeord:");
		grid.add(password, 0, 2);
		password.setId("tekst");

		PasswordField passwordInput = new PasswordField();
		grid.add(passwordInput, 1, 2);

		Text masterPassword = new Text("Master kodeord:");
		grid.add(masterPassword, 0, 3);
		masterPassword.setId("tekst");

		PasswordField masterPasswordInput = new PasswordField();
		grid.add(masterPasswordInput, 1, 3);

		Text fejl = new Text();
		HBox hbFejl = new HBox(10);
		hbFejl.getChildren().add(fejl);
		grid.add(hbFejl, 0, 4, 3, 4);
		fejl.setId("fejl");

		Button glemt = new Button("✓");
		HBox hbGlemt = new HBox(10);
		glemt.setId("login");
		hbGlemt.getChildren().add(glemt);
		grid.add(hbGlemt, 2, 1, 2, 3);

		glemt.setOnAction(e -> {
			fejl.setFill(Color.RED);
			if (masterPasswordInput.getText().isEmpty() == false && passwordInput.getText().isEmpty() == false
					&& usernameInput.getText().isEmpty() == false) {
				if (masterPasswordInput.getText().equals("password") == true) {

					// MD5 Kryptering på ny kode
					try {
						MessageDigest alg;
						alg = MessageDigest.getInstance("MD5");
						String password1 = passwordInput.getText();
						alg.reset();
						alg.update(password1.getBytes());
						byte[] msgDigest = alg.digest();
						BigInteger number = new BigInteger(1, msgDigest);
						String MD5krypteret = number.toString(16);
						passwordInput.setText(MD5krypteret);
					} catch (NoSuchAlgorithmException e1) {
						e1.printStackTrace();
					}

					// Her skal den nye kode sendes til DBen
					DB db = new DB();
					try {
						fejl.setText(db.nyKode(usernameInput.getText().toLowerCase(), passwordInput.getText()));
						if (fejl.getText().equals("Dit kodeord er nu opdateret - og du kan logge ind!")) {
							fejl.setFill(Color.GREEN);
							passwordInput.setText("");
							masterPasswordInput.setText("");
						} else {
							
						}

					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				} else if (masterPasswordInput.getText().equals("password") == false) {
					fejl.setText("Master kodeord forkert, HINT: det er ikke \"password\"");
				}
			} else {
				fejl.setText("Alle felterne skal udfyldes!");
			}
		});

		Scene scene = new Scene(grid);
		glemtStage.setScene(scene);
		scene.getStylesheets().add(Login_IO.class.getResource("login.css").toExternalForm());
		glemtStage.setResizable(false);
		glemtStage.initStyle(StageStyle.UNDECORATED);
		glemtStage.show();

		usernameInput.requestFocus();

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					fejl.setFill(Color.RED);
					if (masterPasswordInput.getText().isEmpty() == false && passwordInput.getText().isEmpty() == false
							&& usernameInput.getText().isEmpty() == false) {
						if (masterPasswordInput.getText().equals("password") == true) {

							// MD5 Kryptering på ny kode
							try {
								MessageDigest alg;
								alg = MessageDigest.getInstance("MD5");
								String password1 = passwordInput.getText();
								alg.reset();
								alg.update(password1.getBytes());
								byte[] msgDigest = alg.digest();
								BigInteger number = new BigInteger(1, msgDigest);
								String MD5krypteret = number.toString(16);
								passwordInput.setText(MD5krypteret);
							} catch (NoSuchAlgorithmException e1) {
								e1.printStackTrace();
							}

							// Her skal den nye kode sendes til DBen
							DB db = new DB();
							try {
								fejl.setText(db.nyKode(usernameInput.getText().toLowerCase(), passwordInput.getText()));
								if (fejl.getText().equals("Dit kodeord er nu opdateret - og du kan logge ind!")) {
									fejl.setFill(Color.GREEN);
									passwordInput.setText("");
									masterPasswordInput.setText("");
								} else {
									
								}

							} catch (SQLException e1) {
								e1.printStackTrace();
							}

						} else if (masterPasswordInput.getText().equals("password") == false) {
							fejl.setText("Master kodeord forkert, HINT: det er ikke \"password\"");
						}
					} else {
						fejl.setText("Alle felterne skal udfyldes!");
					}

				}
			}
		});
	}
}
