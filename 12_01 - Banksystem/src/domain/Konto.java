package domain;

import java.math.BigDecimal;

import logic.Logic;

public class Konto {
	protected Kunde ejer;
	protected int kontonummer;
	protected BigDecimal saldo;
	private Logic logic;
	
	public Konto(Kunde ejer, BigDecimal saldo){
		this.ejer=ejer;
		this.saldo=saldo;
	}

	public void deposit(BigDecimal amount){
		saldo=logic.add(saldo,amount);
	}
	public void withdraw(BigDecimal amount){
		saldo=logic.subtract(saldo,amount);
	}
	public void transfer(BigDecimal amount, Konto modtager, Konto sender){
		sender.withdraw(amount);
		modtager.deposit(amount);
	}
	public Kunde getEjer(){
		return ejer;
	}
	public void setEjer(Kunde ejer) {
		this.ejer = ejer;
	}

	public void setKontonummer(int kontonummer) {
		this.kontonummer = kontonummer;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public int getKontonummer(){
		return kontonummer;
	}
	public BigDecimal getSaldo(){
		return saldo;
	}

	@Override
	public String toString() {
		return "Konto: [ejer=" + ejer + ", kontonummer=" + kontonummer + ", saldo=" + saldo + "]";
	}
}
