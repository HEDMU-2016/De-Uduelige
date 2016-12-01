package logic;

import java.sql.Connection;

public class Konto {
	
	Kunde ejer;
	String kontonummer;
	double saldo;
	
	public Konto(Kunde ejer, String kontonummer){
		this.ejer=ejer;
		this.kontonummer=kontonummer;
		this.saldo=0.00;
	}

	public void deposit(double amount){
		saldo+=amount;
	}
	public void withdraw(double amount){
		saldo -= amount;
	}
	public void transfer(double amount, Konto konto){
		saldo-=amount;
		konto.deposit(amount);
	}
	public Kunde getEjer(){
		return ejer;
	}
	public String getKontonummer(){
		return kontonummer;
	}

	@Override
	public String toString() {
		return "Konto: [ejer=" + ejer + ", kontonummer=" + kontonummer + ", saldo=" + saldo + "]";
	}
	
	//kommentar
	
}
