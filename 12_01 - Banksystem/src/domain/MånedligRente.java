package domain;

import java.math.BigDecimal;
import java.sql.Date;

public class MånedligRente extends Rente {
public MånedligRente(BigDecimal rente, Date indsætningsdato, int kontonummer) {
		super(rente, indsætningsdato, kontonummer);
	}
	public int getid() {
		return 1;
	}
}
