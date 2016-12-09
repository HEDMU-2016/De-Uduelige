package Brugerflade;

import java.sql.SQLException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.Login_IO;

public class Brugermenu {
	public void start(Stage stage){

	GridPane grid = new GridPane();
	grid.setAlignment(Pos.CENTER);
	grid.setHgap(10);
	grid.setVgap(10);
	
	
	//Dette skal bruges på alle vinduer
	//
	//
	stage.setTitle("Hovedmenu - Lortebank A/S");
	stage.getIcons().add(new Image("Brugerflade/ico.png"));
	//
	//
	//Dette skal bruges på alle vinduer
	
	

	Text navn = new Text("Lortebank A/S");
	HBox hbNavn = new HBox(10);
	grid.add(hbNavn, 0, 0);
	hbNavn.getChildren().add(navn);
	navn.setId("logo");
	
	Button close = new Button("X");
	HBox hbClose = new HBox(10);
	close.setId("close");
	hbClose.getChildren().add(close);
	hbClose.setAlignment(Pos.TOP_RIGHT);
	grid.add(hbClose, 0 ,0);
	
	
	close.setOnAction(e->{
		stage.close();
	});
	
	Button kontooversigtknap = new Button("kontooversigt");
	kontooversigtknap.setId("KnapImenu");
	grid.add(kontooversigtknap, 0, 2);
	kontooversigtknap.setOnAction(e ->{
		KontoOversigt kontooversigt = new KontoOversigt();
		try {
			kontooversigt.start(new Stage());
		} catch (SQLException e1) {
		
			e1.printStackTrace();
		}
	});
	
	Button overførsel = new Button("overførsel");
	grid.add(overførsel, 0, 3);
	overførsel.setId("KnapImenu");
	overførsel.setOnAction(e ->{
		OverførselsStage overførselsstage = new OverførselsStage();
		overførselsstage.start(new Stage());
	});
	
	Button kontohistorik = new Button("kontohistorik");
	grid.add(kontohistorik, 0, 4);
	kontohistorik.setId("KnapImenu");
	kontohistorik.setOnAction(e ->{
	KontoHistorik historikvindue = new KontoHistorik();
	historikvindue.start(new Stage());
	});
	
	Button administrator = new Button("administrator");
	grid.add(administrator, 0, 5);
	administrator.setId("KnapImenu");
	administrator.setOnAction(e ->{
		AdministratorVindue administratorvindue = new AdministratorVindue();
		try {
			administratorvindue.start(new Stage());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	});
	

	Scene scene = new Scene(grid, 400, 400);
	stage.setScene(scene);
	scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
	stage.setResizable(false);
	stage.initStyle(StageStyle.UNDECORATED);
	stage.show();
	}

	
}
