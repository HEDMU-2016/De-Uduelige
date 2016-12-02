package domain;

public abstract class Login {
private String brugernavn;
private String adgangskode;
private int id;

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
	public abstract int getId();
	@Override
	public String toString() {
		return "Login [brugernavn=" + brugernavn + ", adgangskode=" + adgangskode + "]";
	}
	
}
