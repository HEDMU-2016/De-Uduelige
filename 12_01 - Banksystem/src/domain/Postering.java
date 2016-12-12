package domain;

import java.math.BigDecimal;
import java.sql.Date;

public class Postering {
private int frakontoid;
private int tilkontoid;
private Date sendt;
private BigDecimal beløb;

	public Postering(int sender, int modtager, Date sendt, BigDecimal beløb){
		this.frakontoid=sender;
		this.tilkontoid=modtager;
		this.sendt=sendt;
		this.beløb=beløb;
	}

	public int getSender() {
		return frakontoid;
	}

	public void setSender(int sender) {
		this.frakontoid = sender;
	}

	public int getModtager() {
		return tilkontoid;
	}

	public void setModtager(int modtager) {
		this.tilkontoid = modtager;
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
		return "Postering [sender=" + frakontoid + ", modtager=" + tilkontoid + ", sendt=" + sendt + ", beløb=" + beløb + "]";
	}
	

}
