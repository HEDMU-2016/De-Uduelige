package domain;

public class NormaltLogin extends Login {
int id;
	public NormaltLogin(String brugernavn, String adgangskode) {
		super(brugernavn, adgangskode);
		id=1;
	}
	public int getId() {
		return id;
	}

}
