package domain;

import java.sql.Date;

public class Kunde {
	String navn;
	String email;
	String konto;
	String brugernavn;

	Date startdato;
	Date slutdato;

	

	public Kunde(String navn, String email, String brugernavn) {
		this.navn = navn;
		this.email = email;
		this.brugernavn=brugernavn;
	}

	public String getBrugernavn() {
		return brugernavn;
	}


	public String getKonto() {
		return konto;
	}

	public String getEmail() {
		return email;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setKonto(String konto) {
		this.konto = konto;
	}

	public String getNavn() {
		return navn;
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

	@Override
	public String toString() {
		return navn;
	}

}
