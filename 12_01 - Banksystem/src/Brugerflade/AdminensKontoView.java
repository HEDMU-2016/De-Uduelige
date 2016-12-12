package Brugerflade;

import java.math.BigDecimal;
import java.sql.SQLException;

import DB.DB;
import domain.Konto;
import domain.Kunde;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
			
			Label allekontoer = new Label("Alle kontoer");
			grid.add(allekontoer, 1, 0);

			stage.setTitle("Konto historik - Lortebank A/S");
			stage.getIcons().add(new Image("Brugerflade/ico.png"));

			Button close = new Button("x");
			close.setId("close");
			grid.add(close, 0, 0);
			close.setOnAction(e -> {
				stage.close();
			});

			TableView<Konto> kompletteKontoliste = tablecreator.kontotable();
			kompletteKontoliste.autosize();
			grid.add(kompletteKontoliste, 0, 1);
			
			Label ejerlabel = new Label("ejer");
			grid.add(ejerlabel, 2, 1);
			
			TextField ejerfelt = new TextField();
			grid.add(ejerfelt, 2, 2);
			
			Label saldolabel = new Label("saldo");
			grid.add(saldolabel, 3, 1);
			
			TextField saldofelt = new TextField();
			grid.add(saldofelt, 3, 2);
			
			
			Button opret = new Button("opret");
			grid.add(opret, 4, 1);
			opret.setId("opret");
			opret.setOnAction(e->{
			try {
				db.addKonto(ejerfelt.getText(), BigDecimal.valueOf(Double.parseDouble(saldofelt.getText())));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}	
			
			});
		 
			Scene scene = new Scene(grid, 900, 400);
			stage.setScene(scene);
			scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
			stage.setResizable(false);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show(); 
		}
	}
