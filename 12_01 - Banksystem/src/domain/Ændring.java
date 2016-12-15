package domain;

import java.sql.Date;
import java.sql.PreparedStatement;

public class Ændring {
Date indførelsesdato;
PreparedStatement statement;
public Ændring(Date indførelsesdato, PreparedStatement statement) {
	super();
	this.indførelsesdato = indførelsesdato;
	this.statement = statement;
}
public Date getIndførelsesdato() {
	return indførelsesdato;
}
public void setIndførelsesdato(Date indførelsesdato) {
	this.indførelsesdato = indførelsesdato;
}
public PreparedStatement getStatement() {
	return statement;
}
public void setStatement(PreparedStatement statement) {
	this.statement = statement;
}
@Override
public String toString() {
	return "Ændring [indførelsesdato=" + indførelsesdato + ", statement=" + statement + "]";
}



}
