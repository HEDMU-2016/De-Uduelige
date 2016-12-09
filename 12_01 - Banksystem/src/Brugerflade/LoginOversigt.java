package Brugerflade;

import java.sql.SQLException;

import domain.Login;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utill.TableCreator;

public class LoginOversigt {
	public void start(Stage stage) throws SQLException {
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

		Scene scene = new Scene(grid, 400, 500);
		stage.setScene(scene);
		scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}
}
