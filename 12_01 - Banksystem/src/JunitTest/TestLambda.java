package JunitTest;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import domain.Kunde;

public class TestLambda {

	private static void test(){
	TestLambda instans = new TestLambda();
	List<Kunde> kunder = Arrays.asList(
			new Kunde("dennis","dennis@rosenkilde.nu"),
			new Kunde("dan", "dan@gmail.com"),
			new Kunde("christoffer","christoffer@gmail.com"),
			new Kunde("Jurjis", "jurjis@gmail.com"),
			new Kunde("alan", "alan@gmail.com")
			
			
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
