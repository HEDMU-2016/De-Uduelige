package logic;

import java.math.BigDecimal;

public class Logic {
	
	public BigDecimal add(BigDecimal n1, BigDecimal n2){
		BigDecimal sum = n1.add(n2);
		return sum;
	}
	public BigDecimal subtract(BigDecimal n1, BigDecimal n2){
		BigDecimal sum =n1.add(n2);
		return sum;
	}
	public BigDecimal indsætrente(BigDecimal saldo, BigDecimal rente){
		saldo.multiply(rente);
		return saldo;
	}
}