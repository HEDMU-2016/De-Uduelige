package domain;

public class AdminLogin implements identifiable {
private static final int id=1;
	public AdminLogin(String brugernavn, String adgangskode) {
		super(brugernavn, adgangskode);
	}
	public int getId() {
		return id;
	}
	
	
}
