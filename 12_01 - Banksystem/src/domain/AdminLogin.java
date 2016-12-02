package domain;

public class AdminLogin extends Login {
int id;
	public AdminLogin(String brugernavn, String adgangskode) {
		super(brugernavn, adgangskode);
		this.id=2;
	}
	public int getId() {
		return id;
	}

	
}
