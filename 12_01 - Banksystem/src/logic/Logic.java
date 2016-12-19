package logic;

import java.math.BigDecimal;

public class Logic {
	
	public BigDecimal add(BigDecimal n1, BigDecimal n2){
		BigDecimal sum = n1.add(n2);
		return sum;
	}
	public BigDecimal subtract(BigDecimal n1, BigDecimal n2){
		BigDecimal sum =n1.subtract(n2);
		return sum;
	}
	public BigDecimal indsætrente(BigDecimal saldo, BigDecimal rente){
		if(saldo.equals(0)){
		return BigDecimal.valueOf(0);
		}
		
		saldo = saldo.multiply(rente);
		System.out.println(saldo);
		return saldo;
	}
}