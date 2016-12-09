package DB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Interfaces.Startable;
import domain.AdminLogin;
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// INDSÆT METODER:
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
		statement = connection.prepareStatement("insert into kunde(navn, startdato, slutdato, email) values (?,?,?,?)");
		statement.setString(1, kunde.getNavn());
		statement.setDate(2, startdato);
		statement.setDate(3, slutdato);
		statement.setString(4, kunde.getEmail());
		statement.execute();
		System.out.println("Tilførte kunde: " + kunde);
		stop();
	}

	public void addKonto(Konto konto) throws SQLException {
		System.out.println("tilfører konto...");
		start();
		statement = connection.prepareStatement("insert into konto(ejer,saldo)values(?,?)");
		statement.setString(1, konto.getEjer().getNavn());
		statement.setDouble(2, konto.getSaldo());
		statement.execute();
		System.out.println("konto: " + konto + "blev lagt ind i databasen");
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

	// FIND METODER:

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

	public void findKonto(String s) throws SQLException {
		System.out.println("Leder efter kontoer til ejeren: " + s);
		start();
		statement = connection.prepareStatement("Select ejer,saldo,id from konto WHERE ejer LIKE ?");
		statement.setString(1, "%" + s + "%");
		resultset = statement.executeQuery();
		while (resultset.next()) {
			String ejer = resultset.getString("ejer");
			String saldo = resultset.getString("saldo");
			String id = resultset.getString("id");

			System.out.println("fandt " + ejer + "s konto, med saldo: " + saldo + "og id:" + id);
		}

		stop();
		System.out.println("Noget gik galt da jeg skulle finde " + s + "s konto");

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

	public void findKunde(String s) throws SQLException {
		System.out.println("Finder kunder hvis navn indeholder: " + s);
		start();
		statement = connection.prepareStatement("SELECT navn, email FROM kunde WHERE kunde LIKE ?");
		statement.setString(1, "%" + s + "%");
		resultset = statement.executeQuery();

		System.out.println("Fandt kunder: ");
		while (resultset.next()) {
			String stmp = resultset.getString("navn");
			String email = resultset.getString("email");
			System.out.println("Fandt: " + stmp);
		}
		stop();
	}

	public Kunde mailtoKunde(String email) throws SQLException {
		System.out.println("Finder kunden med email: " + email);
		start();
		statement = connection.prepareStatement("Select navn, email FROM kunde WHERE email LIKE ?");
		statement.setString(1, "%" + email + "%");
		resultset = statement.executeQuery();
		while (resultset.next()) {
			String navn = resultset.getString("navn");

			Kunde tmpkunde = new Kunde(navn, email);
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

	// TABLE METODER:
	public List<Postering> listPostering() throws SQLException {
		System.out.println("Finder posteringer...");
		List<Postering> posteringslist = new ArrayList<>();
		start();
		statement = connection.prepareStatement("select sender, modtager, sendt, beløb from postering");
		resultset = statement.executeQuery();
		while (resultset.next()) {
			String sender = resultset.getString("sender");
			String modtager = resultset.getString("modtager");
			Date startdato = resultset.getDate("sendt");
			double beløb = resultset.getDouble("beløb");
			Postering tmppostering = new Postering(sender, modtager, startdato, beløb);
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
			String sender = resultset.getString("sender");
			String modtager = resultset.getString("modtager");
			Date startdato = resultset.getDate("sendt");
			double beløb = resultset.getDouble("beløb");
			Postering tmppostering = new Postering(sender, modtager, startdato, beløb);
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
			String kontonummer = resultset.getString("kontoid");
			double saldo = resultset.getDouble("saldo");
			Konto tmpKonto = new Konto(ejer, saldo);
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
		for (int i = 0; i <= kundeliste.size(); i++) {
			Kunde tmpkunde = kundeliste.get(i);
			List<Konto> tmpList = listkonti(tmpkunde);

			for (int j = 0; i <= tmpList.size(); i++) {
				Konto tmpKonto = tmpList.get(j);

				kontolist.add(tmpKonto);
			}
		}
		System.out.println("Done!");
		return kontolist;
	}

	public List<Kunde> listKunder() throws SQLException {
		System.out.println("Laver en liste over alle kunder");
		List<Kunde> kundeliste = new ArrayList<>();
		start();
		statement = connection.prepareStatement("Select navn, email, startdato from kunde");
		resultset = statement.executeQuery();

		while (resultset.next()) {
			String navn = resultset.getString("navn");
			String email = resultset.getString("email");

			Kunde tmpKunde = new Kunde(navn, email);
			kundeliste.add(tmpKunde);

			System.out.println("tilføjede " + tmpKunde.toString() + " Til listen");
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
						.println("Tilføjede Login med Brugernavn: " + brugernavn + "\n og adgangskode: " + adgangskode);
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

	public void transfer(String modtager, String sender, Double beløb) throws SQLException {
		System.out.println("Overfører " + beløb + "kr til " + modtager + " fra " + sender);
		logic = new Logic();
		start();
		statement = connection.prepareStatement("UPDATE konto SET saldo=? WHERE ejer=?");
		double nyesaldo = logic.add((getSaldo(modtager)), beløb);
		System.out.println("tilførte " + beløb + " til " + modtager + "s konto");
		statement.setDouble(1, nyesaldo);
		statement.setString(2, modtager);
		statement.execute();
		nyesaldo = logic.subtract((getSaldo(sender)), beløb);
		System.out.println("træk " + beløb + " fra " + sender + "s konto");
		statement.setDouble(1, nyesaldo);
		statement.setString(2, sender);
		statement.execute();
		stop();

	}

	public Double getSaldo(String kunde) throws SQLException {
		System.out.println("looking for " + kunde + "s saldo");
		start();
		statement = connection.prepareStatement("Select saldo from konto WHERE ejer=?");
		statement.setString(1, kunde);
		resultset = statement.executeQuery();
		while (resultset.next()) {
			Double saldo = resultset.getDouble("saldo");
			System.out.println(kunde + "s saldo er: " + saldo);
			return saldo;

		}
		System.out.println("no saldo was found");
		stop();
		return null;
	}

	public void fastOverførselprDag() throws SQLException {
		DB db = new DB();
		LocalDate kontol = LocalDate.of(0, 0, 0);
		System.out.println("lister overførsels datoer");
		List<LocalDate> slutdatoliste = new ArrayList<>();
		start();
		statement = connection.prepareStatement("select slutdato,sender, modtager, beløb from fastoverførsel");
		resultset = statement.executeQuery();
		while (resultset.next()) {
			Date tmpslutdato = resultset.getDate("slutdato");
			String sender = resultset.getString("sender");
			String modtager = resultset.getString("modtager");
			double beløb = resultset.getDouble("beløb");

			LocalDate slutdato = tmpslutdato.toLocalDate();
			slutdatoliste.add(slutdato);
			for (int i = 0; i <= slutdatoliste.size(); i++) {
				LocalDate nu = LocalDateTime.now().toLocalDate();

				if (nu.isAfter(slutdatoliste.get(i)) == true) {
					db.transfer(modtager, sender, beløb);
					slutdatoliste.get(i).plusDays(1);

				}

			}
		}
	}
	public void fastOverførselpruge() throws SQLException {
		DB db = new DB();
		LocalDate kontol = LocalDate.of(0, 0, 0);
		System.out.println("lister overførsels datoer");
		List<LocalDate> slutdatoliste = new ArrayList<>();
		start();
		statement = connection.prepareStatement("select slutdato,sender, modtager, beløb from fastoverførsel");
		resultset = statement.executeQuery();
		while (resultset.next()) {
			Date tmpslutdato = resultset.getDate("slutdato");
			String sender = resultset.getString("sender");
			String modtager = resultset.getString("modtager");
			double beløb = resultset.getDouble("beløb");

			LocalDate slutdato = tmpslutdato.toLocalDate();
			slutdatoliste.add(slutdato);
			for (int i = 0; i <= slutdatoliste.size(); i++) {
				LocalDate nu = LocalDateTime.now().toLocalDate();

				if (nu.isAfter(slutdatoliste.get(i)) == true) {
					db.transfer(modtager, sender, beløb);
					slutdatoliste.get(i).plusWeeks(1);

				}
			}
		}}
		public void fastOverførselprmåned() throws SQLException {
			DB db = new DB();
			LocalDate kontol = LocalDate.of(0, 0, 0);
			System.out.println("lister overførsels datoer");
			List<LocalDate> slutdatoliste = new ArrayList<>();
			start();
			statement = connection.prepareStatement("select slutdato,sender, modtager, beløb from fastoverførsel");
			resultset = statement.executeQuery();
			while (resultset.next()) {
				Date tmpslutdato = resultset.getDate("slutdato");
				String sender = resultset.getString("sender");
				String modtager = resultset.getString("modtager");
				double beløb = resultset.getDouble("beløb");

				LocalDate slutdato = tmpslutdato.toLocalDate();
				slutdatoliste.add(slutdato);
				for (int i = 0; i <= slutdatoliste.size(); i++) {
					LocalDate nu = LocalDateTime.now().toLocalDate();

					if (nu.isAfter(slutdatoliste.get(i)) == true) {
						db.transfer(modtager, sender, beløb);
						slutdatoliste.get(i).plusMonths(1);

					}

				}
			}
		}
		public void fastOverførselpryear() throws SQLException {
			DB db = new DB();
			LocalDate kontol = LocalDate.of(0, 0, 0);
			System.out.println("lister overførsels datoer");
			List<LocalDate> slutdatoliste = new ArrayList<>();
			start();
			statement = connection.prepareStatement("select slutdato,sender, modtager, beløb from fastoverførsel");
			resultset = statement.executeQuery();
			while (resultset.next()) {
				Date tmpslutdato = resultset.getDate("slutdato");
				String sender = resultset.getString("sender");
				String modtager = resultset.getString("modtager");
				double beløb = resultset.getDouble("beløb");

				LocalDate slutdato = tmpslutdato.toLocalDate();
				slutdatoliste.add(slutdato);
				for (int i = 0; i <= slutdatoliste.size(); i++) {
					LocalDate nu = LocalDateTime.now().toLocalDate();

					if (nu.isAfter(slutdatoliste.get(i)) == true) {
						db.transfer(modtager, sender, beløb);
						slutdatoliste.get(i).plusYears(1);

					}

				}
			}
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
