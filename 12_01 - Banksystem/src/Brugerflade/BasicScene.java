package Brugerflade;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BasicScene {
	// Dette skal bruges på alle vinduer
	public void start(Stage stage) {
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);

		stage.setTitle("Konto historik - Lortebank A/S");
		stage.getIcons().add(new Image("Brugerflade/ico.png"));

		Button close = new Button("x");
		close.setId("close");
		grid.add(close, 0, 0);
		close.setOnAction(e -> {
			stage.close();
		});

		Scene scene = new Scene(grid);
		stage.setScene(scene);
		scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
		
		/*Brug den her scene hvis det er et table vindue:
		
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
		*/
		
		
	}
}
