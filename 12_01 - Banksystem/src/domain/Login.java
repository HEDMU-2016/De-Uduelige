package domain;

import java.sql.Date;

public abstract class Login{
private String brugernavn;
private String adgangskode;
private Date startdato;
private Date slutdato;
private static final int id=0;

	public Login(String brugernavn , String adgangskode){
		this.brugernavn=brugernavn;
		this.adgangskode=adgangskode;
	}

	public Date getStartdato() {
		return startdato;
	}

	public void setStartdato(Date startdato) {
		this.startdato = startdato;
	}

	public Date getSlutdato() {
		return slutdato;
	}

	public void setSlutdato(Date slutdato) {
		this.slutdato = slutdato;
	}

	public String getBrugernavn() {
		return brugernavn;
	}

	public void setBrugernavn(String brugernavn) {
		this.brugernavn = brugernavn;
	}

	public String getAdgangskode() {
		return adgangskode;
	}

	public void setAdgangskode(String adgangskode) {
		this.adgangskode = adgangskode;
	}
	public int getId(){
		return id;
	}
	@Override
	public String toString() {
		return "Login [brugernavn=" + brugernavn + ", adgangskode=" + adgangskode + "]";
	}
	
}
