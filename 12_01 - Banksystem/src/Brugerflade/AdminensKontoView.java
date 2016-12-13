package Brugerflade;

import java.math.BigDecimal;
import java.sql.SQLException;

import DB.DB;
import domain.Konto;
import domain.Kunde;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utill.TableCreator;

public class AdminensKontoView {

	public void start(Stage stage) throws SQLException{
			DB db = new DB();
			GridPane grid = new GridPane();
			TableCreator tablecreator = new TableCreator();

			grid.setVgap(10);
			grid.setHgap(10);
			
			stage.setTitle("Konto historik - Lortebank A/S");
			stage.getIcons().add(new Image("Brugerflade/ico.png"));
			
			Label allekontoer = new Label("Alle kontoer");
			allekontoer.setId("overskrift");
			grid.add(allekontoer, 0, 0);

			Button close = new Button("X");
			HBox hbClose = new HBox(10);
			close.setId("close");
			hbClose.getChildren().add(close);
			hbClose.setAlignment(Pos.TOP_RIGHT);
			grid.add(hbClose, 3, 0);
			
			close.setOnAction(e -> {
				stage.close();
			});

			TableView<Konto> kompletteKontoliste = tablecreator.kontotable();
			kompletteKontoliste.setPrefWidth(350);
			grid.add(kompletteKontoliste, 0, 1, 1, 5);
			
			Label ejerlabel = new Label("Ejer");
			ejerlabel.setId("tekst");
			grid.add(ejerlabel, 2, 2);
			
			TextField ejerfelt = new TextField();
			ejerfelt.setPrefWidth(250);
			grid.add(ejerfelt, 3, 2);
			
			Label saldolabel = new Label("Saldo");
			saldolabel.setId("tekst");
			grid.add(saldolabel, 2, 3);
			
			TextField saldofelt = new TextField();
			grid.add(saldofelt, 3, 3);
			
			Label fejl = new Label("");
			fejl.setId("fejl");
			grid.add(fejl, 2, 6, 3, 6);
			
			Button opret = new Button("opret");
			grid.add(opret, 2, 4, 3, 4);
			opret.setId("KnapImenu");
			opret.setOnAction(e->{
			if(ejerfelt.getText().isEmpty() == false && saldofelt.getText().isEmpty() == false)
			try {
				db.addKonto(ejerfelt.getText(), BigDecimal.valueOf(Double.parseDouble(saldofelt.getText())));
				fejl.setTextFill(Color.GREEN);
				fejl.setText("Kontoen blev oprettet!");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			else {
				fejl.setTextFill(Color.RED);
				fejl.setText("Du skal lige udfylde alle felterne!");
			}
			});
		 
			Scene scene = new Scene(grid);
			stage.setScene(scene);
			scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
			stage.setResizable(false);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show(); 
		}
	}
