//Første skærm brugerne mødes af når de åbner banksystemen - Dan

package login;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;

import Brugerflade.AdministratorMenu;
import Brugerflade.Brugermenu;
import DB.DB;
import domain.Login;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.stage.WindowEvent;

public class Login_IO extends Application {
	DB db = new DB();

	public void start(Stage loginStage) throws SQLException {

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

		Button luk = new Button("X");
		HBox hbLuk = new HBox(10);
		hbLuk.getChildren().add(luk);
		hbLuk.setAlignment(Pos.TOP_RIGHT);
		grid.add(hbLuk, 4, 0);
		luk.setId("luk");

		luk.setOnAction(e -> {
			Alert alert = new Alert(AlertType.NONE);
			alert.setTitle("Du er ved at lukke banksystemet!");
			alert.setHeaderText(null);
			alert.setContentText("Er du sikker på at du vil lukke systemet uden at logge ind?");

			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResource("ico.png").toString()));

			ButtonType buttonTypeJa = new ButtonType("Ja, luk det!");
			ButtonType buttonTypeNej = new ButtonType("Nej, lad mig blive!");

			alert.getButtonTypes().setAll(buttonTypeNej, buttonTypeJa);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeJa) {
				loginStage.close();
			}

		});

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
			GlemtKode glemtKode = new GlemtKode();
			glemtKode.start(new Stage());
		});

		Button login = new Button("Log ind");
		HBox hbLogin = new HBox(10);
		login.setId("login");
		hbLogin.getChildren().add(login);
		hbLogin.setAlignment(Pos.BASELINE_RIGHT);
		grid.add(hbLogin, 3, 4);

		login.setOnAction(e -> {
			// Funktion ved enter tast
			fejl.setFill(Color.RED);
			System.out.println("Der blev tastet: \"" + usernameInput.getText() + "\" som brugernavn og: \""
					+ passwordInput.getText() + "\" som kodeord og trykket på log ind knappen!");

			if (usernameInput.getText().isEmpty() == false && passwordInput.getText().isEmpty() == false) {

				// MD5 kryptering
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

				try {
					boolean korrekt = db.checkLogin(usernameInput.getText().toLowerCase(), passwordInput.getText());

					if (korrekt == true) {
						Login tmplogin = db.findLogin(usernameInput.getText().toLowerCase());
						
						fejl.setText("Du er nu logget ind som: \"" + usernameInput.getText() + "\"!");
						usernameInput.setText("");
						passwordInput.setText("");
						fejl.setFill(Color.web("#184c18"));
						if (tmplogin.getId() == 2) {
							Brugermenu brugermenu = new Brugermenu();
							brugermenu.start(new Stage(), tmplogin);

						}
						if (tmplogin.getId() == 1) {
							AdministratorMenu administratormenu = new AdministratorMenu();
							administratormenu.start(new Stage(), tmplogin);
						}
						loginStage.hide();
					} else if (korrekt == false) {
						fejl.setText("Forkert brugernavn eller adgangskode!");
						passwordInput.setText("");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
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
		//loginStage.setMaximized(true);
		loginStage.initStyle(StageStyle.UNDECORATED);
		loginStage.show();
		
		loginStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
            }
        });
		usernameInput.requestFocus();

		// Lyt efter Enter tast
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					// Funktion ved enter tast
					fejl.setFill(Color.RED);
					System.out.println("Der blev tastet: \"" + usernameInput.getText() + "\" som brugernavn og: \""
							+ passwordInput.getText() + "\" som kodeord og trykket på enter knappen!");

					if (usernameInput.getText().isEmpty() == false && passwordInput.getText().isEmpty() == false) {

						// MD5 kryptering
						try {
							MessageDigest alg;
							alg = MessageDigest.getInstance("MD5");
							String password = passwordInput.getText();
							alg.reset();
							alg.update(password.getBytes());
							byte[] msgDigest = alg.digest();
							BigInteger number = new BigInteger(1, msgDigest);
							String MD5krypteret = number.toString(16);
							passwordInput.setText(MD5krypteret);
						} catch (NoSuchAlgorithmException e1) {
							e1.printStackTrace();
						}

						try {
							boolean korrekt = db.checkLogin(usernameInput.getText().toLowerCase(),
									passwordInput.getText());

							if (korrekt == true) {
								Login tmplogin = db.findLogin(usernameInput.getText().toLowerCase());
								fejl.setText("Du er nu logget ind som: \"" + usernameInput.getText() + "\"!");
								usernameInput.setText("");
								passwordInput.setText("");
								fejl.setFill(Color.web("#184c18"));
								if (tmplogin.getId() == 2) {
									Brugermenu brugermenu = new Brugermenu();
									brugermenu.start(new Stage(), tmplogin);

								}
								if (tmplogin.getId() == 1) {
									AdministratorMenu administratormenu = new AdministratorMenu();
									administratormenu.start(new Stage(), tmplogin);
								}
								loginStage.hide();
							} else if (korrekt == false) {
								fejl.setText("Forkert brugernavn eller adgangskode!");
								passwordInput.setText("");
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					} else if (usernameInput.getText().isEmpty() == true
							&& passwordInput.getText().isEmpty() == false) {
						fejl.setText("Du skal lige skrive et brugernavn!");
						passwordInput.setText("");
					} else if (usernameInput.getText().isEmpty() == false
							&& passwordInput.getText().isEmpty() == true) {
						fejl.setText("Du skal lige skrive et kodeord!");
					} else if (usernameInput.getText().isEmpty() == true && passwordInput.getText().isEmpty() == true) {
						fejl.setText("Du skal lige skrive noget i felterne!");
					}

					System.out.println("og det udskrevende resultat blev \"" + fejl.getText() + "\"\n");
				}

			}
		});
	}
}