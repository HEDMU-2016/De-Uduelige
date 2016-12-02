package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Konto;
import domain.Kunde;
import domain.Login;

public class db {
    static final String db = "jdbc:hsqldb:hsql://localhost/mydb";
    static final String dbuser = "SA";
    static final String dbpass = "";
    private Connection connection;
    ResultSet resultset;
    PreparedStatement statement;
    

    public db(Connection connection){
    	this.connection=connection;
    }
    
    public Connection connect(Connection connection) throws SQLException {
    	connection = DriverManager.getConnection(db, dbuser, dbpass);
		return connection;
    }
	
	public void close(Connection connection) throws SQLException{
		connection.close();
	}

	Connection getConnection() {
		return connection;
	}
	public void Kunder() throws SQLException{
		statement = connection.prepareStatement("SELECT navn FROM kunde");
		while(resultset.next()){
			String stmp = resultset.getString("navn");
			statement.executeQuery(stmp);
		}
	}
	public void Kunde(String s) throws SQLException{
		statement = connection.prepareStatement("SELECT navn FROM kunde");
		while(resultset.next()){
			String stmp = resultset.getString("navn");
			if(stmp.contains(s))
			System.out.println(stmp);
		}
	}
	public void addKunde(Kunde kunde) throws SQLException{
		statement = connection.prepareStatement("insert into kunde(navn) values (?)");
		statement.setString(0, kunde.getNavn());
		statement.execute();
	}
	public void addKonto(Konto konto) throws SQLException{
		statement = connection.prepareStatement("insert into konto(ejer,kontonummer,saldo)values(?,?,?)");
		statement.setString(0, konto.getEjer().getNavn());
		statement.setString(1, konto.getKontonummer());
		statement.setDouble(2, konto.getSaldo());
		statement.execute();
	}
	
	
	public boolean checkLogin(Login login)throws SQLException{
		statement = connection.prepareStatement("select brugernavn, adgangskode FROM login");
		String brugernavn = resultset.getString(login.getBrugernavn());
		String adgangskode = resultset.getString(login.getAdgangskode());
		
		if(brugernavn.equals(login.getBrugernavn()) && adgangskode.equals(login.getAdgangskode())){
			return true;
			}	
		return false;
		}
	public void addLogin(Login login) throws SQLException{
		statement = connection.prepareStatement("INSERT INTO login (brugernavn, adgangskode, id) values(?,?,?)");
		statement.setString(0, login.getBrugernavn());
		statement.setString(1, login.getAdgangskode());
		statement.setInt(2,login.getId());
		statement.execute();
	}
	public void findLogin() throws SQLException{
		statement = connection.prepareStatement("SELECT brugernavn,adgangskode FROM login");
		String brugernavn = resultset.getString("brugernavn");
		String adgangskode = resultset.getString("adgangskode");
		
		while(resultset.next()){
			statement.executeQuery(brugernavn);
			statement.executeQuery(adgangskode);
		}
	}

}

