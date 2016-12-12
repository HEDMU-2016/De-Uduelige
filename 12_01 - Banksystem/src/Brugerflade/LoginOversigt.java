package Brugerflade;

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
		grid.add(hbClose, 1, 0);

		close.setOnAction(e -> {
			stage.close();
		});

		TableView<Login> loginoversigt = tablecreator.logintable();
		loginoversigt.setPrefWidth(350);
		loginoversigt.getSelectionModel().setCellSelectionEnabled(true);
		loginoversigt.setEditable(true);
		grid.add(loginoversigt, 0, 1, 2, 2);

		Label brugernavnlabel = new Label("brugernavn");
		grid.add(brugernavnlabel, 2, 1);
		
		TextField brugernavnfelt = new TextField();
		grid.add(brugernavnfelt, 2, 2);
		
		Label passwordlabel = new Label("password");
		grid.add(passwordlabel, 3, 1);
		
		TextField passwordfelt = new TextField();
		grid.add(passwordfelt, 3, 2);
		
		Label idlabel = new Label("id");
		grid.add(idlabel, 4, 1);
		
		
		ObservableList<String> options = FXCollections.observableArrayList("admin","kunde");
		
		final ComboBox idfeltoptions = new ComboBox(options);
		HBox hbidfelt = new HBox();
		hbidfelt.getChildren().add(idfeltoptions);
		idfeltoptions.setPrefWidth(175);
		grid.add(hbidfelt, 4, 2);
		
		
		
		Button opret = new Button("opret");
		grid.add(opret, 5, 1);
		opret.setId("opret");
		opret.setOnAction(e->{
		try {
			if(idfeltoptions.getValue().equals("admins")){
			db.addLogin(new AdminLogin(brugernavnfelt.getText(), passwordfelt.getText()));
			}
			if(idfeltoptions.getValue().equals("kunde")){
			Login tmplogin = new NormaltLogin(brugernavnfelt.getText(), passwordfelt.getText());
			db.addLogin(tmplogin);
				}
			if(idfeltoptions.getPromptText().equals(null)){
			System.out.println("jeg registrede ikke noget id");
			}
			else System.out.println(idfeltoptions.getPromptText());
		
		} catch (SQLException e1) {
			e1.printStackTrace();
		}	
		
		});
	 
		
		Scene scene = new Scene(grid, 900, 500);
		stage.setScene(scene);
		scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}
}
