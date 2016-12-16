package Brugerflade;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DB.DB;
import domain.Konto;
import domain.Kunde;
import domain.Login;
import domain.Ændring;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
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
		grid.add(hbClose, 2, 0);
		
		close.setOnAction(e -> {
			stage.close();
		});
		
		Label indførelsesdatolabel = new Label("Ændring: ");
		indførelsesdatolabel.setId("tekst");
		grid.add(indførelsesdatolabel, 0, 2);
		
		ObservableList<String> options = FXCollections.observableArrayList("Startdato", "Slutdato");

		final ComboBox combobox = new ComboBox(options);

		combobox.setPrefWidth(170);
		grid.add(combobox, 1, 2);
		
		Label label = new Label("Hvilken tabel");
		grid.add(label, 2, 2);
		
		ObservableList<String> options2 = FXCollections.observableArrayList("kunde", "login", "konto");

		final ComboBox combobox2 = new ComboBox(options2);
		combobox2.setPrefWidth(170);
		grid.add(combobox2, 2, 3);


		Label statementlabel = new Label("Dato: ");
		statementlabel.setId("tekst");
		grid.add(statementlabel, 0, 3);
		
		DatePicker startDato = new DatePicker();
		grid.add(startDato, 1, 3);
		
		
		TableView<Konto> kompletteKontoliste = tablecreator.kontoTable();
		HBox hbKontolist = new HBox(10);
		hbKontolist.getChildren().add(kompletteKontoliste);
		grid.add(hbKontolist, 0, 1);
	
		TableView<Login> loginoversigt = tablecreator.loginTable();
		HBox hbLoginlist = new HBox(10);
		hbLoginlist.getChildren().add(loginoversigt);
		loginoversigt.setMinWidth(350);
		grid.add(hbLoginlist, 1, 1);
		
		TableView<Kunde> kundeoversigt = tablecreator.kundeTable();
		HBox hbKundelist = new HBox(10);
		hbKundelist.getChildren().add(kundeoversigt);
		kundeoversigt.setPrefWidth(300);
		grid.add(hbKundelist, 2, 1);
		

		Button commit = new Button("OK");
		commit.setId("KnapImenu");
		grid.add(commit, 3, 3, 3, 4);
		
		Text fejl = new Text("");
		HBox hbFejl = new HBox(10);
		hbFejl.getChildren().add(fejl);
		grid.add(hbFejl, 0, 4, 5, 4);
		fejl.setId("fejl");
		
		
		
	
		
		commit.setOnAction(j->{
			
			System.out.println("test 1");
			if(combobox2.getValue()=="konto"){
				System.out.println("test 2");
				Konto utillkonto = new Konto();
				if(combobox.getValue()=="Startdato"){
					System.out.println("test 3");
				utillkonto.setStartdato(kompletteKontoliste.getSelectionModel().getSelectedItem().getStartdato());
				utillkonto.setKontoid(kompletteKontoliste.getSelectionModel().getSelectedItem().getKontonummer());
				
				try {
					Konto aktuellekonto = db.findKonto(utillkonto.getKontoid());
					System.out.println(aktuellekonto);
					db.setStartDato(aktuellekonto, utillkonto.getStartdato());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				}
				if(combobox.getValue()=="slutdato"){
				utillkonto.setSlutdato(kompletteKontoliste.getSelectionModel().getSelectedItem().getSlutdato());	
				
				
				}
			kompletteKontoliste.getColumns();

			
			if(combobox.getValue()=="startdato"){
			
			}
			if(combobox.getValue()=="slutdato"){
				
			}
			}
			
		});
		fejl.setText("Ændringen er nu fortaget!");

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
