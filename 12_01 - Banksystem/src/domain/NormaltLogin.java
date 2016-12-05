package domain;

public class NormaltLogin extends Login {
private static final int id=2;
	public NormaltLogin(String brugernavn, String adgangskode) {
		super(brugernavn, adgangskode);
	}
	public int getId() {
		return id;
	}

}
