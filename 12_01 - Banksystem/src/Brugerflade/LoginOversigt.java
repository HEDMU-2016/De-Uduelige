package Brugerflade;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import DB.DB;
import domain.AdminLogin;
import domain.Login;
import domain.NormaltLogin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utill.TableCreator;

public class LoginOversigt {
	public void start(Stage stage) throws SQLException {
		DB db = new DB();
		TableCreator tablecreator = new TableCreator();
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setAlignment(Pos.CENTER);

		stage.setTitle("Loginoversigt - Lortebank A/S");
		stage.getIcons().add(new Image("Brugerflade/ico.png"));

		Text loginlabel = new Text("Logins");
		HBox hbloginlabel = new HBox(10);
		grid.add(hbloginlabel, 0, 0);
		hbloginlabel.getChildren().add(loginlabel);
		loginlabel.setId("overskrift");

		Button close = new Button("X");
		HBox hbClose = new HBox(10);
		close.setId("close");
		hbClose.getChildren().add(close);
		hbClose.setAlignment(Pos.TOP_RIGHT);
		grid.add(hbClose, 3, 0);

		close.setOnAction(e -> {
			stage.close();
		});

		TableView<Login> loginoversigt = tablecreator.loginTable();
		loginoversigt.setPrefWidth(350);
		// loginoversigt.getSelectionModel().setCellSelectionEnabled(true);
		// loginoversigt.setEditable(true);
		grid.add(loginoversigt, 0, 1, 1, 4);

		Label brugernavnlabel = new Label("Brugernavn: ");
		grid.add(brugernavnlabel, 2, 1);
		brugernavnlabel.setId("tekst");

		TextField brugernavnfelt = new TextField();
		grid.add(brugernavnfelt, 3, 1);

		Label passwordlabel = new Label("Kodeord:");
		grid.add(passwordlabel, 2, 2);
		passwordlabel.setId("tekst");

		TextField passwordfelt = new TextField();
		grid.add(passwordfelt, 3, 2);

		Label idlabel = new Label("ID:");
		grid.add(idlabel, 2, 3);
		idlabel.setId("tekst");

		ObservableList<String> options = FXCollections.observableArrayList("admin", "kunde");

		final ComboBox idfeltoptions = new ComboBox(options);
		HBox hbidfelt = new HBox();
		hbidfelt.getChildren().add(idfeltoptions);
		idfeltoptions.setPrefWidth(175);
		grid.add(hbidfelt, 3, 3);

		Label fejl = new Label("");
		fejl.setId("fejl");
		grid.add(fejl, 2, 5, 3, 5);

		Button opret = new Button("Opret");
		grid.add(opret, 2, 4, 3, 4);
		opret.setId("KnapImenu");
		opret.setOnAction(e -> {
			if (brugernavnfelt.getText().isEmpty() == false && passwordfelt.getText().isEmpty() == false
					&& idfeltoptions.getValue() != null)

				// MD5 Kryptering på ny kode
				try {
					MessageDigest alg;
					alg = MessageDigest.getInstance("MD5");
					String password1 = passwordfelt.getText();
					alg.reset();
					alg.update(password1.getBytes());
					byte[] msgDigest = alg.digest();
					BigInteger number = new BigInteger(1, msgDigest);
					String MD5krypteret = number.toString(16);
					passwordfelt.setText(MD5krypteret);
				
				
					if (idfeltoptions.getValue().equals("admins")) {
						db.addLogin(new AdminLogin(brugernavnfelt.getText(), passwordfelt.getText()));
					}
					if (idfeltoptions.getValue().equals("kunde")) {
						Login tmplogin = new NormaltLogin(brugernavnfelt.getText(), passwordfelt.getText());
						db.addLogin(tmplogin);
					}
					if (idfeltoptions.getPromptText().equals(null)) {
						System.out.println("jeg registrede ikke noget id");
					} else
						System.out.println(idfeltoptions.getPromptText());

				passwordfelt.setText("");
				fejl.setTextFill(Color.GREEN);
				fejl.setText("Så er loginet oprettet!");
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
			else {
				fejl.setTextFill(Color.RED);
				fejl.setText("Du skal lige udfylde alle felterne!");
			}

		});

		// grid.setGridLinesVisible(true);

		Scene scene = new Scene(grid);
		stage.setScene(scene);
		scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}
}
