package Brugerflade;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.Login_IO;

public class Kundeoversigt {
	public void start(Stage stage){
	
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		
		Button close = new Button("x");
		close.setId("close");
		grid.add(close,10,0);
		close.setOnAction(e->{
			stage.close();
		});
		
		
		
		
		Scene scene = new Scene(grid,450,400);
		stage.setScene(scene);
		scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
		}
}
