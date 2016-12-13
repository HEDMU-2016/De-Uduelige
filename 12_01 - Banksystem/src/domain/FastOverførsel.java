package domain;

import java.math.BigDecimal;
import java.sql.Date;

public class FastOverførsel {
int senderskontoid;
int modtagerkontoid;
BigDecimal beløb;
Date startdato;
int id;
public FastOverførsel(int senderskontoid, int modtagerkontoid, BigDecimal beløb, Date startdato, int id) {
	this.senderskontoid = senderskontoid;
	this.modtagerkontoid = modtagerkontoid;
	this.beløb = beløb;
	this.startdato = startdato;
	this.id = id;
}
public int getSenderskontoid() {
	return senderskontoid;
}
public void setSenderskontoid(int senderskontoid) {
	this.senderskontoid = senderskontoid;
}
public int getModtagerkontoid() {
	return modtagerkontoid;
}
public void setModtagerkontoid(int modtagerkontoid) {
	this.modtagerkontoid = modtagerkontoid;
}
public BigDecimal getBeløb() {
	return beløb;
}
public void setBeløb(BigDecimal beløb) {
	this.beløb = beløb;
}
public Date getStartdato() {
	return startdato;
}
public void setStartdato(Date startdato) {
	this.startdato = startdato;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
@Override
public String toString() {
	return "FastOverførsel [senderskontoid=" + senderskontoid + ", modtagerkontoid=" + modtagerkontoid + ", beløb="
			+ beløb + ", startdato=" + startdato + ", id=" + id + "]";
}


}
