package Brugerflade;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import DB.DB;
import domain.Konto;
import domain.Kunde;
import domain.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utill.TableCreator;
public class KontoOversigt {
ObservableList<Konto> list;
	

	public Kunde findKunde(String navn, DB db) throws SQLException{
		List<Kunde> kundeliste = db.listKunder();
		for(int i=0;i<=kundeliste.size();i++){
		Kunde tmpkunde = kundeliste.get(i);
		if(tmpkunde.getNavn()==navn)
		return tmpkunde;
		else continue;
		}
		return null;
	}
	
	public void start(Stage stage, Login bruger)throws SQLException{
		DB db = new DB();
		TableCreator tablecreator = new TableCreator();
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
	
		stage.setTitle("Konto historik - Lortebank A/S");
		stage.getIcons().add(new Image("Brugerflade/ico.png"));

		Button close = new Button("X");
		close.setId("close");
		grid.add(close, 0,0);
		
		close.setOnAction(e -> {
		stage.close();
		});
		
		Kunde tmpkunde = db.matchkundemedlogin(bruger.getBrugernavn());
		
		TableView<Konto> kundeoversigt = tablecreator.kontoTable(tmpkunde);
		grid.add(kundeoversigt, 0, 0);
		
		Scene scene = new Scene(grid, 400, 500);
		stage.setScene(scene);
		scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
		
		}
	
}
