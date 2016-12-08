package Brugerflade;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Redigerkunde {
	public void start(Stage stage){
	
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		
		Button rediger = new Button("Rediger Kunde");
		grid.add(rediger, 0, 0);
		
		Button opret = new Button("Opret kunde");
		grid.add(opret, 0, 1);
		
		Button slet = new Button("Slet kunde");
		grid.add(slet, 0, 2);
		
		rediger.setOnAction(e->{
			Label navn = new Label("Fulde Navn: ");
			grid.add(navn, 0, 1);
			TextField navnfelt = new TextField();
			grid.add(navnfelt, 1, 1);
			
			Label email = new Label("Email: ");
			grid.add(email, 0, 2);
			TextField emailfelt = new TextField();
			grid.add(emailfelt, 1, 2);
			
			Label fødselsdato = new Label("Fødselsdato");
			grid.add(fødselsdato, 0, 3);
			TextField fødselsdatofelt = new TextField();
			grid.add(fødselsdatofelt, 1, 3);	
		});
		

		
		
		
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
