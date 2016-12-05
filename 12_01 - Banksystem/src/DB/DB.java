package DB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import domain.Konto;
import domain.Kunde;
import domain.Login;
 
 public class DB {
     static final String db = "jdbc:hsqldb:hsql://localhost/mydb";
     static final String dbuser = "SA";
     static final String dbpass = "";
     private Connection connection;
     ResultSet resultset;
     PreparedStatement statement;
     
 
     public void start()throws SQLException{
     	connection=DriverManager.getConnection(db, dbuser, dbpass);
     }
 
     
     public DB(){
     }
     
     public Connection connect(Connection connection) throws SQLException {
     	connection = DriverManager.getConnection(db, dbuser, dbpass);
 		return connection;
     }
 	
	public void close() throws SQLException{
		connection.close();
	}
	public void findKunder() throws SQLException{
		statement = connection.prepareStatement("SELECT navn FROM kunde");
		resultset = statement.executeQuery();
		System.out.println("Fandt kunder: ");
		while(resultset.next()){
			String stmp = resultset.getString("navn");
			System.out.println(stmp);		}
  }
	public void findKunde(String s) throws SQLException{
	statement = connection.prepareStatement("SELECT navn FROM kunde WHERE kunde LIKE ?");
	statement.setString(1, "%"+s+"%");
	resultset = statement.executeQuery();
 
	System.out.println("Fandt kunder: ");
	while(resultset.next()){
 		String stmp = resultset.getString("navn");
 		System.out.println(stmp);
 		}
 	}
	public void addKunde(Kunde kunde) throws SQLException{
 	Date startdato = Date.valueOf(LocalDate.now());
 	Date slutdato = Date.valueOf(LocalDate.of(9999, 01, 01));
	statement = connection.prepareStatement("insert into kunde(navn, startdato, slutdato) values (?,?,?)");
 	statement.setString(1, kunde.getNavn());
 	statement.setDate(2, startdato);
 	statement.setDate(3, slutdato);
 	statement.execute();
 	System.out.println("Tilførte kunde: "+ kunde);
 }
	public void addKonto(Konto konto) throws SQLException{
 	statement = connection.prepareStatement("insert into konto(ejer,kontonummer,saldo)values(?,?,?)");
 	statement.setString(1, konto.getEjer().getNavn());
 	statement.setString(2, konto.getKontonummer());
 	statement.setDouble(3, konto.getSaldo());
	statement.execute();
	System.out.println("konto: " + konto + "blev lagt ind i databasen");
 }
	
 
	public boolean checkLogin(Login login)throws SQLException{ 
	 statement = connection.prepareStatement("select brugernavn, adgangskode FROM login");
	 resultset = statement.executeQuery();
 	while(resultset.next()){ 
		String brugernavn = resultset.getString("brugernavn");
 		String adgangskode = resultset.getString("adgangskode");
 			
	 if(brugernavn.equals(login.getBrugernavn()) && adgangskode.equals(login.getAdgangskode())){
 			System.out.println("Login: " + login + " er godkendt");
 			return true;
 			}
 		System.out.println("Login: " + login + " blev afvist");
 		
 		}
 	return false;
	 }
 	public void addLogin(Login login) throws SQLException{
 		statement = connection.prepareStatement("INSERT INTO login (brugernavn, adgangskode, id) values(?,?,?)");
 		statement.setString(1, login.getBrugernavn());
 		statement.setString(2, login.getAdgangskode());
 		statement.setInt(3,login.getId());
 		statement.execute();
 		System.out.println("Login: " + login + "blev lagt ind i databasen");
 	}
 	public void findLogin() throws SQLException{
 		statement = connection.prepareStatement("SELECT brugernavn,adgangskode FROM login");
 		resultset = statement.executeQuery();
 		System.out.println("Fandt logins: ");
 		while(resultset.next()){
 			String brugernavn = resultset.getString("brugernavn");
 	 		String adgangskode = resultset.getString("adgangskode");

 			System.out.println("Brugernavn: "+brugernavn + " adgangskode: " + adgangskode);
 		}
 	}
 	public Connection getConnection(){
 		return connection;
 	}
 
 }
