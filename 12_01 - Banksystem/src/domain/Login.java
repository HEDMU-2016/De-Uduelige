package domain;

public class Login implements identifiable {
private String brugernavn;
private String adgangskode;
private static final int id=0;

	public Login(String brugernavn , String adgangskode){
		this.brugernavn=brugernavn;
		this.adgangskode=adgangskode;
	}

	public String getBrugernavn() {
		return brugernavn;
	}

	public void setBrugernavn(String brugernavn) {
		this.brugernavn = brugernavn;
	}

	public String getAdgangskode() {
		return adgangskode;
	}

	public void setAdgangskode(String adgangskode) {
		this.adgangskode = adgangskode;
	}
	public int getId(){
		return id;
	}
	@Override
	public String toString() {
		return "Login [brugernavn=" + brugernavn + ", adgangskode=" + adgangskode + "]";
	}
	
}
