package Brugerflade;


import java.sql.SQLException;
import java.util.List;

import DB.DB;
import domain.Konto;
import domain.Kunde;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.Login_IO;
public class KontoOversigt {
ObservableList<Konto> list;
	
	private void createTable(ObservableList<Konto> kontolist){
	TableView<Konto> kontooversigt = new TableView<Konto>();
	TableColumn<Konto,String> ejerCol = new TableColumn("name");
	ejerCol.setCellValueFactory(new PropertyValueFactory<Konto,String>("ejer"));
	TableColumn<Konto,String> kontoIdCol = new TableColumn("ID");
	kontoIdCol.setCellValueFactory(new PropertyValueFactory<Konto,String>("kontonummer"));
	TableColumn<Konto,Double> saldoCol = new TableColumn("Saldo");
	saldoCol.setCellValueFactory(new PropertyValueFactory<Konto,Double>("saldo"));
	
	kontooversigt.setItems(kontolist);
	kontooversigt.getColumns().addAll(ejerCol, kontoIdCol, saldoCol);
	}
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
	
	public void start(Stage stage)throws SQLException{
		DB db = new DB();
		stage = new Stage();
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
	

		Kunde dennis = findKunde("Dennis Rosenkilde", db);
		ObservableList<Konto> kontolist = FXCollections.observableArrayList(db.listkonti(dennis));
		createTable(kontolist);
		
		
		Scene scene = new Scene(grid);
		stage.setScene(scene);
		scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
		}
	
}
