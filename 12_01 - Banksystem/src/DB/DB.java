package DB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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
	DB database = new DB();
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
	public void addKonto(String ejer, Double saldo) throws SQLException{
		database.start();
		statement = connection.prepareStatement("INSERT INTO konto (ejer,saldo) values(?,?)");
		statement.setString(1, ejer);
		statement.setDouble(2, saldo);
		statement.execute();
		System.out.println("Oprettede konto med ejer: " + ejer + "og saldo: " + saldo);
		database.stop();
	}
	public void findKontoer() throws SQLException{
		database.start();
		statement = connection.prepareStatement("Select ejer,id from konto");
		resultset = statement.executeQuery();
		while(resultset.next()){
				String ejer = resultset.getString("ejer");
				String id = resultset.getString("id");
				System.out.println("fandt "+ejer+"s konto, med id: "+id);
			}
		database.stop();
		
	}
	public void findKonto(String s) throws SQLException{
		database.start();
		statement = connection.prepareStatement("Select ejer,id from konto WHERE ejer LIKE ?");
		statement.setString(1, "%"+s+"%");
		resultset = statement.executeQuery();
		while(resultset.next()){
				String ejer = resultset.getString("ejer");
				String id = resultset.getString("id");
				System.out.println("fandt "+ejer+"s konto, med id: "+id);
			}
		database.stop();
		
	}
	
	public void findKunder() throws SQLException {
		database.start();
		statement = connection.prepareStatement("SELECT navn FROM kunde");
		resultset = statement.executeQuery();
		System.out.println("Fandt kunder: ");
		while (resultset.next()) {
			String stmp = resultset.getString("navn");
			System.out.println(stmp);
		}
		database.stop();
	}

	public void findKunde(String s) throws SQLException {
		database.start();
		statement = connection.prepareStatement("SELECT navn FROM kunde WHERE kunde LIKE ?");
		statement.setString(1, "%" + s + "%");
		resultset = statement.executeQuery();

		System.out.println("Fandt kunder: ");
		while (resultset.next()) {
			String stmp = resultset.getString("navn");
			System.out.println(stmp);
		}
		database.stop();
	}

	public void addKunde(Kunde kunde) throws SQLException {
		System.out.println("tilfører kunde");
		database.start();
		Date startdato = Date.valueOf(LocalDate.now());
		Date slutdato = Date.valueOf(LocalDate.of(9999, 01, 01));
		statement = connection.prepareStatement("insert into kunde(navn, startdato, slutdato) values (?,?,?)");
		statement.setString(1, kunde.getNavn());
		statement.setDate(2, startdato);
		statement.setDate(3, slutdato);
		statement.execute();
		System.out.println("Tilførte kunde: " + kunde);
		database.stop();
	}

	public void addKonto(Konto konto) throws SQLException {
		System.out.println("tilfører konto... \n");
		database.start();
		statement = connection.prepareStatement("insert into konto(ejer,kontonummer,saldo)values(?,?,?)");
		statement.setString(1, konto.getEjer().getNavn());
		statement.setString(2, konto.getKontonummer());
		statement.setDouble(3, konto.getSaldo());
		statement.execute();
		System.out.println("konto: " + konto + "blev lagt ind i databasen");
		database.stop();
	}

	public boolean checkLogin(String brugernavn, String adgangskode) throws SQLException {
		System.out.println("checker login...\n");
		database.start();
		statement = connection.prepareStatement("select brugernavn, adgangskode , id FROM login");
		resultset = statement.executeQuery();
		
			brugernavn = resultset.getString("brugernavn");
			adgangskode = resultset.getString("adgangskode");
			while (resultset.next()) {
			if (brugernavn.equals(resultset.getString("brugernavn")) && adgangskode.equals(resultset.getString("adgangskode"))) {
				System.out.println("Brugernavn:" +brugernavn + "med adgangskode:"+adgangskode +" er godkendt");
				return true;
			}
			System.out.println("Brugernavn:" +brugernavn + "med adgangskode:"+adgangskode +" blev afvist");

		}
			database.stop();
			return false;
	}

	public int getLoginID(String brugernavn, String adgangskode) throws SQLException {
		database.start();
		statement = connection.prepareStatement("select brugernavn, adgangskode , id FROM login");
		resultset = statement.executeQuery();
		while (resultset.next()) {
			brugernavn = resultset.getString("brugernavn");
			adgangskode = resultset.getString("adgangskode");
			int id = resultset.getInt("id");

			if (brugernavn.equals(resultset.getString("brugernavn")) && adgangskode.equals(resultset.getString("adgangskode"))) {
				return id;
			}
		database.stop();
		}
	return 69;
	}

	public void addLogin(Login login) throws SQLException {
		database.start();
		statement = connection.prepareStatement("INSERT INTO login (brugernavn, adgangskode, id) values(?,?,?)");
		statement.setString(1, login.getBrugernavn());
		statement.setString(2, login.getAdgangskode());
		statement.setInt(3, login.getId());
		statement.execute();
		System.out.println("Login: " + login + "blev lagt ind i databasen");
		database.stop();
	}

	public void findLogin() throws SQLException {
		database.start();
		statement = connection.prepareStatement("SELECT brugernavn,adgangskode FROM login");
		resultset = statement.executeQuery();
		System.out.println("Fandt logins: ");
		while (resultset.next()) {
			String brugernavn = resultset.getString("brugernavn");
			String adgangskode = resultset.getString("adgangskode");

			System.out.println("Brugernavn: " + brugernavn + " adgangskode: " + adgangskode);
		}
		database.stop();
	}

	public Connection getConnection() {
		return connection;
	}
	
	public void transfer(String modtager, String sender, Double beløb) throws SQLException{
		logic = new Logic();
		database.start();
		statement = connection.prepareStatement("UPDATE konto SET saldo=? WHERE ejer=?");
		double nyesaldo = logic.add(Double.parseDouble(database.getSaldo(modtager)), beløb);
		statement.setDouble(1, nyesaldo);
		statement.setString(2, modtager);
		statement.execute();
		nyesaldo = logic.subtract(Double.parseDouble(database.getSaldo(sender)), beløb);
		statement.setDouble(1, nyesaldo);
		statement.setString(2, sender);
		statement.execute();
		database.stop();
		
		
	}
	public String getSaldo(String kunde) throws SQLException{
		System.out.println("looking for saldo...");
		database.start();
		statement = connection.prepareStatement("Select saldo from konto WHERE ejer=?");
		statement.setString(1, kunde);
		resultset = statement.executeQuery();
		while(resultset.next()){
			return resultset.getString("saldo");
			
		}
		System.out.println("no saldo was found");
		database.stop();
	return "";
	}

	@Override
	public void stop() {
		try {
			connection.close();
			resultset.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	

}
