package Brugerflade;

import java.sql.SQLException;

import DB.DB;
import domain.Kunde;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utill.TableCreator;

public class Kundeoversigt {



	public void start(Stage stage) throws SQLException {
	
		DB db = new DB();
		TableCreator tablecreator = new TableCreator();
		
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		
		stage.setTitle("Konto historik - Lortebank A/S");
		stage.getIcons().add(new Image("Brugerflade/ico.png"));


		Button close = new Button("x");
		close.setId("close");
		grid.add(close, 10, 0);
		close.setOnAction(e -> {
			stage.close();
		});

		final Label label = new Label("Kunde oversigt");
		label.setFont(new Font("arial", 20));
		
		TableView<Kunde> kundeoversigt = tablecreator.kundetable();
		
		kundeoversigt.autosize();
		kundeoversigt.getOnScroll();
		
		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, kundeoversigt);
		Scene scene = new Scene(new Group(), 450, 400);
		 ((Group) scene.getRoot()).getChildren().addAll(vbox);
	
	 
		stage.setScene(scene);
		scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();

	}
}
