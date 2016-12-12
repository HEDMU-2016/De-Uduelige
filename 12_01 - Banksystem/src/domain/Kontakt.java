package domain;

public class Kontakt {
	String navn;
	int kontonr;
	
	public Kontakt(String navn, int  kontonr){
		this.navn=navn;
		this.kontonr=kontonr;
	}

	@Override
	public String toString() {
		return "Kontakt [kontakt=" + navn + ", kontonr=" + kontonr + "]";
	}

	public String getKontakt() {
		return navn;
	}

	public void setKontakt(String kontakt) {
		this.navn = kontakt;
	}

	public int getKontonr() {
		return kontonr;
	}

	public void setKontonr(int kontonr) {
		this.kontonr = kontonr;
	}


}
