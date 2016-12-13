package Brugerflade;

import java.sql.SQLException;
import java.util.List;

import DB.DB;
import domain.Konto;
import domain.Kunde;
import domain.Login;
import domain.Postering;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utill.TableCreator;

public class KontoHistorik {
	public void start(Stage stage, Login bruger) throws SQLException {
		DB db = new DB();
		TableCreator tablecreator = new TableCreator();

		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);

		stage.setTitle("Konto historik - Lortebank A/S");
		stage.getIcons().add(new Image("Brugerflade/ico.png"));

		Button close = new Button("X");
		HBox hbClose = new HBox(10);
		close.setId("close");
		hbClose.getChildren().add(close);
		hbClose.setAlignment(Pos.TOP_RIGHT);
		grid.add(hbClose, 1, 0);

		close.setOnAction(e -> {
			stage.close();
		});

		Kunde tmpkunde = db.matchkundemedlogin(bruger.getBrugernavn());
		TableView<Postering> kontohistorik = tablecreator.posteringTable(tmpkunde);
		grid.add(kontohistorik, 0, 0);
		kontohistorik.setPrefWidth(350);

		Scene scene = new Scene(grid, 400, 400);
		stage.setScene(scene);
		scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}
}
