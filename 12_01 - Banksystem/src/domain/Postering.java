package domain;

import java.math.BigDecimal;
import java.sql.Date;

public class Postering {
	int sender;
int modtager;
Date sendt;
BigDecimal beløb;

	public Postering(int frakontoid, int tilkontoid, Date sendt, BigDecimal beløb){
		this.sender=sender;
		this.modtager=modtager;
		this.sendt=sendt;
		this.beløb=beløb;
	}

	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public int getModtager() {
		return modtager;
	}

	public void setModtager(int modtager) {
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
