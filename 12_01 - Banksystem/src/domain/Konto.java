package domain;

import logic.Logic;

public class Konto {
	protected Kunde ejer;
	protected String kontonummer;
	protected double saldo;
	private Logic logic;
	
	public Konto(Kunde ejer, String kontonummer){
		this.ejer=ejer;
		this.kontonummer=kontonummer;
		this.saldo=0.00;
	}

	public void deposit(double amount){
		saldo=logic.add(saldo,amount);
	}
	public void withdraw(double amount){
		saldo=logic.subtract(saldo,amount);
	}
	public void transfer(double amount, Konto modtager, Konto sender){
		sender.withdraw(amount);
		modtager.deposit(amount);
	}
	public Kunde getEjer(){
		return ejer;
	}
	public String getKontonummer(){
		return kontonummer;
	}
	public Double getSaldo(){
		return saldo;
	}

	@Override
	public String toString() {
		return "Konto: [ejer=" + ejer + ", kontonummer=" + kontonummer + ", saldo=" + saldo + "]";
	}
}
