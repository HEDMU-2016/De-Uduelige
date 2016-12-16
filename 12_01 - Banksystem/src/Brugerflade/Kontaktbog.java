package Brugerflade;

import java.sql.SQLException;

import com.sun.prism.paint.Color;

import DB.DB;
import domain.Kontakt;
import domain.Login;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import utill.TableCreator;

public class Kontaktbog {
	public void start(Stage stage, Login bruger) throws SQLException {
		DB db = new DB();
		TableCreator tablecreator = new TableCreator();

		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setAlignment(Pos.CENTER);

		stage.setTitle("Kontaktbog - Lortebank A/S");
		stage.getIcons().add(new Image("Brugerflade/ico.png"));

		Button close = new Button("X");
		HBox hbClose = new HBox(10);
		close.setId("close");
		hbClose.getChildren().add(close);
		hbClose.setAlignment(Pos.TOP_RIGHT);
		grid.add(hbClose, 3, 0);

		close.setOnAction(e -> {
			stage.close();
		});
		TableView<Kontakt> kontakttable = tablecreator.kontaktTable(bruger);
		grid.add(kontakttable, 0, 0, 1, 5);

		Label navnlabel = new Label("Navn");
		grid.add(navnlabel, 2, 1);

		TextField navnfelt = new TextField();
		navnfelt.setPrefWidth(250);
		grid.add(navnfelt, 3, 1);

		Label kontonrlabel = new Label("Kontonr");
		grid.add(kontonrlabel, 2, 2);

		TextField kontonrfelt = new TextField();
		grid.add(kontonrfelt, 3, 2);

		Text notifikation = new Text("");
		notifikation.setId("fejltext");
		grid.add(notifikation, 2, 6, 3, 6);

		Button gemknap = new Button("Tilføj kontakt");
		gemknap.setId("KnapImenu");
		grid.add(gemknap, 2, 4, 3, 4);
		gemknap.setOnAction(e -> {
	
			Kontakt nykontakt = new Kontakt(navnfelt.getText(), Integer.parseInt(kontonrfelt.getText()));
			try {
				db.addKontakt(nykontakt, bruger.getBrugernavn());
				if (kontonrfelt.getText().isEmpty()){
					
					notifikation.setText("kontonr skal være udfyldt");
				}else{
					//notifikation.setFill(Color.GREEN);
					notifikation.setText("luk og åbn din kontaktbog for at se dine opdateringer");
				}
					

			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		});

		Scene scene = new Scene(grid);
		stage.setScene(scene);
		scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				event.consume();
			}
		});
	}
}
