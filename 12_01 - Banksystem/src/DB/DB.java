package DB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Interfaces.Startable;
import domain.Konto;
import domain.Kunde;
import domain.Login;
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

	public Connection connect(Connection connection) throws SQLException {
		connection = DriverManager.getConnection(db, dbuser, dbpass);
		return connection;
	}

	public void addKonto(String ejer, Double saldo) throws SQLException {
		start();
		statement = connection.prepareStatement("INSERT INTO konto (ejer,saldo) values(?,?)");
		statement.setString(1, ejer);
		statement.setDouble(2, saldo);
		statement.execute();
		System.out.println("Oprettede konto med ejer: " + ejer + "og saldo: " + saldo);
		stop();
	}

	public void findKontoer() throws SQLException {
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
		start();
		statement = connection.prepareStatement("Select ejer,id from konto WHERE ejer LIKE ?");
		statement.setString(1, "%" + s + "%");
		resultset = statement.executeQuery();
		while (resultset.next()) {
			String ejer = resultset.getString("ejer");
			String id = resultset.getString("id");
			System.out.println("fandt " + ejer + "s konto, med id: " + id);
		}
		stop();

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
		start();
		statement = connection.prepareStatement("SELECT navn FROM kunde WHERE kunde LIKE ?");
		statement.setString(1, "%" + s + "%");
		resultset = statement.executeQuery();

		System.out.println("Fandt kunder: ");
		while (resultset.next()) {
			String stmp = resultset.getString("navn");
			System.out.println(stmp);
		}
		stop();
	}

	public void addKunde(Kunde kunde) throws SQLException {
		System.out.println("tilfører kunde... \n");
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
		System.out.println("tilfører konto... \n");
		start();
		statement = connection.prepareStatement("insert into konto(ejer,saldo)values(?,?)");
		statement.setString(1, konto.getEjer().getNavn());
		statement.setDouble(2, konto.getSaldo());
		statement.execute();
		System.out.println("konto: " + konto + "blev lagt ind i databasen");
		stop();
	}

	public List<Konto> listkonti(Kunde ejer) throws SQLException {
		List<Konto> kontolist = new ArrayList<>();
		System.out.println("finder " + ejer + "s kontoer");
		start();
		statement = connection.prepareStatement("select ejer,kontonummer,saldo from konto where ejer = ?");
		statement.setString(1, ejer.getNavn());
		statement.execute();
		resultset = statement.executeQuery();
		while (resultset.next()) {
			String kontonummer = resultset.getString("kontonummer");
			double saldo = resultset.getDouble("saldo");

			Konto tmpKonto = new Konto(ejer, saldo);
			tmpKonto.setKontonummer(kontonummer);
			kontolist.add(tmpKonto);
		}
		return kontolist;
	}

	public List<Kunde> listKunder() throws SQLException {
		List<Kunde> kundeliste = new ArrayList<>();
		System.out.println("Laver en liste over alle kunder");
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
			if (brugernavn.equals(resultset.getString("brugernavn")))
				return true;
			else
				return false;

		}
	return false;
	
	}

	public int getLoginID(String brugernavn, String adgangskode) throws SQLException {
		start();
		statement = connection.prepareStatement("select brugernavn, adgangskode , id FROM login");
		resultset = statement.executeQuery();
		while (resultset.next()) {
			brugernavn = resultset.getString("brugernavn");
			adgangskode = resultset.getString("adgangskode");
			int id = resultset.getInt("id");

			if (brugernavn.equals(resultset.getString("brugernavn"))
					&& adgangskode.equals(resultset.getString("adgangskode"))) {
				return id;
			}
			stop();
		}
		return 69;
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

	public void addLogin(Login login) throws SQLException {
		start();
		statement = connection.prepareStatement("INSERT INTO login (brugernavn, adgangskode, id) values(?,?,?)");
		statement.setString(1, login.getBrugernavn());
		statement.setString(2, login.getAdgangskode());
		statement.setInt(3, login.getId());
		statement.execute();
		System.out.println("Login: " + login + "blev lagt ind i databasen");
		stop();
	}

	public void findLogin() throws SQLException {
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

	public Connection getConnection() {
		return connection;
	}

	public void transfer(String modtager, String sender, Double beløb) throws SQLException {
		logic = new Logic();
		start();
		statement = connection.prepareStatement("UPDATE konto SET saldo=? WHERE ejer=?");
		double nyesaldo = logic.add(Double.parseDouble(getSaldo(modtager)), beløb);
		statement.setDouble(1, nyesaldo);
		statement.setString(2, modtager);
		statement.execute();
		nyesaldo = logic.subtract(Double.parseDouble(getSaldo(sender)), beløb);
		statement.setDouble(1, nyesaldo);
		statement.setString(2, sender);
		statement.execute();
		stop();

	}

	public String getSaldo(String kunde) throws SQLException {
		System.out.println("looking for saldo...");
		start();
		statement = connection.prepareStatement("Select saldo from konto WHERE ejer=?");
		statement.setString(1, kunde);
		resultset = statement.executeQuery();
		while (resultset.next()) {
			return resultset.getString("saldo");

		}
		System.out.println("no saldo was found");
		stop();
		return "";
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
