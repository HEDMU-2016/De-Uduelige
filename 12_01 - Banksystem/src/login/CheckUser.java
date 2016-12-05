package login;

public class CheckUser {

	public static boolean check(String brugernavn, String password) {
		System.out.println("Det checkeren har modtaget er: \"" + brugernavn + "\" og \"" + password + "\"");

		if (brugernavn.equals("admin") && password.equals("password"))
			return true;
		else if (brugernavn.equals("penis") && password.equals("penis"))
			return true;
		else
			return false;
	}

}
