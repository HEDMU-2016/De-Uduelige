package logic;

import java.math.BigDecimal;

public class Logic {
	
	public BigDecimal add(BigDecimal n1, BigDecimal n2){
		BigDecimal sum =BigDecimal.valueOf(n1.doubleValue()+n2.doubleValue());
		return sum;
	}
	public BigDecimal subtract(BigDecimal n1, BigDecimal n2){
		BigDecimal sum =BigDecimal.valueOf(n1.doubleValue()-n2.doubleValue());
		return sum;
	}
}