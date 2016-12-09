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

public class AdministratorVindue {

	public void start(Stage stage) throws SQLException {

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(10);
		grid.setHgap(10);

		// Dette skal bruges på alle vinduer
		//
		//
		stage.setTitle("Adminmenu - Lortebank A/S");
		stage.getIcons().add(new Image("Brugerflade/ico.png"));
		//
		//
		// Dette skal bruges på alle vinduer

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
		grid.add(hbClose, 0, 0);

		close.setOnAction(e -> {
			stage.close();
		});

		Button kundeoversigt = new Button("Kundeoversigt");
		grid.add(kundeoversigt, 0, 2);
		kundeoversigt.setId("KnapImenu");
		kundeoversigt.setOnAction(e -> {
			Kundeoversigt kundeoversigt1 = new Kundeoversigt();
			try {
				kundeoversigt1.start(new Stage());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		});

		Button medarbejderoversigt = new Button("Medarbejderoversigt");
		grid.add(medarbejderoversigt, 0, 3);
		medarbejderoversigt.setId("KnapImenu");
		medarbejderoversigt.setOnAction(e -> {
			Medarbejderoversigt medarbejderoversigt1 = new Medarbejderoversigt();
			medarbejderoversigt1.start(new Stage());
		});

		Button redigerkunde = new Button("Opret/rediger/slet kunde");
		grid.add(redigerkunde, 0, 4);
		redigerkunde.setId("KnapImenu");
		redigerkunde.setOnAction(e -> {
			Redigerkunde redigerkunde1 = new Redigerkunde();
			redigerkunde1.start(new Stage());
		});

		Button redigermedarbejder = new Button("Opret/rediger/slet medarbejder");
		grid.add(redigermedarbejder, 0, 5);
		redigermedarbejder.setId("KnapImenu");
		redigermedarbejder.setOnAction(e -> {
			Redigermedarbejder redigermedarbejder1 = new Redigermedarbejder();
			redigermedarbejder1.start(new Stage());
		});
	
		Button kontooversigt = new Button("Se alle kontis");
		grid.add(kontooversigt, 0, 6);
		kontooversigt.setId("KnapImenu");
		kontooversigt.setOnAction(e -> {
			AdminensKontoView adminenskontoview = new AdminensKontoView();
			try {
				adminenskontoview.start(new Stage());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		Button logins = new Button("Se alle logins");
		grid.add(logins, 0, 7);
		logins.setId("KnapImenu");
		logins.setOnAction(e -> {
			LoginOversigt loginoversigt = new LoginOversigt();
			try {
				loginoversigt.start(new Stage());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		Scene scene = new Scene(grid, 400, 500);
		stage.setScene(scene);
		scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}
}
