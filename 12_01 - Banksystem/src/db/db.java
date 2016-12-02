package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import logic.Konto;
import logic.Kunde;

public class db {
    static final String db = "jdbc:hsqldb:hsql://localhost/mydb";
    static final String dbuser = "SA";
    static final String dbpass = "";
    private Connection connection;
    ResultSet resultset;
    PreparedStatement statement;

	public void connect() throws SQLException {
		
			connection = DriverManager.getConnection(db, dbuser, dbpass);
			connection.setAutoCommit(false);
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
	public boolean checkuser(String brugernavn, String kodeord)throws SQLException{
		boolean login;
		statement = connection.prepareStatement("select brugernavn, kodeord FROM login");
		while(resultset.next()){
		if(resultset.getString(brugernavn).equals(brugernavn)){
			if(resultset.getString(kodeord).equals(kodeord)){
			return login = true;
			}	
		}
		return login = false;
		}
		return login=false;
	}

	

}
