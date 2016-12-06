package Brugerflade;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.Login_IO;

public class Brugermenu {
	public void start(Stage stage){

	GridPane grid = new GridPane();
	grid.setAlignment(Pos.CENTER);
	grid.setHgap(30);
	grid.setVgap(30);
	

	Scene scene = new Scene(grid);
	stage.setScene(scene);
	scene.getStylesheets().add(Login_IO.class.getResource("Brugermenu.css").toExternalForm());
	stage.setResizable(false);
	stage.initStyle(StageStyle.UNDECORATED);
	stage.show();
	}

	
}
