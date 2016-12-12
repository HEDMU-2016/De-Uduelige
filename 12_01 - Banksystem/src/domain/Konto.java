package domain;

import java.math.BigDecimal;

import logic.Logic;

public class Konto {
	protected Kunde ejer;
	protected int kontoid;
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

	public void setKontonummer(int kontoid) {
		this.kontoid = kontoid;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public int getKontonummer(){
		return kontoid;
	}
	public BigDecimal getSaldo(){
		return saldo;
	}

	@Override
	public String toString() {
		return "Konto: "+kontoid;
	}
}
