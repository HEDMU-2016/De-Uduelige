package domain;

public class Login {
private String brugernavn;
private String adgangskode;

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

	@Override
	public String toString() {
		return "Login [brugernavn=" + brugernavn + ", adgangskode=" + adgangskode + "]";
	}
	
}
