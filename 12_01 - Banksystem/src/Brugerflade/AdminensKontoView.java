package Brugerflade;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import DB.DB;
import domain.Konto;
import domain.MånedligRente;
import domain.Rente;
import domain.ÅrligRente;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import utill.TableCreator;

public class AdminensKontoView {

	public void start(Stage stage) throws SQLException{
			DB db = new DB();
			GridPane grid = new GridPane();
			TableCreator tablecreator = new TableCreator();

			grid.setVgap(10);
			grid.setHgap(10);
			
			stage.setTitle("Konto historik - Lortebank A/S");
			stage.getIcons().add(new Image("Brugerflade/ico.png"));
			
			Label allekontoer = new Label("Alle kontoer");
			allekontoer.setId("overskrift");
			grid.add(allekontoer, 0, 0);

			Button close = new Button("X");
			HBox hbClose = new HBox(10);
			close.setId("close");
			hbClose.getChildren().add(close);
			hbClose.setAlignment(Pos.TOP_RIGHT);
			grid.add(hbClose, 3, 0);
			
			close.setOnAction(e -> {
				stage.close();
			});

			TableView<Konto> kompletteKontoliste = tablecreator.kontoTable();
			kompletteKontoliste.setPrefWidth(350);
			grid.add(kompletteKontoliste, 0, 1, 1, 8);
			
			Label ejerlabel = new Label("Ejer");
			ejerlabel.setId("tekst");
			grid.add(ejerlabel, 2, 2);
			
			TextField ejerfelt = new TextField();
			ejerfelt.setPrefWidth(250);
			grid.add(ejerfelt, 3, 2);
			
			Label saldolabel = new Label("Saldo");
			saldolabel.setId("tekst");
			grid.add(saldolabel, 2, 3);
			
			TextField saldofelt = new TextField();
			grid.add(saldofelt, 3, 3);
			
			Label rentelabel = new Label("Rente");
			rentelabel.setId("tekst");
			grid.add(rentelabel, 2, 4);
			
			TextField rentefelt = new TextField();
			rentelabel.setId("tekst");
			grid.add(rentefelt, 3, 4);
			
			Label fejl = new Label("");
			fejl.setId("fejl");
			grid.add(fejl, 2, 9, 3, 9);
			
			ObservableList<String> options = FXCollections.observableArrayList("Månedlig", "Årlig");
			final ComboBox comboBox = new ComboBox(options);
			comboBox.setPrefWidth(270);
			grid.add(comboBox, 3, 5);

			Label startDatoLabel = new Label("Start dato");
			startDatoLabel.setId("tekst");
			grid.add(startDatoLabel, 2, 6);
			
			DatePicker startDato = new DatePicker();
			grid.add(startDato, 3, 6);
			startDato.setPrefWidth(270);
			System.out.println(startDato.getValue());
			
			Label slutDatoLabel = new Label("Slut dato");
			slutDatoLabel.setId("tekst");
			grid.add(slutDatoLabel, 2, 7);
			
			DatePicker slutDato = new DatePicker();
			grid.add(slutDato, 3, 7);
			slutDato.setPrefWidth(270);
			System.out.println(slutDato.getValue());
			
			Button opret = new Button("Opret");
			grid.add(opret, 2, 8, 3, 8);
			opret.setId("KnapImenu");
			
			opret.setOnAction(e->{
			if(ejerfelt.getText().isEmpty() == false && saldofelt.getText().isEmpty() == false)
			try {
				db.addKonto(ejerfelt.getText().toLowerCase(), BigDecimal.valueOf(Double.parseDouble(saldofelt.getText())));
				
				if(legitrente(rentefelt.getText(),fejl)==true){
				BigDecimal renten = BigDecimal.valueOf(Double.parseDouble(rentefelt.getText()));
				List<Konto> kontolist = db.listAlleKontis();
				
				int kontonummer = kontolist.get(kontolist.size()-1).getKontonummer(); 
				if(comboBox.getValue().equals("månedlig")){
					Date indsætningsdato = Date.valueOf(LocalDate.now().plusMonths(1).minusDays(LocalDate.now().getDayOfMonth()-1));					
					Rente rente = new MånedligRente(renten,indsætningsdato,kontonummer);
					db.addRente(rente);
				}
				if(comboBox.getValue().equals("årlig")){
					Date indsætningsdato = Date.valueOf(LocalDate.now().plusYears(1).minusDays(LocalDate.now().getDayOfMonth()-1).minusMonths(LocalDate.now().getMonthValue()));										
					Rente rente = new ÅrligRente(renten,indsætningsdato,kontonummer);
				}
			}
				fejl.setTextFill(Color.GREEN);
				fejl.setText("Kontoen blev oprettet!");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			else {
				fejl.setTextFill(Color.RED);
				fejl.setText("Du skal lige udfylde alle felterne!");
			}
			});
		 
			Scene scene = new Scene(grid);
			stage.setScene(scene);
			scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
			stage.setResizable(false);
			//stage.initStyle(StageStyle.UTILITY);
			stage.show();
			
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            @Override
	            public void handle(WindowEvent event) {
	                event.consume();
	            }
	        });
			
		}

private boolean legitrente(String rente, Label fejl) {
	double rentesomdouble = Double.parseDouble(rente);
	if(rentesomdouble<1)
	return true;
	else fejl.setText("renten skal være over 1");
	return false;

	}
}
