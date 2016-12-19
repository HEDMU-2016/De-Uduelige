package domain;

import java.math.BigDecimal;
import java.sql.Date;

import logic.Logic;

public class Konto {
	Kunde ejer;
	String ejernavn;

	int kontoid;
	BigDecimal saldo;
	Logic logic;
	Rente rente;
	Date startdato;
	Date slutdato;

	public Konto() {

	}

	public Konto(Kunde ejer, BigDecimal saldo) {
		this.ejer = ejer;
		this.saldo = saldo;
	}

	public String getEjernavn() {
		return ejernavn;
	}

	public void setEjernavn(String ejernavn) {
		this.ejernavn = ejernavn;
	}

	public int getKontoid() {
		return kontoid;
	}

	public void setKontoid(int kontoid) {
		this.kontoid = kontoid;
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

	public Rente getRente() {
		return rente;
	}

	public void setRente(Rente rente) {
		this.rente = rente;
	}

	public void deposit(BigDecimal amount) {
		saldo = logic.add(saldo, amount);
	}

	public void withdraw(BigDecimal amount) {
		saldo = logic.subtract(saldo, amount);
	}

	public void transfer(BigDecimal amount, Konto modtager, Konto sender) {
		sender.withdraw(amount);
		modtager.deposit(amount);
	}

	public Kunde getEjer() {
		return ejer;
	}

	public void setEjer(Kunde ejer) {
		this.ejer = ejer;
	}

	public void setKontonummer(int kontoid) {
		this.kontoid = kontoid;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public int getKontonummer() {
		return kontoid;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	@Override
	public String toString() {
		return "Konto: " + kontoid;
	}
}
