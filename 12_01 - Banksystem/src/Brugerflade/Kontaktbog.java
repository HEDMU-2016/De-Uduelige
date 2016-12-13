package Brugerflade;

import java.sql.SQLException;

import DB.DB;
import domain.Kontakt;
import domain.Login;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class Kontaktbog {
	public void start(Stage stage,Login bruger) throws SQLException{
		DB db = new DB();
		TableCreator tablecreator = new TableCreator();
		
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setAlignment(Pos.CENTER);

		stage.setTitle("NAVN PÅ VINDEUET - Lortebank A/S");
		stage.getIcons().add(new Image("Brugerflade/ico.png"));

		Button close = new Button("X");
		HBox hbClose = new HBox(10);
		close.setId("close");
		hbClose.getChildren().add(close);
		hbClose.setAlignment(Pos.TOP_RIGHT);
		grid.add(hbClose, 0, 0);

		close.setOnAction(e -> {
			stage.close();
		});
		TableView<Kontakt> kontakttable = tablecreator.kontaktTable(bruger);
		grid.add(kontakttable, 1, 1);
		
		Label navnlabel = new Label("navn");
		grid.add(navnlabel, 1, 8);
		
		TextField navnfelt = new TextField();
		grid.add(navnfelt, 1, 9);
		
		Label kontonrlabel = new Label("kontonr");
		grid.add(kontonrlabel, 2, 8);
		
		TextField kontonrfelt = new TextField();
		grid.add(kontonrfelt, 2, 9);
		
		Text notifikation = new Text();
		grid.add(notifikation, 4, 9);
		
		
		
		Button gemknap = new Button("tilfør kontakt");
		grid.add(gemknap, 3, 8);
		gemknap.setOnAction(e->{
		Kontakt nykontakt = new Kontakt(navnfelt.getText(),Integer.parseInt(kontonrfelt.getText()));
		try {
			db.addKontakt(nykontakt, bruger.getBrugernavn());
			if(kontonrfelt.getText().isEmpty())
			notifikation.setText("kontonr skal være udfyldt");	
			else
			notifikation.setText("luk og åbn din kontaktbog for at se dine opdateringer");
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		});
		
		Scene scene = new Scene(grid, 400, 400);
		stage.setScene(scene);
		scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}
}
