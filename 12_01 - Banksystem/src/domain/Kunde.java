package domain;

public class Kunde {
String navn;
String konto;

	public Kunde(String navn){
		this.navn=navn;
	}

	public String getKonto() {
		return konto;
	}

	public void setKonto(String konto) {
		this.konto = konto;
	}
	public String getNavn(){
		return navn;
	}

	@Override
	public String toString() {
		return "Kunde [navn=" + navn + ", konto=" + konto + "]";
	}
	
}
