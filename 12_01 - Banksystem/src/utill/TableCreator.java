package utill;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import DB.DB;
import domain.FastOverførsel;
import domain.Kontakt;
import domain.Konto;
import domain.Kunde;
import domain.Login;
import domain.Postering;
import domain.Ændring;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableCreator {
	DB db = new DB();

	public TableView<Kunde> kundeTable() throws SQLException {

		ObservableList<Kunde> kundetabel;
		kundetabel = FXCollections.observableArrayList(db.listKunder());

		TableView<Kunde> kundeoversigt = new TableView<Kunde>();

		TableColumn<Kunde, String> nameCol = new TableColumn<Kunde, String>("Navn");
		nameCol.setCellValueFactory(new PropertyValueFactory<Kunde, String>("navn"));

		TableColumn<Kunde, String> emailCol = new TableColumn<Kunde, String>("Email");
		emailCol.setCellValueFactory(new PropertyValueFactory<Kunde, String>("email"));

		TableColumn<Kunde, Date> startdatoCol = new TableColumn<Kunde, Date>("Oprettet");
		startdatoCol.setCellValueFactory(new PropertyValueFactory<Kunde, Date>("startdato"));

		TableColumn<Kunde,Date> slutdatoCol = new TableColumn<Kunde,Date>("Slettet");
		slutdatoCol.setCellValueFactory(new PropertyValueFactory<Kunde,Date>("slutdato"));
		
		kundeoversigt.setItems(kundetabel);
		kundeoversigt.getColumns().addAll(nameCol, emailCol, startdatoCol,slutdatoCol);
		return kundeoversigt;
	}

	public TableView<Login> loginTable() throws SQLException {
		ObservableList<Login> logintabel;
		logintabel = FXCollections.observableArrayList(db.listLogins());

		TableView<Login> loginoversigt = new TableView();

		TableColumn<Login, String> bnCol = new TableColumn<Login, String>("Brugernavn ");
		bnCol.setCellValueFactory(new PropertyValueFactory<Login, String>("brugernavn"));

		TableColumn<Login, String> pwCol = new TableColumn<Login, String>("Password ");
		pwCol.setCellValueFactory(new PropertyValueFactory<Login, String>("adgangskode"));

		TableColumn<Login, Integer> idCol = new TableColumn<Login, Integer>("ID ");
		idCol.setCellValueFactory(new PropertyValueFactory<Login, Integer>("id"));
		
		TableColumn<Login,String> startdatoCol = new TableColumn<Login,String>("Startdato");
		startdatoCol.setCellValueFactory(new PropertyValueFactory<Login,String>("startdato"));
		
		TableColumn<Login,Date> slutdatoCol = new TableColumn<Login,Date>("Slutdato");
		slutdatoCol.setCellValueFactory(new PropertyValueFactory<Login,Date>("slutdato"));
		
		loginoversigt.setItems(logintabel);
		loginoversigt.getColumns().addAll(bnCol, pwCol, idCol,startdatoCol,slutdatoCol);
		return loginoversigt;
	}

	public TableView<Konto> kontoTable(Kunde kunde) throws SQLException {
		TableView<Konto> kontooversigt = new TableView<Konto>();
		List<Konto> kontolist = db.listkonti(kunde);

		ObservableList<Konto> kontotabel;
		kontotabel = FXCollections.observableArrayList(kontolist);

		TableColumn<Konto, String> ejerCol = new TableColumn<Konto, String>("Ejer ");
		ejerCol.setCellValueFactory(new PropertyValueFactory<Konto, String>("ejer"));

		TableColumn<Konto, Integer> kontoCol = new TableColumn<Konto, Integer>("KontoID ");
		kontoCol.setCellValueFactory(new PropertyValueFactory<Konto, Integer>("kontonummer"));

		TableColumn<Konto, Double> saldoCol = new TableColumn<Konto, Double>("Saldo ");
		saldoCol.setCellValueFactory(new PropertyValueFactory<Konto, Double>("saldo"));

		TableColumn<Konto,Date> startdatoCol = new TableColumn<Konto,Date>("Startdato");
		startdatoCol.setCellValueFactory(new PropertyValueFactory<Konto,Date>("startdato "));
		
		TableColumn<Konto,Date> slutdatoCol = new TableColumn<Konto,Date>("Slutdato");
		slutdatoCol.setCellValueFactory(new PropertyValueFactory<Konto,Date>("slutdato "));
		
		
		kontooversigt.setItems(kontotabel);
		kontooversigt.getColumns().addAll(ejerCol, kontoCol, saldoCol,startdatoCol,slutdatoCol);

		return kontooversigt;

	}

	public TableView<Konto> kontoTable() throws SQLException {
		ObservableList<Konto> kontotabel;
		kontotabel = FXCollections.observableArrayList(db.listAlleKontis());

		TableView<Konto> kontooversigt = new TableView<Konto>();

		TableColumn<Konto, String> ejerCol = new TableColumn<Konto, String>("Ejer ");
		ejerCol.setCellValueFactory(new PropertyValueFactory<Konto, String>("ejernavn"));

		TableColumn<Konto, Integer> kontoCol = new TableColumn<Konto, Integer>("kontoID ");
		kontoCol.setCellValueFactory(new PropertyValueFactory<Konto, Integer>("kontonummer"));

		TableColumn<Konto, Double> saldoCol = new TableColumn<Konto, Double>("Saldo ");
		saldoCol.setCellValueFactory(new PropertyValueFactory<Konto, Double>("saldo"));

		TableColumn<Konto,Date> startdatoCol = new TableColumn<Konto,Date>("Startdato");
		startdatoCol.setCellValueFactory(new PropertyValueFactory<Konto,Date>("startdato"));
		
		TableColumn<Konto,Date> slutdatoCol = new TableColumn<Konto,Date>("Slutdato");
		slutdatoCol.setCellValueFactory(new PropertyValueFactory<Konto,Date>("slutdato"));
		
		kontooversigt.setItems(kontotabel);
		kontooversigt.getColumns().addAll(ejerCol, kontoCol, saldoCol,startdatoCol,slutdatoCol);
		return kontooversigt;
	}

	public ObservableList<Postering> posteringsTable(Konto konto) throws SQLException {
		ObservableList<Postering> posteringstable;
		posteringstable = FXCollections.observableArrayList(db.listPostering(konto));

		TableView<Postering> posteringsoversigt = new TableView<Postering>();

		TableColumn<Postering, String> senderCol = new TableColumn<Postering, String>("Sender ");
		senderCol.setCellValueFactory(new PropertyValueFactory<Postering, String>("sender"));

		TableColumn<Postering, String> modtagerCol = new TableColumn<Postering, String>("Modtager ");
		modtagerCol.setCellValueFactory(new PropertyValueFactory<Postering, String>("modtager"));

		TableColumn<Postering, Date> sendtCol = new TableColumn<Postering, Date>("Sendt ");
		sendtCol.setCellValueFactory(new PropertyValueFactory<Postering, Date>("sendt"));

		TableColumn<Postering, Double> beløbCol = new TableColumn<Postering, Double>("Beløb ");
		beløbCol.setCellValueFactory(new PropertyValueFactory<Postering, Double>("beløb"));

		posteringsoversigt.setItems(posteringstable);
		posteringsoversigt.getColumns().addAll(senderCol, modtagerCol, sendtCol, beløbCol);

		return posteringstable;
	}
	public ObservableList<Postering> posteringsTable() throws SQLException {
		ObservableList<Postering> posteringstable;
		posteringstable = FXCollections.observableArrayList(db.listallePosteringer());

		TableView<Postering> posteringsoversigt = new TableView<Postering>();

		TableColumn<Postering, String> senderCol = new TableColumn<Postering, String>("Sender ");
		senderCol.setCellValueFactory(new PropertyValueFactory<Postering, String>("sender"));

		TableColumn<Postering, String> modtagerCol = new TableColumn<Postering, String>("Modtager ");
		modtagerCol.setCellValueFactory(new PropertyValueFactory<Postering, String>("modtager"));

		TableColumn<Postering, Date> sendtCol = new TableColumn<Postering, Date>("Sendt ");
		sendtCol.setCellValueFactory(new PropertyValueFactory<Postering, Date>("sendt"));

		TableColumn<Postering, Double> beløbCol = new TableColumn<Postering, Double>("Beløb ");
		beløbCol.setCellValueFactory(new PropertyValueFactory<Postering, Double>("beløb"));

		posteringsoversigt.setItems(posteringstable);
		posteringsoversigt.getColumns().addAll(senderCol, modtagerCol, sendtCol, beløbCol);

		return posteringstable;
	}

	public TableView<Postering> posteringTable(Kunde kunde) throws SQLException {
		
		List<Konto> kontolist = db.listkonti(kunde);
		List<Postering> posteringslist;
		
		ObservableList<Postering> posteringstable = FXCollections.observableArrayList();
		
		TableView<Postering> posteringsoversigt = new TableView<Postering>();
		
		TableColumn<Postering, String> senderCol = new TableColumn<Postering, String>("Sendt fra");
		PropertyValueFactory<Postering,String> senderColFabrik = new PropertyValueFactory<Postering,String>("sender");
		
		TableColumn<Postering, String> modtagerCol = new TableColumn<Postering, String>("Modtaget på");
		PropertyValueFactory<Postering,String> modtagerColFabrik = new PropertyValueFactory<Postering,String>("modtager");
		
		TableColumn<Postering, Date> sendtCol = new TableColumn<Postering, Date>("Sendt ");
		PropertyValueFactory<Postering,Date> sendtColFabrik = new PropertyValueFactory<Postering,Date>("sendt");
		
		TableColumn<Postering, Double> beløbCol = new TableColumn<Postering, Double>("Beløb ");
		PropertyValueFactory<Postering,Double> beløbColFabrik = new PropertyValueFactory<Postering,Double>("beløb");
		
		for(int i=0;i<kontolist.size();i++){
			posteringslist = db.listPostering(kontolist.get(i));
			for(int j=0;j<posteringslist.size();j++){
			posteringstable.add(posteringslist.get(j));
			}
		}
		for (int i = 0; i < kontolist.size(); i++) {
		senderCol.setCellValueFactory(senderColFabrik);
		modtagerCol.setCellValueFactory(modtagerColFabrik);
		sendtCol.setCellValueFactory(sendtColFabrik);
		beløbCol.setCellValueFactory(beløbColFabrik);
		}
		
		posteringsoversigt.setItems(posteringstable);
		posteringsoversigt.getColumns().addAll(senderCol,modtagerCol,sendtCol,beløbCol);
		
		return posteringsoversigt;
	}

	public TableView<Kontakt> kontaktTable(Login bruger) throws SQLException {
		Kunde tmpkunde = db.matchkundemedlogin(bruger.getBrugernavn());
		ObservableList<Kontakt> kontakttable;
		
		kontakttable = FXCollections.observableArrayList(db.listkontakter(tmpkunde));

		TableView<Kontakt> kontaktoversigt = new TableView<Kontakt>();

		TableColumn<Kontakt, String> navnCol = new TableColumn<Kontakt, String>("Navn ");
		navnCol.setCellValueFactory(new PropertyValueFactory<Kontakt, String>("navn"));

		TableColumn<Kontakt, Integer> kontonrCol = new TableColumn<Kontakt, Integer>("Kontonr ");
		kontonrCol.setCellValueFactory(new PropertyValueFactory<Kontakt, Integer>("kontonr"));

		kontaktoversigt.setItems(kontakttable);
		kontaktoversigt.getColumns().addAll(navnCol, kontonrCol);

		return kontaktoversigt;

	}

	public TableView<FastOverførsel> fastoverførselsTable(Login bruger) throws SQLException {
		ObservableList<FastOverførsel> fastoverførselsTable;
		fastoverførselsTable = FXCollections.observableArrayList(db.listfasteoverførsler(bruger));

		TableView<FastOverførsel> fastoverførselsOversigt = new TableView<FastOverførsel>();

		TableColumn<FastOverførsel, Integer> senderCol = new TableColumn<FastOverførsel, Integer>("Sender");
		senderCol.setCellValueFactory(new PropertyValueFactory<FastOverførsel, Integer>("sender "));

		TableColumn<FastOverførsel, Integer> modtagerCol = new TableColumn<FastOverførsel, Integer>(
				"modtager");
		modtagerCol.setCellValueFactory(new PropertyValueFactory<FastOverførsel, Integer>("modtager "));

		TableColumn<FastOverførsel, Double> beløbCol = new TableColumn<FastOverførsel, Double>("Beløb");
		beløbCol.setCellValueFactory(new PropertyValueFactory<FastOverførsel, Double>("beløb"));

		TableColumn<FastOverførsel, Date> datoCol = new TableColumn<FastOverførsel, Date>("Næste Overførsel");
		datoCol.setCellValueFactory(new PropertyValueFactory<FastOverførsel, Date>("startdato"));

		TableColumn<FastOverførsel, Integer> idCol = new TableColumn<FastOverførsel, Integer>("Type");
		idCol.setCellValueFactory(new PropertyValueFactory<FastOverførsel, Integer>("id"));

		fastoverførselsOversigt.setItems(fastoverførselsTable);
		fastoverførselsOversigt.getColumns().addAll(senderCol, modtagerCol, beløbCol, datoCol, idCol);

		return fastoverførselsOversigt;

	}

	public TableView<Ændring> ændringertable() throws SQLException{
		ObservableList<Ændring> ændringtable;
		ændringtable = FXCollections.observableArrayList(db.listændringer());
		
		TableView<Ændring> ændringoversigt = new TableView<Ændring>();
		
		TableColumn<Ændring,Date> dateCol = new TableColumn<Ændring,Date>("Dato");
		dateCol.setCellValueFactory(new PropertyValueFactory<Ændring,Date>("indsætningsdato"));
		
		TableColumn<Ændring, String> statementCol = new TableColumn<Ændring,String>("Ændring");
		statementCol.setCellValueFactory(new PropertyValueFactory<Ændring,String>("statement"));
		
		
		ændringoversigt.setItems(ændringtable);
		ændringoversigt.getColumns().addAll(dateCol,statementCol);
		
		return ændringoversigt;
	}
//	public TableView<Object> objekttable() throws SQLException{
//		ObservableList<Object> objekclist = FXCollections.observableArrayList();
//		ObservableList<Kunde> kundelist = FXCollections.observableArrayList(db.listKunder());
//		ObservableList<Konto> kontolist = FXCollections.observableArrayList(db.listAlleKontis());
//		ObservableList<Login> loginlist = FXCollections.observableArrayList(db.listLogins());
//		objekclist.addAll(kundelist);
//		objekclist.addAll(kontolist);
//		objekclist.addAll(loginlist);
//		
//		PropertyValueFactory<Object,String> kontofabrik = new PropertyValueFactory<Object,String>("kontoid");
//		PropertyValueFactory<Object,String> kundefabrik = new PropertyValueFactory<Object,String>("navn");
//		
//		
//		TableView<Object> objektoversigt = new TableView<Object>();
//		
//		TableColumn<Object,String> objectCol = new TableColumn<Object,String>("Object");
//		objectCol.setCellValueFactory(kontofabrik);
//		objectCol.
//		
//		return objektoversigt;
//	
//	}


}