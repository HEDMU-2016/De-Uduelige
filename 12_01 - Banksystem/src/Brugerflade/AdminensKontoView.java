package Brugerflade;

import java.sql.SQLException;

import DB.DB;
import domain.Konto;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
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
			final VBox vbox = new VBox();
			vbox.setSpacing(5);
			vbox.setPadding(new Insets(10, 0, 0, 10));
			vbox.getChildren().addAll(allekontoer, kompletteKontoliste);
			Scene scene = new Scene(new Group(), 450, 400);
			 ((Group) scene.getRoot()).getChildren().addAll(vbox);
		
		 
			stage.setScene(scene);
			scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
			stage.setResizable(false);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show(); 
		}
	}
