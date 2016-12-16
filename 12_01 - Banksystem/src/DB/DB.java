package DB;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Interfaces.Startable;
import domain.AdminLogin;
import domain.FastOverførsel;
import domain.Kontakt;
import domain.Konto;
import domain.Kunde;
import domain.Login;
import domain.MånedligRente;
import domain.NormaltLogin;
import domain.Postering;
import domain.Rente;
import domain.ÅrligRente;
import domain.Ændring;
import javafx.util.Callback;
import logic.Logic;

public class DB implements Startable {
	static final String db = "jdbc:hsqldb:hsql://localhost/mydb";
	static final String dbuser = "SA";
	static final String dbpass = "";
	private Connection connection;
	ResultSet resultset;
	PreparedStatement statement;
	Logic logic;

	public void start() {
		try {
			connection = DriverManager.getConnection(db, dbuser, dbpass);
			checkifdayhaspassed();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Connection getConnection() throws SQLException{
		connection=DriverManager.getConnection(db,dbuser,dbpass);
		return connection;
	}
	
	public void addÆndring(Ændring ændring)throws SQLException{
	System.out.println("tilfører ændring: "+ændring);
	start();
	statement = connection.prepareStatement("insert into ændring(indeførelsesdato,statement) values(?,?)");
	statement.setDate(1, ændring.getIndførelsesdato());
	statement.setString(2, ændring.getStatement().toString());
	statement.execute();
	stop();	
	
	}
	public void executeÆndring(Ændring ændring) throws SQLException{
		start();
		ændring.getStatement().execute();
		stop();
	}
	
	
	
	//tidsstyringsmetoder (skal muligvis bruges)
	public void setStartDato(Kunde kunde,Date startdato)throws SQLException{
		start();
		statement=connection.prepareStatement("update kunde set startdato=? where brugernavn=?");
		statement.setDate(1, startdato);
		statement.setString(2, kunde.getBrugernavn());
		statement.execute();
		System.out.println("satte startdatoen til: "+startdato+"på kunde: "+kunde);
		stop();
	}
	public void setSlutDato(Kunde kunde, Date slutdato) throws SQLException{
	start();
	statement=connection.prepareStatement("update kunde set slutdato=? where brugernavn=?");
	statement.setDate(1, slutdato);
	statement.setString(2, kunde.getBrugernavn());
	statement.execute();
	System.out.println("satte slutdatoen til "+slutdato+" på kunde "+kunde);
	stop();
	}
	public void setStartDato(Konto konto,Date startdato)throws SQLException{
		start();
		statement=connection.prepareStatement("update konto set startdato=? where kontoid=?");
		statement.setDate(1, startdato);
		statement.setInt(2, konto.getKontonummer());
		statement.execute();
		System.out.println("satte konto "+konto.getKontonummer()+"s startdato til "+ startdato);
	}
	public void setSslutDato(Konto konto,Date slutdato)throws SQLException{
		start();
		statement=connection.prepareStatement("update konto set slutdato=? where kontoid=?");
		statement.setDate(1, slutdato);
		statement.setInt(2, konto.getKontonummer());
		statement.execute();
		System.out.println("satte konto "+konto.getKontonummer()+"s slutdato til "+ slutdato);
	}
	public void setStartDato(Login login,Date startdato)throws SQLException{
		start();
		statement=connection.prepareStatement("update login set startdato=? where brugernavn=?");
		statement.setDate(1, startdato);
		statement.setString(2, login.getBrugernavn());
		statement.execute();
		System.out.println("satte login med brugernavn "+login.getBrugernavn()+" til startdatoen "+ startdato);
	}
	public void setSslutDato(Login login,Date slutdato)throws SQLException{
		start();
		statement=connection.prepareStatement("update konto set slutdato=? where kontoid=?");
		statement.setDate(1, slutdato);
		statement.setString(2, login.getBrugernavn());
		statement.execute();
		System.out.println("satte konto "+login.getBrugernavn()+"s slutdato til "+ slutdato);
	}
	// INDSÆT METODER:
	public void addRente(Rente rente) throws SQLException{
		System.out.println("Tilfører rente: "+rente);
		start();
		statement = connection.prepareStatement("insert into renter (rente,indsætningsdato,kontonummer) values(?,?,?)");
		statement.setDouble(1, rente.getRente().doubleValue());
		statement.setDate(2,rente.getIndsætningsdato());
		statement.setInt(3, rente.getKontonummer());
		statement.execute();
		System.out.println("done");
		stop();
	}
	public void addKontakt(Kontakt kontakt, String ejer) throws SQLException { 
		//ejer ligger sig til brugernavn																																	
		
		System.out.println("tilførert kontakt: " + kontakt);
		start();
		statement = connection.prepareStatement("insert into kontakt (navn, kontonr, ejer) values(?,?,?)");
		statement.setString(1, kontakt.getKontakt());
		statement.setInt(2, kontakt.getKontonr());
		statement.setString(3, ejer);
		statement.execute();
		System.out.println("done");
		stop();
	}

	public void addKunde(Kunde kunde) throws SQLException {
		System.out.println("tilfører kunde...");
		start();
		Date startdato = Date.valueOf(LocalDate.now());
		Date slutdato = Date.valueOf(LocalDate.of(9999, 01, 01));
		statement = connection.prepareStatement("insert into kunde(navn, startdato, slutdato, email,brugernavn) values (?,?,?,?,?)");
		statement.setString(1, kunde.getNavn());
		statement.setDate(2, startdato);
		statement.setDate(3, slutdato);
		statement.setString(4, kunde.getEmail());
		statement.setString(5, kunde.getBrugernavn());
		statement.execute();
		System.out.println("Tilførte kunde: " + kunde);
		stop();
	}

	public void addKonto(Konto konto) throws SQLException {
		System.out.println("tilfører konto...");
		Date startdato = Date.valueOf(LocalDate.now());
		Date slutdato = Date.valueOf(LocalDate.of(9999, 12, 31));
		start();
		statement = connection.prepareStatement("insert into konto(ejer,saldo,startdato,slutdato)values(?,?,?,?)");
		statement.setString(1, konto.getEjer().getNavn());
		statement.setDouble(2, konto.getSaldo().doubleValue());
		statement.setDate(3, startdato);
		statement.setDate(4, slutdato);		
		statement.execute();
		System.out.println(konto+ "blev lagt ind i databasen");
		stop();
	}
	public void addKonto(String ejer, BigDecimal saldo) throws SQLException {
		System.out.println("tilfører konto...");
		Date startdato = Date.valueOf(LocalDate.now());
		Date slutdato = Date.valueOf(LocalDate.of(9999, 12, 31));
		start();
		statement = connection.prepareStatement("insert into konto(ejer,saldo,startdato,slutdato)values(?,?,?,?)");
		statement.setString(1, ejer);
		statement.setDouble(2, saldo.doubleValue());
		statement.setDate(2, startdato);
		statement.setDate(3, slutdato);		
		statement.execute();
		System.out.println("kontoen med ejer:"+ejer+"blev lagt ind i databasen");
		stop();
	}

	public void addLogin(Login login) throws SQLException {
		System.out.println("tilfører login...");
		Date startdato = Date.valueOf(LocalDate.now());
		Date slutdato = Date.valueOf(LocalDate.of(9999, 12, 31));
		
		
		start();
		statement = connection.prepareStatement("INSERT INTO login (brugernavn, adgangskode, id,startdato,slutdato) values(?,?,?,?,?)");
		statement.setString(1, login.getBrugernavn());
		statement.setString(2, login.getAdgangskode());
		statement.setInt(3, login.getId());
		statement.setDate(4, startdato);
		statement.setDate(5, slutdato);
		statement.execute();
		System.out.println("Login: " + login + "blev lagt ind i databasen");
		stop();
	}

	public void addPostering(Postering postering) throws SQLException {
		System.out.println("tilfører postering...");
		start();
		statement = connection.prepareStatement("insert into postering(modtager,sender,sendt,beløb) values(?,?,?,?)");
		statement.setInt(1, postering.getModtager());
		statement.setInt(2, postering.getSender());
		statement.setDate(3, postering.getSendt());
		statement.setDouble(4, postering.getBeløb().doubleValue());
		statement.execute();
		System.out.println("postering: " + postering + " blev lagt ind i databasen");
		stop();
	}

	// FIND METODER:
	public Rente findrente(Konto konto) throws SQLException{
		System.out.println("finder renten til konto: "+konto);
		start();
		statement = connection.prepareStatement("select rente, kontonummer,indsætningsdato,id from renter where kontonummer=?");
		statement.setInt(1, konto.getKontonummer());
		resultset=statement.executeQuery();
		while(resultset.next()){
			int kontonummer = resultset.getInt("kontonummer");
			double rente = resultset.getDouble("rente");
			Date indsætningsdato = resultset.getDate("indsætningsdato");
			int id = resultset.getInt("id");
			BigDecimal renteinBD = BigDecimal.valueOf(rente);
			if(id==1){
			Rente renten = new MånedligRente(renteinBD,indsætningsdato,kontonummer);
			return renten;
			}
			if(id==2){
			Rente renten = new ÅrligRente(renteinBD,indsætningsdato,kontonummer);
			return renten;
			}
			
		}
		System.out.println("kontoen har ikke en rente");
		return null;
	}
	public Login findLogin(String brugernavn) throws SQLException {
		System.out.println("Finder loginnet med brugernavn: " + brugernavn);
		List<Login> loginlist = listLogins();
		for (int i = 0; i <= loginlist.size(); i++) {
			Login tmplogin = loginlist.get(i);
			if (tmplogin.getBrugernavn().equals(brugernavn)) {
				return tmplogin;
			}
		}
		System.out.println("Loginnet du prøvede at finde findes ikke.");
		return null;
	}

	public Kunde matchkundemedlogin(Login bruger) throws SQLException {
		System.out.println("finder kunden med login " + bruger);
		List<Kunde> kundeliste = listKunder();
		for (int i = 0; i < kundeliste.size();) {
			Kunde tmpkunde = kundeliste.get(i);
			if (tmpkunde.getBrugernavn().equals(bruger.getBrugernavn())) {
				System.out.println("matchede login " + bruger + " med Kunde " + tmpkunde);
				return tmpkunde;
			}
			else 
				System.out.println(tmpkunde+" matchede ikke "+bruger);
				i++;
		}
		return null;
	}

	public Kunde matchkundemedlogin(String brugernavn) throws SQLException {
		System.out.println("finder kunden med brugernavn: " + brugernavn);
		List<Kunde> kundeliste = listKunder();
		for (int i = 0; i < kundeliste.size(); i++) {
			Kunde tmpkunde = kundeliste.get(i);
			if (tmpkunde.getBrugernavn().equals(brugernavn)) {
				System.out.println("matchede " + brugernavn + " med Kunde " + tmpkunde);
				return tmpkunde;
			}
			else System.out.println("det var ikke "+tmpkunde.getBrugernavn());
		}
		return null;
	}
	public void findLogin() throws SQLException {
		System.out.println("finder logins");
		start();
		statement = connection.prepareStatement("SELECT brugernavn,adgangskode FROM login");
		resultset = statement.executeQuery();
		System.out.println("Fandt logins: ");
		while (resultset.next()) {
			String brugernavn = resultset.getString("brugernavn");
			String adgangskode = resultset.getString("adgangskode");

			System.out.println("Brugernavn: " + brugernavn + " adgangskode: " + adgangskode);
		}
		stop();
	}

	public void findKontoer() throws SQLException {
		System.out.println("Leder efter kontoer...");
		start();
		statement = connection.prepareStatement("Select ejer,kontoid from konto");
		resultset = statement.executeQuery();
		while (resultset.next()) {
			String ejer = resultset.getString("ejer");
			String id = resultset.getString("kontoid");
			System.out.println("fandt " + ejer + "s konto, med id: " + id);
		}
		stop();

	}

	public Konto findKonto(int kontoid) throws SQLException {
		System.out.println("Leder efter kontoen med id: " + kontoid);
		start();
		statement = connection.prepareStatement("Select ejer,saldo,kontoid from konto WHERE kontoid= ?");
		statement.setInt(1, kontoid);
		resultset = statement.executeQuery();
		while (resultset.next()) {
			String ejer = resultset.getString("ejer");
			double saldo = resultset.getDouble("saldo");
			String id = resultset.getString("kontoid");
			BigDecimal saldoasBD = BigDecimal.valueOf(saldo);
			Konto tmpkonto = new Konto(findKunde(ejer), saldoasBD);
			System.out.println("fandt " + ejer + "s konto, med saldo: " + saldo + "og id:" + id);
			return tmpkonto;
		}

		stop();
		System.out.println("Noget gik galt da jeg skulle finde kontoid " + kontoid);
		return null;
	}

	public void findKunder() throws SQLException {
		start();
		statement = connection.prepareStatement("SELECT navn FROM kunde");
		resultset = statement.executeQuery();
		System.out.println("Fandt kunder: ");
		while (resultset.next()) {
			String stmp = resultset.getString("navn");
			System.out.println(stmp);
		}
		stop();
	}

	public Kunde findKunde(String s) throws SQLException {
		System.out.println("Finder kunder hvis navn indeholder: " + s);
		start();
		statement = connection.prepareStatement("SELECT navn, email, brugernavn FROM kunde WHERE kunde =");
		statement.setString(1, "%" + s + "%");
		resultset = statement.executeQuery();

		System.out.println("Fandt kunder: ");
		while (resultset.next()) {
			String stmp = resultset.getString("navn");
			String email = resultset.getString("email");
			String brugernavn = resultset.getString("brugernavn");
			Kunde tmpkunde = new Kunde(stmp, email, brugernavn);
			System.out.println("Fandt: " + stmp);
			stop();
			return tmpkunde;

		}
		return null;

	}

	public Kunde mailtoKunde(String email) throws SQLException {
		System.out.println("Finder kunden med email: " + email);
		start();
		statement = connection.prepareStatement("Select navn, email, brugernavn FROM kunde WHERE email LIKE ?");
		statement.setString(1, "%" + email + "%");
		resultset = statement.executeQuery();
		while (resultset.next()) {
			String navn = resultset.getString("navn");
			String brugernavn = resultset.getString("brugernavn");
			Kunde tmpkunde = new Kunde(navn, email, brugernavn);
			System.out.println("Fandt: " + tmpkunde);
			return tmpkunde;
		}
		System.out.println("Jeg burde ikke være noget her til");
		return null;
	}

	public int getLoginID(String brugernavn, String adgangskode) throws SQLException {
		System.out.println("Finder matchence login id til brugernavnet: " + brugernavn);
		start();
		statement = connection.prepareStatement("select brugernavn, adgangskode , id FROM login");
		resultset = statement.executeQuery();
		while (resultset.next()) {
			brugernavn = resultset.getString("brugernavn");
			adgangskode = resultset.getString("adgangskode");
			int id = resultset.getInt("id");

			if (brugernavn.equals(resultset.getString("brugernavn"))
					&& adgangskode.equals(resultset.getString("adgangskode"))) {
				System.out.println("Fandt id: " + id);
				return id;
			}
			stop();
		}
		return 69;
	}

	// LIST METODER:
	public List<Ændring> listændringer() throws SQLException{
		List<Ændring> ændringerlist = new ArrayList<>();
		start();
		statement = connection.prepareStatement("select indførelsesdato, statement from ændring");
		resultset = statement.executeQuery();
		while(resultset.next()){
			Date indførelsesdato = resultset.getDate("indførelsesdato");
			String statement2 = resultset.getString("statement");
			PreparedStatement statement = connection.prepareStatement(statement2);
			Ændring tmpændring = new Ændring(indførelsesdato,statement);
			ændringerlist.add(tmpændring);			
		}
		return ændringerlist;
	}
	
	public List<Kontakt> listkontakter(Kunde ejer) throws SQLException {
		System.out.println("lister kontakter...");
		List<Kontakt> kontaktlist = new ArrayList<>();
		start();
		statement = connection.prepareStatement("select navn, kontonr, ejer from kontakt where ejer= ?");
		statement.setString(1, ejer.getBrugernavn());
		resultset = statement.executeQuery();
		while (resultset.next()) {
			String navn = resultset.getString("navn");
			int kontonr = resultset.getInt("kontonr");
			Kontakt tmpkontakt = new Kontakt(navn, kontonr);
			kontaktlist.add(tmpkontakt);
			System.out.println("tilføjede " + tmpkontakt + " til listen");

		}
		System.out.println("done");
		return kontaktlist;
	}

	public List<Postering> listPostering() throws SQLException {
		System.out.println("Finder alle posteringer...");
		List<Postering> posteringslist = new ArrayList<>();
		start();
		statement = connection.prepareStatement("select sender, modtager, sendt, beløb from postering");
		resultset = statement.executeQuery();

		while (resultset.next()) {
			int senderkontonr = resultset.getInt("sender");
			int modtagerskontonr = resultset.getInt("modtager");
			Date startdato = resultset.getDate("sendt");
			double beløb = resultset.getDouble("beløb");
			BigDecimal beløbinBD = BigDecimal.valueOf(beløb);
			
			Postering tmppostering = new Postering(senderkontonr, modtagerskontonr, startdato, beløbinBD);
			posteringslist.add(tmppostering);
		}
		return posteringslist;
	}

	public List<Postering> listPostering(Konto konto) throws SQLException {
		System.out.println("Finder posteringer på "+konto);
		List<Postering> posteringslist = new ArrayList<>();
		start();
		statement = connection.prepareStatement("select sender, modtager, sendt, beløb from postering where sender=?");
		statement.setInt(1, konto.getKontonummer());
		resultset = statement.executeQuery();
		while (resultset.next()) {
			int sender = resultset.getInt("sender");
			int modtager = resultset.getInt("modtager");
			Date startdato = resultset.getDate("sendt");
			double beløb = resultset.getDouble("beløb");
			BigDecimal beløbinBD = BigDecimal.valueOf(beløb);
			Postering tmppostering = new Postering(sender, modtager, startdato, beløbinBD);
			System.out.println("fandt postering: "+tmppostering);
			posteringslist.add(tmppostering);
		}
		stop();
		start();
		statement = connection.prepareStatement("select sender, modtager, sendt, beløb from postering where modtager=?");
		statement.setInt(1, konto.getKontonummer());
		resultset = statement.executeQuery();
		while (resultset.next()) {
			int sender = resultset.getInt("sender");
			int modtager = resultset.getInt("modtager");
			Date startdato = resultset.getDate("sendt");
			double beløb = resultset.getDouble("beløb");
			BigDecimal beløbinBD = BigDecimal.valueOf(beløb);
			Postering tmppostering = new Postering(sender, modtager, startdato, beløbinBD);
			System.out.println("fandt postering: "+tmppostering);
			posteringslist.add(tmppostering);
		}
		return posteringslist;
	}
	public List<Postering> listallePosteringer() throws SQLException {
		System.out.println("lister alle posteringer...");
		List<Postering> posteringslist = new ArrayList<>();
		start();
		statement = connection.prepareStatement("select sender, modtager, sendt, beløb from postering");
		resultset = statement.executeQuery();
		while (resultset.next()) {
			int sender = resultset.getInt("sender");
			int modtager = resultset.getInt("modtager");
			Date startdato = resultset.getDate("sendt");
			double beløb = resultset.getDouble("beløb");
			BigDecimal beløbinBD = BigDecimal.valueOf(beløb);
			Postering tmppostering = new Postering(sender, modtager, startdato, beløbinBD);
			System.out.println("fandt postering: "+tmppostering);
			posteringslist.add(tmppostering);
		}
		return posteringslist;
	}

	public List<Konto> listkonti(Kunde ejer) throws SQLException {
		System.out.println("finder " + ejer + "s kontoer");

		List<Konto> kontolist = new ArrayList<>();
		start();
		statement = connection.prepareStatement("select ejer,kontoid,saldo,startdato,slutdato from konto where ejer = ?");
		statement.setString(1, ejer.getNavn());
		statement.execute();
		resultset = statement.executeQuery();
		while (resultset.next()) {
			int kontonummer = resultset.getInt("kontoid");
			double saldo = resultset.getDouble("saldo");
			BigDecimal saldoinBD = BigDecimal.valueOf(saldo);
			Date startdato = resultset.getDate("startdato");
			Date slutdato = resultset.getDate("slutdato");
			
			Konto tmpKonto = new Konto(ejer, saldoinBD);
			tmpKonto.setStartdato(startdato);
			tmpKonto.setSlutdato(slutdato);
			
			tmpKonto.setKontonummer(kontonummer);

			
			kontolist.add(tmpKonto);
			System.out.println("fandt og listede: " + tmpKonto.toString());
		}
		return kontolist;
	}

	public List<Konto> listAlleKontis() throws SQLException {
		System.out.println("finder alle kontis");
		List<Konto> kontolist = new ArrayList<>();
		start();
	
		statement = connection.prepareStatement("select ejer, saldo,kontoid,startdato,slutdato,kontoid from konto");
		resultset = statement.executeQuery();
		while(resultset.next()){
			String ejer = resultset.getString("ejer");
			double saldo = resultset.getDouble("saldo");
			BigDecimal saldoinBD = BigDecimal.valueOf(saldo);
			Date startdato = resultset.getDate("startdato");
			Date slutdato = resultset.getDate("slutdato");
			int id = resultset.getInt("kontoid");
			
			Konto tmpkonto = new Konto();
			tmpkonto.setEjernavn(ejer);
			tmpkonto.setSaldo(saldoinBD);
			tmpkonto.setEjernavn(ejer);
			tmpkonto.setStartdato(startdato);
			tmpkonto.setSlutdato(slutdato);
			tmpkonto.setKontonummer(id);
			kontolist.add(tmpkonto);
		}
		
		
		System.out.println("Done!");
		return kontolist;
	}

	public List<Kunde> listKunder() throws SQLException {
		System.out.println("Laver en liste over alle kunder");
		List<Kunde> kundeliste = new ArrayList<>();
		start();
		statement = connection.prepareStatement("Select navn, email, startdato,slutdato, brugernavn from kunde");
		resultset = statement.executeQuery();

		while (resultset.next()) {
			String navn = resultset.getString("navn");
			String email = resultset.getString("email");
			String brugernavn = resultset.getString("brugernavn");
			Date startdato = resultset.getDate("startdato");
			Date slutdato = resultset.getDate("slutdato");
			
			if(startdato.toLocalDate().isAfter(LocalDate.now()) && slutdato.toLocalDate().isBefore(LocalDate.now()));
			
			Kunde tmpKunde = new Kunde(navn, email, brugernavn);
			tmpKunde.setSlutdato(slutdato);
			tmpKunde.setStartdato(startdato);
			kundeliste.add(tmpKunde);

			System.out.println("tilføjede " + tmpKunde.toString() + " Til listen");
		}
		return kundeliste;
	}
	public List<Login> listLogins() throws SQLException {
		System.out.println("Laver en liste over alle logins");
		List<Login> loginlist = new ArrayList<>();
		start();
		statement = connection.prepareStatement("select brugernavn, adgangskode, id, startdato, slutdato from login");
		resultset = statement.executeQuery();
		while (resultset.next()) {
			String brugernavn = resultset.getString("brugernavn");
			String adgangskode = resultset.getString("adgangskode");
			Date startdato = resultset.getDate("startdato");
			Date slutdato = resultset.getDate("slutdato");
			
			int id = resultset.getInt("id");
			if (id == 1) {
				Login login = new AdminLogin(brugernavn, adgangskode);
				login.setStartdato(startdato);
				login.setSlutdato(slutdato);
				loginlist.add(login);
				System.out
						.println("Tilføjede Login med Brugernavn: " + brugernavn + "\n og adgangskode: " + adgangskode);
			}
			if (id == 2) {
				Login login = new NormaltLogin(brugernavn, adgangskode);
				loginlist.add(login);
				login.setStartdato(startdato);
				login.setSlutdato(slutdato);
				System.out
						.println("Tilføjede Login med Brugernavn: " + brugernavn + "\n og adgangskode: " + adgangskode+"til listen");
			}
		}
		return loginlist;
	}
	public List<FastOverførsel>  listfasteoverførsler(Login bruger) throws SQLException{
		System.out.println("laver en liste over "+bruger+"s faste overførsler");
		List<FastOverførsel> fastoverførselsliste = new ArrayList<>();
		List<Konto> brugerenskontoliste = listkonti(matchkundemedlogin(bruger));
		start();
		for(int i=0; i<brugerenskontoliste.size();i++)	{
			Konto tmpkonto = brugerenskontoliste.get(i);
			System.out.println("finder faste overførsler for "+tmpkonto);
			
			statement = connection.prepareStatement("select sender,modtager, beløb, startdato, id from fastoverførsel where sender=?");
			statement.setInt(1, tmpkonto.getKontonummer());
			resultset = statement.executeQuery();
			while(resultset.next()){
				int senderskontoid = resultset.getInt("sender");
				int modtagerskontoid = resultset.getInt("modtager");
				double beløb = resultset.getDouble("beløb");
				Date startdato = resultset.getDate("startdato");
				int id = resultset.getInt("id");
				FastOverførsel tmpfastoverførsel = new FastOverførsel(senderskontoid,modtagerskontoid,BigDecimal.valueOf(beløb),
						startdato,id);
				System.out.println("fandt"+tmpfastoverførsel);
				fastoverførselsliste.add(tmpfastoverførsel);
			
				}
			}
		return fastoverførselsliste;	
	}
	public List<Rente> listrenter() throws SQLException{
		List<Rente> rentelist = new ArrayList<>();
		System.out.println("lister renter");
		start();
		statement = connection.prepareStatement("select rente,indsætningsdato,kontonummer,id from renter");
		resultset = statement.executeQuery();
		while(resultset.next()){
			double rente = resultset.getDouble("rente");
			Date indsætningsdato = resultset.getDate("indsætningsdato");
			int kontonummer = resultset.getInt("kontonummer");
			int id = resultset.getInt("id");
			BigDecimal renteinBD = BigDecimal.valueOf(rente);
			if(id==1){
			rentelist.add(new MånedligRente(renteinBD,indsætningsdato,kontonummer));
			}
			if(id==2){
			rentelist.add(new ÅrligRente(renteinBD,indsætningsdato,kontonummer));
			}
		}
		return rentelist;
	}
	// KONTROL METODER:

	public boolean checkLogin(String brugernavn, String adgangskode) throws SQLException {
		System.out.println("checker login...\n");
		start();
		statement = connection.prepareStatement("select brugernavn, adgangskode , id FROM login");
		resultset = statement.executeQuery();

		while (resultset.next()) {
			if (brugernavn.equals(resultset.getString("brugernavn"))
					&& adgangskode.equals(resultset.getString("adgangskode"))) {
				System.out.println("DB.Checkuser: Brugernavn: \"" + brugernavn + "\" med adgangskode: \"" + adgangskode
						+ "\" er godkendt");
				return true;
			}

		}
		System.out.println("DB.Checkuser: Brugernavn: \"" + brugernavn + "\" med adgangskode: \"" + adgangskode
				+ "\" blev afvist");

		stop();
		return false;
	}

	public boolean checkBrugernavn(String brugernavn) throws SQLException {
		System.out.println("Checker brugernavnet: " + brugernavn);
		statement = connection.prepareStatement("select brugernavn from login");
		resultset = statement.executeQuery();

		while (resultset.next()) {
			if (brugernavn.equals(resultset.getString("brugernavn"))) {
				return true;
			}

		}
		return false;
	}



	public String nyKode(String brugernavn, String nyadgangskode) throws SQLException {
		System.out.println("Finder bruger... \n");
		start();
		if (checkBrugernavn(brugernavn) == true) {
			statement = connection.prepareStatement("update login set adgangskode=? where brugernavn=?");
			statement.setString(1, nyadgangskode);
			statement.setString(2, brugernavn);
			statement.execute();
			System.out.println("ændrede adgangskoden for brugernavn: " + brugernavn + " til: " + nyadgangskode);
			if (checkLogin(brugernavn, nyadgangskode) == true) {
				return "Dit kodeord er nu opdateret - og du kan logge ind!";
			} else
				return "Der skete en teknisk fejl!";
		}
		return "Brugernavn ikke fundet!";

	}
	public void hæv(long amount, int kontoid) throws SQLException{
		start();
		PreparedStatement statementGetSaldoFromDb; //sql statement variable
		statementGetSaldoFromDb = connection.prepareStatement("select saldo from konto where kontoid=?"); //sql query to get saldo from kontoid
		statementGetSaldoFromDb.setInt(1, kontoid); //set the '?' parameter to kontoid
		resultset = statementGetSaldoFromDb.executeQuery(); //execute query and get result
		resultset.next();
		
		//add amount to saldo from db
		double saldo = resultset.getDouble("saldo"); //saldo from db
		
		logic = new Logic();
		double newSaldo = Double.parseDouble(logic.subtract(BigDecimal.valueOf(saldo), BigDecimal.valueOf(amount)).toString());
		
		//update db with the new saldo
		statement = connection.prepareStatement("update konto set saldo=? where kontoid=?"); //sql statement variable
		statement.setDouble(1, newSaldo); //set the 1st '?' parameter to newSaldo
		statement.setInt(2, kontoid); //set the 1st '?' parameter to newkontoid
		
		statement.execute(); //execute query (updates saldo in db)
		
		stop();
	}
	
	public void insertMoney(long amount, int kontoid) throws SQLException{
		
		//get current saldo from db from kontoid

		start();
		PreparedStatement statementGetSaldoFromDb; //sql statement variable
		statementGetSaldoFromDb = connection.prepareStatement("select saldo from konto where kontoid=?"); //sql query to get saldo from kontoid
		statementGetSaldoFromDb.setInt(1, kontoid); //set the '?' parameter to kontoid
		resultset = statementGetSaldoFromDb.executeQuery(); //execute query and get result
		resultset.next();
		
		//add amount to saldo from db
		double saldo = resultset.getDouble("saldo"); //saldo from db
		
		logic = new Logic();
		double newSaldo = Double.parseDouble(logic.add(BigDecimal.valueOf(saldo), BigDecimal.valueOf(amount)).toString());
		
		//update db with the new saldo
		statement = connection.prepareStatement("update konto set saldo=? where kontoid=?"); //sql statement variable
		statement.setDouble(1, newSaldo); //set the 1st '?' parameter to newSaldo
		statement.setInt(2, kontoid); //set the 1st '?' parameter to newkontoid
		
		statement.execute(); //execute query (updates saldo in db)
		
		stop();
	}

	public void transfer(int senderskontoid, int modtagerskontoid, BigDecimal beløb) throws SQLException {
		PreparedStatement statement2;
		logic = new Logic();
		Date dato = Date.valueOf(LocalDateTime.now().toLocalDate());
		System.out.println("forsøger at Overføre " + beløb + "kr til konto med id" + modtagerskontoid + " fra konto med id "
				+ senderskontoid);
		BigDecimal nyesaldo = logic.add((BigDecimal.valueOf(getSaldo(modtagerskontoid))), beløb);
		
		start();
		statement = connection.prepareStatement("UPDATE konto SET saldo=? WHERE kontoid=?");
		statement.setDouble(1, nyesaldo.doubleValue());
		statement.setInt(2, modtagerskontoid);
		statement.execute();
		System.out.println("tilførte " + beløb + " til konto med id " + modtagerskontoid + "s konto");
		
		nyesaldo = logic.subtract((BigDecimal.valueOf(getSaldo(senderskontoid))), beløb);
		System.out.println("træk " + beløb + " fra konto med id " + senderskontoid + " konto");
		
		statement2 = connection.prepareStatement("UPDATE konto SET saldo=? WHERE kontoid=?");
		statement2.setDouble(1, nyesaldo.doubleValue());
		statement2.setInt(2, senderskontoid);
		statement2.execute();

		Postering postering = new Postering(senderskontoid, modtagerskontoid, dato, beløb);
		
		addPostering(postering);

		stop();

	}

	public Double getSaldo(int kontoid) throws SQLException {
		System.out.println("finder saldoen på konto med id " + kontoid);

		start();
		statement = connection.prepareStatement("Select saldo, ejer from konto WHERE kontoid=?");
		statement.setInt(1, kontoid);
		resultset = statement.executeQuery();

		while (resultset.next()) {
			Double saldo = resultset.getDouble("saldo");
			System.out.println("fandt saldoen "+saldo+"på kontoid: "+kontoid);
			return saldo;
		}

		System.out.println("no saldo was found");
		stop();
		return null;
	}

	public void fastoverførsel(Date startdato, int senderskontoid, int modtagerskontoid, double beløb, int id)
			throws SQLException {
		start();
		System.out.println("Tilfører fast overførsel");
		statement = connection.prepareStatement("insert into fastoverførsel (sender,modtager,beløb,startdato,id) values (?,?,?,?,?)");
		statement.setInt(1, senderskontoid);
		statement.setInt(2, modtagerskontoid);
		statement.setDouble(3, beløb);
		statement.setDate(4, startdato);
		statement.setInt(5, id);
		statement.execute();
		if (id == 1) {
			System.out.println("oprettede daglig overførsel på " + beløb + "kr fra " + senderskontoid + " til " + modtagerskontoid
					+ " med startdato: " + startdato);
		}
		if (id == 2) {
			System.out.println("oprettede ugentlig overførsel på " + beløb + "kr fra " + senderskontoid + " til " + modtagerskontoid
					+ " med startdato: " + startdato);
		}
		if (id == 3) {
			System.out.println("oprettede månedlig overførsel på " + beløb + "kr fra " + senderskontoid + " til " + modtagerskontoid
					+ " med startdato: " + startdato);

		}
		if (id == 4) {
			System.out.println("oprettede årlig overførsel på " + beløb + "kr fra " + senderskontoid + " til " + modtagerskontoid
					+ " med startdato: " + startdato);
		}
	}
	private void updaterændringer()throws SQLException{
	LocalDate nu = LocalDate.now();
	List<Ændring> ændringerlist = new ArrayList<>();
	for(int i=0; i<ændringerlist.size();i++){
		if(nu.isAfter(ændringerlist.get(i).getIndførelsesdato().toLocalDate())){
			executeÆndring(ændringerlist.get(i));
		}
		
	}
	}
	private void updaterrenter(List<Rente> renter) throws SQLException{
		LocalDate nu = LocalDate.now();
		PreparedStatement statement2;
		PreparedStatement statement3;
		Date næsteindsætningsdato;
		
		
		for(int i=0;i<renter.size();i++){
		Date indsætningsdato = renter.get(i).getIndsætningsdato();
		
		
		if(nu.isAfter(indsætningsdato.toLocalDate())){
			if(renter.get(i).getid()==1){
			næsteindsætningsdato = Date.valueOf(indsætningsdato.toLocalDate().plusMonths(1));
			System.out.println("din næste rente bliver udbetalt på dato: "+næsteindsætningsdato);
			}
			else{
			næsteindsætningsdato = Date.valueOf(indsætningsdato.toLocalDate().plusYears(2));		
			System.out.println("din næste rente bliver udbetalt på dato: "+næsteindsætningsdato);
			}
		
		System.out.println("Indsætter rente "+renter.get(i).getRente()+" på kontonummer: "+renter.get(i).getKontonummer());
		
		
		statement = connection.prepareStatement("select saldo from konto where kontoid=?");
		resultset = statement.executeQuery();
		statement.executeQuery();
		
		double saldo = resultset.getDouble("saldo");
		System.out.println("Gamle saldo: "+saldo);
		
		BigDecimal saldoinBD = BigDecimal.valueOf(saldo);
		BigDecimal nyesaldoinBD = logic.indsætrente(saldoinBD, renter.get(i).getRente());
		double nyesaldo = nyesaldoinBD.doubleValue();
		
		System.out.println("Nye saldo: "+nyesaldo);
		
		statement2 = connection.prepareStatement("update konto set saldo=? where kontoid=?");
		statement2.setDouble(1, nyesaldo);
		statement2.setInt(2, renter.get(i).getKontonummer());
		statement2.execute();
		
		System.out.println("næste indbetaling af rente på denne konto: "+næsteindsætningsdato);
		
		statement3 = connection.prepareStatement("update renter set indsætnindsdato=? where kontonummer=?");
		statement3.setDate(1, næsteindsætningsdato);
		statement3.setInt(2, renter.get(i).getKontonummer());
		statement3.execute();
		
		System.out.println("done!");
		
			}
		}
	}

	private void updatefasteoverførsler() throws SQLException {
		System.out.println("Updatere fasteoverførsler...");
		List<LocalDate> overførselsdatoliste = new ArrayList<>();
		
		statement = connection.prepareStatement("select startdato,sender, modtager, beløb, id from fastoverførsel");
		resultset = statement.executeQuery();
		
		while (resultset.next()) {
			Date tmpstartdato = resultset.getDate("startdato");
			int modtager = resultset.getInt("sender");
			int sender = resultset.getInt("modtager");
			double beløb = resultset.getDouble("beløb");
			BigDecimal beløbinBD = BigDecimal.valueOf(beløb);
			int id = resultset.getInt("id");
			
			LocalDate overførselstdato = tmpstartdato.toLocalDate();
			overførselsdatoliste.add(overførselstdato);
			
			for (int i = 0; i < overførselsdatoliste.size(); i++) {
				LocalDate nu = LocalDate.now();

				if (nu.isAfter(overførselsdatoliste.get(i)) == true) {
					if (id == 1) {
						overførselsdatoliste.get(i).plusDays(1);
						transfer(sender, modtager, beløbinBD);
						System.out.println("overførte "+beløbinBD+" til "+ modtager +" fra "+sender+" som daglig overførsel");
					}
					if (id == 2) {
						overførselsdatoliste.get(i).plusWeeks(1);
						transfer(sender, modtager, beløbinBD);
						System.out.println("overførte "+beløbinBD+" til "+ modtager +" fra "+sender+" som ugentlig overførsel");
						
					}
					if (id == 3) {
						overførselsdatoliste.get(i).plusMonths(1);
						transfer(sender, modtager, beløbinBD);
						System.out.println("overførte "+beløbinBD+" til "+ modtager +" fra "+sender+" som månedlig overførsel");
						
					}
					if (id == 4) {
						overførselsdatoliste.get(i).plusMonths(3);
						transfer(sender, modtager, beløbinBD);
						System.out.println("overførte "+beløbinBD+" til "+ modtager +" fra "+sender+" som kvartalig overførsel");
						
					}
					if(id ==5){
						overførselsdatoliste.get(i).plusMonths(6);
						transfer(sender, modtager, beløbinBD);
						System.out.println("overførte "+beløbinBD+" til "+ modtager +" fra "+sender+" som halvårig overførsel");
						
					}
					if(id == 6){
						overførselsdatoliste.get(i).plusYears(1);
						transfer(sender,modtager,beløbinBD);
						System.out.println("overførte "+beløbinBD+" til "+ modtager +" fra "+sender+" som årlig overførsel");
						
					}
				}
				System.out.println("Done!");
			}		
			start();
		}
		
	}
	

	private void checkifdayhaspassed() throws SQLException {
		Long nu = System.currentTimeMillis();
		Long lastnuplusdag = getTimer();
		
		if (nu > lastnuplusdag) {
			System.out.println(nu+" > "+lastnuplusdag);
			nu += (3600000 * 24);
			setTimer(nu);
			System.out.println("nye tid: "+ nu);
			System.out.println("Holy shit, Der er gået en dag! Jeg skal lige updatere nogen kontoer, brb");
			updatefasteoverførsler();
			updaterrenter(listrenter());
			updaterændringer();
		}

		else {
			Long tidtilbage = lastnuplusdag - nu;
			double timertilbage = tidtilbage / (3600000);
			long minuttertilbage = -(long) timertilbage;
			
			System.out.println("Der er " + timertilbage + " timer, eller "+tidtilbage+"milisekunder indtil der er gået en dag");
		}

	}

	public void setTimer(Long timer) throws SQLException {
		String tmptimer = timer.toString();
		statement = connection.prepareStatement("update timer set tid=? where id=?");
		statement.setString(1, tmptimer);
		statement.setInt(2, 1);
		statement.execute();
	}

	public Long getTimer() throws SQLException {
		statement = connection.prepareStatement("select tid, id from timer");
		resultset = statement.executeQuery();
		while (resultset.next()) {
			String tmptimer = resultset.getString("tid");
			Long timer = Long.parseLong(tmptimer);
			return timer;
		}
		return null;

	}

	@Override
	public void stop() {
		try {
			connection.close();
			if (resultset != null) {
				resultset.close();
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
