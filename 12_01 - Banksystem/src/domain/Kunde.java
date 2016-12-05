package domain;

import java.sql.Date;

public class Kunde {
String navn;
String konto;
Date startdato;
Date slutdato;

	public Kunde(String navn){
		this.navn=navn;
	}

	public String getKonto() {
		return konto;
	}

	public void setKonto(String konto) {
		this.konto = konto;
	}
	public String getNavn(){
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
		return "Kunde [navn=" + navn + ", konto=" + konto + "Oprettet: "+startdato + "]";
	}
	
}
