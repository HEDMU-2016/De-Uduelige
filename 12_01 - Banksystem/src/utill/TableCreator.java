package utill;

import java.math.BigDecimal;
import java.sql.SQLException;

import DB.DB;
import domain.Konto;
import domain.Kunde;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableCreator {
	
	public TableView<Kunde> kundetable()throws SQLException{
		
		ObservableList<Kunde> kundetabel;
		DB db = new DB();
		kundetabel = FXCollections.observableArrayList(db.listKunder());

		TableView<Kunde> kundeoversigt = new TableView<Kunde>();
		TableColumn<Kunde, String> nameCol = new TableColumn<Kunde, String>("name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Kunde, String>("navn"));
		TableColumn<Kunde, String> emailCol = new TableColumn<Kunde, String>("email");
		emailCol.setCellValueFactory(new PropertyValueFactory<Kunde, String>("email"));

		kundeoversigt.setItems(kundetabel);
		kundeoversigt.getColumns().addAll(nameCol, emailCol);
		return kundeoversigt;
	}
		public TableView<Konto> kontotable() throws SQLException{
		ObservableList<Konto> kontotabel;
		DB db = new DB();
		kontotabel = FXCollections.observableArrayList(db.listAlleKontis());

		TableView<Konto> kontooversigt = new TableView<Konto>();
		TableColumn<Konto, String> ejerCol = new TableColumn<Konto, String>("ejer");
		ejerCol.setCellValueFactory(new PropertyValueFactory<Konto, String>("ejer"));
		TableColumn<Konto, Integer> kontoCol = new TableColumn<Konto, Integer>("kontoID");
		kontoCol.setCellValueFactory(new PropertyValueFactory<Konto, Integer>("kontoid"));
		TableColumn<Konto, Double> saldoCol = new TableColumn<Konto, Double>("Saldo");
		saldoCol.setCellValueFactory(new PropertyValueFactory<Konto, Double>("saldo"));

		kontooversigt.setItems(kontotabel);
		kontooversigt.getColumns().addAll(ejerCol,kontoCol,saldoCol);
		return kontooversigt;
	}
	
	

}
