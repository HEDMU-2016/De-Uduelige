package Brugerflade;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdministratorVindue {
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
		
		Button kundeoversigt = new Button("kundeoversigt");
		grid.add(kundeoversigt, 5, 0);
		kundeoversigt.setOnAction(e ->{
			Kundeoversigt kundeoversigt1 = new Kundeoversigt();
			kundeoversigt1.start(new Stage());
		});
		
		Button medarbejderoversigt = new Button("medarbejderoversigt");
		grid.add(medarbejderoversigt, 5, 1);
		medarbejderoversigt.setOnAction(e ->{
			Medarbejderoversigt medarbejderoversigt1 = new Medarbejderoversigt();
			medarbejderoversigt1.start(new Stage());
		});
		
		Button redigerkunde= new Button("opret/rediger/slet kunde");
		grid.add(redigerkunde, 5, 2);
		redigerkunde.setOnAction(e ->{
		Redigerkunde redigerkunde1 = new Redigerkunde();
		redigerkunde1.start(new Stage());
		});
		
		Button redigermedarbejder = new Button("opret/rediger/slet medarbejder");
		grid.add(redigermedarbejder, 5, 3);
		redigermedarbejder.setOnAction(e ->{
		Redigermedarbejder redigermedarbejder1 = new Redigermedarbejder();
		redigermedarbejder1.start(new Stage());
		});
		
		
		Scene scene = new Scene(grid,450,400);
		stage.setScene(scene);
		scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
		}
}
