package domain;

public class AdminLogin extends Login{
private static final int id=1;
	
public AdminLogin(String brugernavn, String adgangskode) {
		super(brugernavn, adgangskode);
	}
	public int getId() {
		return id;
	}
	
	
}
