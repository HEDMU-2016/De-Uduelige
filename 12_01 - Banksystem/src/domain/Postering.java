package domain;

import java.sql.Date;

public class Postering {
String sender;
String modtager;
Date sendt;
double beløb;

	public Postering(String sender, String modtager, Date sendt, double beløb){
		this.sender=sender;
		this.modtager=modtager;
		this.sendt=sendt;
		this.beløb=beløb;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getModtager() {
		return modtager;
	}

	public void setModtager(String modtager) {
		this.modtager = modtager;
	}

	public Date getSendt() {
		return sendt;
	}

	public void setSendt(Date sendt) {
		this.sendt = sendt;
	}

	public double getBeløb() {
		return beløb;
	}

	public void setBeløb(double beløb) {
		this.beløb = beløb;
	}

	@Override
	public String toString() {
		return "Postering [sender=" + sender + ", modtager=" + modtager + ", sendt=" + sendt + ", beløb=" + beløb + "]";
	}
	

}
