package logic;

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
	
}
