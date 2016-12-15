package domain;

import java.math.BigDecimal;
import java.sql.Date;

public class ÅrligRente extends Rente {
	public ÅrligRente(BigDecimal rente, Date indsætningsdato, int kontonummer) {
		super(rente, indsætningsdato, kontonummer);
	}

	@Override
	public int getid() {
		return 2;
	}
}
