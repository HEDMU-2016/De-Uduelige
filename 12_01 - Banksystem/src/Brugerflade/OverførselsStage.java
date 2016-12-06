package Brugerflade;

import java.sql.SQLException;

import DB.DB;
import domain.Konto;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;



public class OverførselsStage extends Application {
	DB db = new DB();
	Stage stage;
	public void start (Stage overførselsstage){
		this.stage=overførselsstage;
		stage.setTitle("Overførsel");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Button fastOverførsel = new Button("Fast overførsel");
		grid.add(fastOverførsel, 0, 0);
		
		Label frakonto = new Label("Hæves på: ");
		grid.add(frakonto, 1, 0);
		
		TextField senderfelt = new TextField();
		grid.add(senderfelt, 1, 1);
		
		Label tilKonto = new Label("Modtager: ");
		grid.add(tilKonto, 2, 0);
		
		TextField modtagerfelt = new TextField();
		grid.add(modtagerfelt, 2, 1);
		
		Label beløb = new Label("beløb: ");
		grid.add(beløb, 3, 0);
		
		TextField beløbfelt = new TextField();
		grid.add(beløbfelt, 3, 1);
		
		Button btn = new Button("Overfør Beløb");
		grid.add(btn, 10, 10);
		
		btn.setOnAction(e ->{
			//Vi skal have 
			try {
				db.transfer(modtagerfelt.getText(),senderfelt.getText(),Double.parseDouble(beløbfelt.getText()));
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		btn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				
			}
		});
		Scene scene = new Scene(grid);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		overførselsstage.setScene(scene);
		overførselsstage.show();

	
	}
	


}