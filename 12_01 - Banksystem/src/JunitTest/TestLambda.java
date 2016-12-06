package JunitTest;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import domain.Kunde;

public class TestLambda {

	private static void main(String[] args){
	TestLambda instans = new TestLambda();
	List<Kunde> kunder = Arrays.asList(
			new Kunde("dennis"),
			new Kunde("dan"),
			new Kunde("christoffer"),
			new Kunde("Jurjis"),
			new Kunde("alan")
			
			
			);
		instans.findKunder("d", kunder);	
	}
	private List<String> findKunder(String søgning, List<Kunde> kunde) {
		List<String> newlist = new ArrayList<>();
		for(Kunde k: kunde){
		if(k.getNavn().contains(søgning))
			newlist.add(k.getNavn());
			
		}
		return newlist;
		
	}
}
