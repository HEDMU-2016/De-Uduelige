package Brugerflade;

import java.sql.SQLException;

import DB.DB;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// PENIS!

public class OverførselsStage extends Application {
	DB db = new DB();
	Stage stage;
	public void start (Stage overførselsstage){
		this.stage=overførselsstage;
		stage.setTitle("Overførsel");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Button close = new Button("x");
		close.setId("close");
		grid.add(close,5,0);
		close.setOnAction(e->{
			stage.close();
		});
		
		
		CheckBox fastOverførsel = new CheckBox("Fast overførsel");
		grid.add(fastOverførsel, 0, 1);
		
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
		grid.add(btn, 4, 1);
		
		btn.setOnAction(e ->{ 
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
		Scene scene = new Scene(grid,800,400);
		overførselsstage.initStyle(StageStyle.UNDECORATED);
		scene.getStylesheets().add(getClass().getResource("Brugermenu.css").toExternalForm());
		overførselsstage.setScene(scene);
		overførselsstage.show();

	
	}
	


}