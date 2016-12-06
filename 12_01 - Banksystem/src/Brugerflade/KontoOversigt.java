package Brugerflade;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.Login_IO;

public class KontoOversigt {
	public void start(Stage stage){
		stage = new Stage();
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		
		
		Scene scene = new Scene(grid);
		stage.setScene(scene);
		scene.getStylesheets().add(Login_IO.class.getResource("login.css").toExternalForm());
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
		}
	
}
