package utill;

import java.sql.Date;
import java.sql.SQLException;

import DB.DB;
import domain.Konto;
import domain.Kunde;
import domain.Login;
import domain.Postering;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableCreator {
	DB db = new DB();

	public TableView<Kunde> kundetable() throws SQLException {

		ObservableList<Kunde> kundetabel;
		kundetabel = FXCollections.observableArrayList(db.listKunder());

		TableView<Kunde> kundeoversigt = new TableView<Kunde>();

		TableColumn<Kunde, String> nameCol = new TableColumn<Kunde, String>("Navn");
		nameCol.setCellValueFactory(new PropertyValueFactory<Kunde, String>("navn"));

		TableColumn<Kunde, String> emailCol = new TableColumn<Kunde, String>("Email");
		emailCol.setCellValueFactory(new PropertyValueFactory<Kunde, String>("email"));

		TableColumn<Kunde, Date> dateCol = new TableColumn<Kunde, Date>("Oprettet");
		dateCol.setCellValueFactory(new PropertyValueFactory<Kunde, Date>("startdato"));

		kundeoversigt.setItems(kundetabel);
		kundeoversigt.getColumns().addAll(nameCol, emailCol, dateCol);
		return kundeoversigt;
	}
	public TableView<Konto> kontotable(Kunde kunde) throws SQLException {
		ObservableList<Konto> kontotabel;
		kontotabel = FXCollections.observableArrayList(db.listkonti(kunde));

		TableView<Konto> kontooversigt = new TableView<Konto>();

		TableColumn<Konto, String> ejerCol = new TableColumn<Konto, String>("Ejer ");
		ejerCol.setCellValueFactory(new PropertyValueFactory<Konto, String>("ejer"));

		TableColumn<Konto, Integer> kontoCol = new TableColumn<Konto, Integer>("KontoID ");
		kontoCol.setCellValueFactory(new PropertyValueFactory<Konto, Integer>("kontoid"));

		TableColumn<Konto, Double> saldoCol = new TableColumn<Konto, Double>("Saldo ");
		saldoCol.setCellValueFactory(new PropertyValueFactory<Konto, Double>("saldo"));

		kontooversigt.setItems(kontotabel);
		kontooversigt.getColumns().addAll(ejerCol, kontoCol, saldoCol);
		return kontooversigt;
	}


	public TableView<Login> logintable() throws SQLException {
		ObservableList<Login> logintabel;
		logintabel = FXCollections.observableArrayList(db.listLogins());

		TableView<Login> loginoversigt = new TableView();

		TableColumn<Login, String> bnCol = new TableColumn<Login, String>("Brugernavn ");
		bnCol.setCellValueFactory(new PropertyValueFactory<Login, String>("brugernavn"));

		TableColumn<Login, String> pwCol = new TableColumn<Login, String>("Password ");
		pwCol.setCellValueFactory(new PropertyValueFactory<Login, String>("adgangskode"));

		TableColumn<Login, Integer> idCol = new TableColumn<Login, Integer>("ID ");
		idCol.setCellValueFactory(new PropertyValueFactory<Login, Integer>("id"));
		
		loginoversigt.setItems(logintabel);
		loginoversigt.getColumns().addAll(bnCol, pwCol, idCol);
		return loginoversigt;
	}
	public TableView<Konto> kontotable() throws SQLException {
		ObservableList<Konto> kontotabel;
		kontotabel = FXCollections.observableArrayList(db.listAlleKontis());

		TableView<Konto> kontooversigt = new TableView<Konto>();

		TableColumn<Konto, String> ejerCol = new TableColumn<Konto, String>("Ejer ");
		ejerCol.setCellValueFactory(new PropertyValueFactory<Konto, String>("ejer"));

		TableColumn<Konto, Integer> kontoCol = new TableColumn<Konto, Integer>("kontoID ");
		kontoCol.setCellValueFactory(new PropertyValueFactory<Konto, Integer>("kontoid"));

		TableColumn<Konto, Double> saldoCol = new TableColumn<Konto, Double>("Saldo ");
		saldoCol.setCellValueFactory(new PropertyValueFactory<Konto, Double>("saldo"));

		kontooversigt.setItems(kontotabel);
		kontooversigt.getColumns().addAll(ejerCol, kontoCol, saldoCol);
		return kontooversigt;
	}


	public TableView<Postering> posteringtable() throws SQLException{
		ObservableList<Postering> posteringstable;
		posteringstable = FXCollections.observableArrayList(db.listPostering());
		
		TableView<Postering> posteringsoversigt = new TableView<Postering>();
		
		TableColumn<Postering, String> senderCol = new TableColumn<Postering, String>("Sender ");
		senderCol.setCellValueFactory(new PropertyValueFactory<Postering, String>("sender"));
		
		TableColumn<Postering, String> modtagerCol = new TableColumn<Postering, String>("Modtager ");
		modtagerCol.setCellValueFactory(new PropertyValueFactory<Postering,String>("modtager"));
		
		TableColumn<Postering, Date> sendtCol = new TableColumn<Postering, Date>("Sendt ");
		sendtCol.setCellValueFactory(new PropertyValueFactory<Postering, Date>("sendt"));
		
		TableColumn<Postering, Double> beløbCol = new TableColumn<Postering, Double>("Beløb ");
		beløbCol.setCellValueFactory(new PropertyValueFactory<Postering, Double>("beløb"));
		
		posteringsoversigt.setItems(posteringstable);
		posteringsoversigt.getColumns().addAll(senderCol,modtagerCol,sendtCol,beløbCol);
		
		return posteringsoversigt;
	}
	public TableView<Postering> posteringtable(Konto konto) throws SQLException{
		ObservableList<Postering> posteringstable;
		posteringstable = FXCollections.observableArrayList(db.listPostering(konto));
		
		TableView<Postering> posteringsoversigt = new TableView<Postering>();
		
		TableColumn<Postering, String> senderCol = new TableColumn<Postering, String>("Sender ");
		senderCol.setCellValueFactory(new PropertyValueFactory<Postering, String>("sender"));
		
		TableColumn<Postering, String> modtagerCol = new TableColumn<Postering, String>("Modtager ");
		modtagerCol.setCellValueFactory(new PropertyValueFactory<Postering,String>("modtager"));
		
		TableColumn<Postering, Date> sendtCol = new TableColumn<Postering, Date>("Sendt ");
		sendtCol.setCellValueFactory(new PropertyValueFactory<Postering, Date>("sendt"));
		
		TableColumn<Postering, Double> beløbCol = new TableColumn<Postering, Double>("Beløb ");
		beløbCol.setCellValueFactory(new PropertyValueFactory<Postering, Double>("beløb"));
		
		posteringsoversigt.setItems(posteringstable);
		posteringsoversigt.getColumns().addAll(senderCol,modtagerCol,sendtCol,beløbCol);
		
		return posteringsoversigt;
	}

}
