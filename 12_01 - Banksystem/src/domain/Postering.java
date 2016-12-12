package domain;

import java.math.BigDecimal;
import java.sql.Date;

public class Postering {
Konto sender;
Konto modtager;
Date sendt;
BigDecimal beløb;

	public Postering(Konto sender, Konto modtager, Date sendt, BigDecimal beløb){
		this.sender=sender;
		this.modtager=modtager;
		this.sendt=sendt;
		this.beløb=beløb;
	}

	public Konto getSender() {
		return sender;
	}

	public void setSender(Konto sender) {
		this.sender = sender;
	}

	public Konto getModtager() {
		return modtager;
	}

	public void setModtager(Konto modtager) {
		this.modtager = modtager;
	}

	public Date getSendt() {
		return sendt;
	}

	public void setSendt(Date sendt) {
		this.sendt = sendt;
	}

	public BigDecimal getBeløb() {
		return beløb;
	}

	public void setBeløb(BigDecimal beløb) {
		this.beløb = beløb;
	}

	@Override
	public String toString() {
		return "Postering [sender=" + sender + ", modtager=" + modtager + ", sendt=" + sendt + ", beløb=" + beløb + "]";
	}
	

}
