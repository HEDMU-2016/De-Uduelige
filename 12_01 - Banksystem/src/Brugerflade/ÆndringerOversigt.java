package Brugerflade;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sun.prism.paint.Color;

import DB.DB;
import domain.Ændring;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utill.TableCreator;


// DENNE CLASS VIRKER IKKE OG KOMMER IKKE TIL DET, MEN ER ET HURTIGT KONCEPT FORDI VI VAR UNDER TIDSPRES

public class ÆndringerOversigt {
	
	public void start(Stage stage) throws SQLException {
		DB db = new DB();
		Connection connection = db.getConnection();
		TableCreator tablecreator = new TableCreator();
		
		
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setAlignment(Pos.CENTER);
		
		
		stage.setTitle("Planlæg ændringer - Lortebank A/S");
		stage.getIcons().add(new Image("Brugerflade/ico.png"));

		Button close = new Button("X");
		HBox hbClose = new HBox(10);
		close.setId("close");
		hbClose.getChildren().add(close);
		hbClose.setAlignment(Pos.TOP_RIGHT);
		grid.add(hbClose, 3, 0);
		
		close.setOnAction(e -> {
			stage.close();
		});
		
		Label indførelsesdatolabel = new Label("Indførelsesdato");
		grid.add(indførelsesdatolabel, 2, 1);
		
		DatePicker startDato = new DatePicker();
		startDato.setPrefWidth(255.00);
		grid.add(startDato, 3, 1);
		
		Label statementlabel = new Label("hsql statement: ");
		grid.add(statementlabel, 2, 2);
		
		TextField statementfelt = new TextField();
		grid.add(statementfelt, 3, 2);
		statementfelt.setPrefWidth(255.00);
		
		TableView<Ændring> ændringtable = tablecreator.ændringertable();
		grid.add(ændringtable, 0, 0, 1, 4);
	

		Button commit = new Button("OK");
		commit.setId("KnapImenu");
		grid.add(commit, 2, 3, 3, 3);
		
		Text fejl = new Text("");
		HBox hbFejl = new HBox(10);
		hbFejl.getChildren().add(fejl);
		grid.add(hbFejl, 2, 4, 3, 4);
		fejl.setId("fejl");
		
		
		commit.setOnAction(j->{
			
			Date date = Date.valueOf(startDato.getValue());
			try {
			if(statementfelt.getText().toLowerCase().contains("drop")==false
			&& statementfelt.getText().toLowerCase().contains("delete") == false);
			PreparedStatement sqlstatement = connection.prepareStatement(statementfelt.getText());
			
			
			Ændring ændring = new Ændring(date,sqlstatement);	
			db.addÆndring(ændring);
			fejl.setText("Din ændring er nu planlagt!");
			} catch (SQLException e1) {
				e1.printStackTrace();
				fejl.setText("Der skete en fejl!");
			}
		});
		

		Scene scene = new Scene(grid);
		stage.setScene(scene);
		scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
		stage.setResizable(false);
		
		
		
		//admin vindue
		stage.show();
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
            }
        });
	}

}
