package domain;

import java.math.BigDecimal;
import java.sql.Date;

public abstract class Rente {
BigDecimal rente;
Date indsætningsdato;
int kontonummer;
public Rente(BigDecimal rente, Date indsætningsdato, int kontonummer) {
	this.rente = rente;
	this.indsætningsdato = indsætningsdato;
	this.kontonummer=kontonummer;
}
public BigDecimal getRente() {
	return rente;
}
public void setRente(BigDecimal rente) {
	this.rente = rente;
}
public int getKontonummer() {
	return kontonummer;
}
public void setKontonummer(int kontonummer) {
	this.kontonummer = kontonummer;
}
public Date getIndsætningsdato() {
	return indsætningsdato;
}
public void setIndsætningsdato(Date indsætningsdato) {
	this.indsætningsdato = indsætningsdato;
}
public abstract int getid();
@Override
public String toString() {
	return "Rente [rente=" + rente + ", indsætningsdato=" + indsætningsdato + "]";
}
	
	
}
