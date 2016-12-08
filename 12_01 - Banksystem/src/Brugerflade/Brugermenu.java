package Brugerflade;

import java.sql.SQLException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.Login_IO;

public class Brugermenu {
	public void start(Stage stage){

	GridPane grid = new GridPane();
	grid.setAlignment(Pos.CENTER);
	grid.setHgap(10);
	grid.setVgap(10);
	
	stage.setTitle("Main menu");
	
	
	
	Button close = new Button("x");
	close.setId("close");
	grid.add(close,10,0);
	close.setOnAction(e->{
		stage.close();
	});
	
	Button kontooversigtknap = new Button("kontooversigt");
	grid.add(kontooversigtknap, 5, 0);
	kontooversigtknap.setOnAction(e ->{
		KontoOversigt kontooversigt = new KontoOversigt();
		try {
			kontooversigt.start(new Stage());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	});
	
	Button overførsel = new Button("overførsel");
	grid.add(overførsel, 5, 1);
	overførsel.setOnAction(e ->{
		OverførselsStage overførselsstage = new OverførselsStage();
		overførselsstage.start(new Stage());
	});
	
	Button kontohistorik = new Button("kontohistorik");
	grid.add(kontohistorik, 5, 2);
	kontohistorik.setOnAction(e ->{
	KontoHistorik historikvindue = new KontoHistorik();
	historikvindue.start(new Stage());
	});
	
	Button administrator = new Button("administrator");
	grid.add(administrator, 10, 10);
	administrator.setOnAction(e ->{
		AdministratorVindue administratorvindue = new AdministratorVindue();
		administratorvindue.start(new Stage());
	});
	

	Scene scene = new Scene(grid);
	stage.setScene(scene);
	scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
	stage.setResizable(false);
	stage.initStyle(StageStyle.UNDECORATED);
	stage.show();
	}

	
}
