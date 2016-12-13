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
import domain.Kontakt;
import domain.Konto;
import domain.Kunde;
import domain.Login;
import domain.NormaltLogin;
import domain.Postering;
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

	// INDSÆT METODER:
	public void addKontakt(Kontakt kontakt, String ejer) throws SQLException { // ejer
																				// ligger
																				// sig
																				// til
																				// brugernavn
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

	public void addKonto(String ejer, Double saldo) throws SQLException {
		System.out.println("tilfører konto...");
		start();
		statement = connection.prepareStatement("INSERT INTO konto (ejer,saldo) values(?,?)");
		statement.setString(1, ejer);
		statement.setDouble(2, saldo);
		statement.execute();
		System.out.println("Oprettede konto med ejer: " + ejer + "og saldo: " + saldo);
		stop();
	}

	public void addKunde(Kunde kunde) throws SQLException {
		System.out.println("tilfører kunde...");
		start();
		Date startdato = Date.valueOf(LocalDate.now());
		Date slutdato = Date.valueOf(LocalDate.of(9999, 01, 01));
		statement = connection
				.prepareStatement("insert into kunde(navn, startdato, slutdato, email,brugernavn) values (?,?,?,?,?)");
		statement.setString(1, kunde.getNavn());
		statement.setDate(2, startdato);
		statement.setDate(3, slutdato);
		statement.setString(4, kunde.getEmail());
		statement.setString(5, kunde.getBrugernavn());
		statement.execute();
		System.out.println("Tilførte kunde: " + kunde);
		stop();
	}

	public void addKonto(String ejer, BigDecimal saldo) throws SQLException {
		System.out.println("tilfører konto...");
		start();
		statement = connection.prepareStatement("insert into konto(ejer,saldo)values(?,?)");
		statement.setString(1, ejer);
		statement.setDouble(2, saldo.doubleValue());
		statement.execute();
		System.out.println("konto med ejer " + ejer + "blev lagt ind i databasen");
		stop();
	}

	public void addLogin(Login login) throws SQLException {
		System.out.println("tilfører login...");
		start();
		statement = connection.prepareStatement("INSERT INTO login (brugernavn, adgangskode, id) values(?,?,?)");
		statement.setString(1, login.getBrugernavn());
		statement.setString(2, login.getAdgangskode());
		statement.setInt(3, login.getId());
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
		System.out.println("finder kunden med brugernavn: " + bruger);
		List<Kunde> kundeliste = listKunder();
		for (int i = 0; i < kundeliste.size(); i++) {
			Kunde tmpkunde = kundeliste.get(i);
			if (tmpkunde.getBrugernavn().equals(bruger)) {
				System.out.println("matchede " + bruger + " med Kunde " + tmpkunde);
				return tmpkunde;
			}
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
		}
		return null;
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
			String id = resultset.getString("id");
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
	public List<Kontakt> listkontakter(Kunde ejer) throws SQLException {
		System.out.println("lister kontakter...");
		List<Kontakt> kontaktlist = new ArrayList<>();
		start();
		statement = connection.prepareStatement("select navn, kontonr, ejer from kontakt where ejer like ?");
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
		System.out.println("Finder posteringer...");
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
		System.out.println("Finder posteringer...");
		List<Postering> posteringslist = new ArrayList<>();
		start();
		statement = connection
				.prepareStatement("select sender, modtager, sendt, beløb from postering where sender like ?");
		statement.setString(1, konto.getEjer().getNavn());
		resultset = statement.executeQuery();
		while (resultset.next()) {
			int sender = resultset.getInt("sender");
			int modtager = resultset.getInt("modtager");
			Date startdato = resultset.getDate("sendt");
			double beløb = resultset.getDouble("beløb");
			BigDecimal beløbinBD = BigDecimal.valueOf(beløb);
			Postering tmppostering = new Postering(sender, modtager, startdato, beløbinBD);
			posteringslist.add(tmppostering);
		}
		return posteringslist;
	}

	public List<Konto> listkonti(Kunde ejer) throws SQLException {
		System.out.println("finder " + ejer + "s kontoer");

		List<Konto> kontolist = new ArrayList<>();
		start();
		statement = connection.prepareStatement("select ejer,kontoid,saldo from konto where ejer = ?");
		statement.setString(1, ejer.getNavn());
		statement.execute();
		resultset = statement.executeQuery();
		while (resultset.next()) {
			int kontonummer = resultset.getInt("kontoid");
			double saldo = resultset.getDouble("saldo");
			BigDecimal saldoinBD = BigDecimal.valueOf(saldo);

			Konto tmpKonto = new Konto(ejer, saldoinBD);
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

		List<Kunde> kundeliste = listKunder();
		
		for (int i = 0; i < kundeliste.size();i++) {
			Kunde tmpkunde = kundeliste.get(i);
			List<Konto> tmpList = listkonti(tmpkunde);
			 
			for (int j = 0; j < tmpList.size(); j++) {
				Konto tmpKonto = tmpList.get(j);
				kontolist.add(tmpKonto);
				
				System.out.println("tilføjede : " + tmpkunde.toString() + "s "+tmpKonto+" til listen");
			}
			
		}
		System.out.println("Done!");
		return kontolist;
	}

	public List<Kunde> listKunder() throws SQLException {
		System.out.println("Laver en liste over alle kunder");
		List<Kunde> kundeliste = new ArrayList<>();
		start();
		statement = connection.prepareStatement("Select navn, email, startdato, brugernavn from kunde");
		resultset = statement.executeQuery();

		for(int i=0;i<= kundeliste.size();i++){
		
		while (resultset.next()) {
			String navn = resultset.getString("navn");
			String email = resultset.getString("email");
			String brugernavn = resultset.getString("brugernavn");
			Kunde tmpKunde = new Kunde(navn, email, brugernavn);
			kundeliste.add(tmpKunde);

			System.out.println("tilføjede " + tmpKunde.toString() + " Til listen");
		}
		}
		return kundeliste;
	}

	public List<Login> listLogins() throws SQLException {
		System.out.println("Laver en liste over alle logins");
		List<Login> logintable = new ArrayList<>();
		start();
		statement = connection.prepareStatement("select brugernavn, adgangskode, id from login");
		resultset = statement.executeQuery();
		while (resultset.next()) {
			String brugernavn = resultset.getString("brugernavn");
			String adgangskode = resultset.getString("adgangskode");
			int id = resultset.getInt("id");
			if (id == 1) {
				Login login = new AdminLogin(brugernavn, adgangskode);
				logintable.add(login);
				System.out
						.println("Tilføjede Login med Brugernavn: " + brugernavn + "\n og adgangskode: " + adgangskode);
			}
			if (id == 2) {
				Login login2 = new NormaltLogin(brugernavn, adgangskode);
				logintable.add(login2);
				System.out
						.println("Tilføjede Login med Brugernavn: " + brugernavn + "\n og adgangskode: " + adgangskode+"til listen");
			}
		}
		return logintable;
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

	public void transfer(int senderskontoid, int modtagerskontoid, BigDecimal beløb) throws SQLException {
		PreparedStatement statement2;
		logic = new Logic();
		Date dato = Date.valueOf(LocalDateTime.now().toLocalDate());
		BigDecimal nyesaldo = logic.add((BigDecimal.valueOf(getSaldo(modtagerskontoid))), beløb);
		
		System.out.println("forsøger at Overføre " + beløb + "kr til konto med id" + modtagerskontoid + " fra konto med id "
				+ senderskontoid);
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
		BigDecimal inversemultiplicand = BigDecimal.valueOf(-1);
		beløb.multiply(inversemultiplicand);
		
		Postering senderenspostering = new Postering(modtagerskontoid, senderskontoid, dato, beløb);
		Postering modtagerenspostering = new Postering(senderskontoid, modtagerskontoid, dato, beløb);
		
		addPostering(senderenspostering);
		addPostering(modtagerenspostering);
		
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

	public void fastoverførsel(Date slutdato, String sender, String modtager, double beløb, int id)
			throws SQLException {
		start();
		System.out.println("Tilfører fast overførsel");
		statement = connection.prepareStatement(
				"insert into fastoverførsel (sender,modtager,beløb,slutdato,id) values " + "values(?,?,?,?,?)");
		statement.setString(1, sender);
		statement.setString(2, modtager);
		statement.setDouble(3, beløb);
		statement.setDate(4, slutdato);
		statement.setInt(5, id);
		statement.execute();
		if (id == 1) {
			System.out.println("oprettede daglig overførsel på " + beløb + "kr fra " + sender + " til " + modtager
					+ " med startdato: " + slutdato);
		}
		if (id == 2) {
			System.out.println("oprettede ugentlig overførsel på " + beløb + "kr fra " + sender + " til " + modtager
					+ " med startdato: " + slutdato);
		}
		if (id == 3) {
			System.out.println("oprettede månedlig overførsel på " + beløb + "kr fra " + sender + " til " + modtager
					+ " med startdato: " + slutdato);

		}
		if (id == 4) {
			System.out.println("oprettede årlig overførsel på " + beløb + "kr fra " + sender + " til " + modtager
					+ " med startdato: " + slutdato);
		}
	}

	private void updatefasteoverførsler() throws SQLException {
		System.out.println("Updatere fasteoverførsler...");
		DB db = new DB();
		System.out.println("lister overførsels datoer");
		List<LocalDate> slutdatoliste = new ArrayList<>();
		statement = connection.prepareStatement("select slutdato,sender, modtager, beløb, id from fastoverførsel");
		resultset = statement.executeQuery();
		while (resultset.next()) {
			Date tmpslutdato = resultset.getDate("slutdato");
			int modtager = resultset.getInt("sender");
			int sender = resultset.getInt("modtager");
			double beløb = resultset.getDouble("beløb");
			BigDecimal beløbinBD = BigDecimal.valueOf(beløb);
			int id = resultset.getInt("id");
			LocalDate slutdato = tmpslutdato.toLocalDate();
			slutdatoliste.add(slutdato);
			for (int i = 0; i <= slutdatoliste.size(); i++) {
				LocalDate nu = LocalDateTime.now().toLocalDate();

				if (nu.isAfter(slutdatoliste.get(i)) == true) {
					if (id == 1) {
						slutdatoliste.get(i).plusDays(1);
						transfer(sender, modtager, beløbinBD);
					}
					if (id == 2) {
						slutdatoliste.get(i).plusWeeks(1);
						transfer(sender, modtager, beløbinBD);
					}
					if (id == 3) {
						slutdatoliste.get(i).plusMonths(1);
						transfer(sender, modtager, beløbinBD);
					}
					if (id == 4) {
						slutdatoliste.get(i).plusMonths(3);
						transfer(sender, modtager, beløbinBD);
					}
					if(id ==5){
						slutdatoliste.get(i).plusMonths(6);
						transfer(sender, modtager, beløbinBD);
					}
					if(id == 6){
						slutdatoliste.get(i).plusYears(1);
						transfer(sender,modtager,beløbinBD);
					}
				}
				System.out.println("Done!");
			}
		}
	}

	private void checkifdayhaspassed() throws SQLException {
		Long nu = System.currentTimeMillis();
		Long lastnuplusdag = getTimer();
		if (nu > lastnuplusdag) {
			nu = nu + (3600000 * 24);
			setTimer(nu);
			System.out.println("Holy shit, Der er gået en dag! Jeg skal lige updatere nogen kontoer, brb");
			updatefasteoverførsler();
		}

		else {
			Long tidtilbage = lastnuplusdag - nu;
			double timertilbage = tidtilbage / (3600000);

			long minuttertilbage = -(long) timertilbage;
			System.out.println("Der er " + timertilbage + " timer indtil der er gået en dag");
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

}
