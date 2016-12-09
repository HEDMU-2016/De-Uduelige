package Brugerflade;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.Login_IO;

public class Redigermedarbejder {
	public void start(Stage stage){
	
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		stage.setTitle("Konto historik - Lortebank A/S");
		stage.getIcons().add(new Image("Brugerflade/ico.png"));

		
		//Dette skal bruges på alle vinduer
		//
		//
		stage.setTitle("Rediger medarbejder - Lortebank A/S");
		stage.getIcons().add(new Image("Brugerflade/ico.png"));
		//
		//
		//Dette skal bruges på alle vinduer
		
		
		Button close = new Button("X");
		close.setId("close");
		grid.add(close,0,0);
		close.setOnAction(e->{
			stage.close();
		});
		
		
		
		
		Scene scene = new Scene(grid,400,400);
		stage.setScene(scene);
		scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
		}
}
