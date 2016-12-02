package login;

public class CheckUser {

	public static boolean check(String brugernavn, String password) {

		switch (brugernavn) {
		case "admin":
			if (password.equals("password"));
			return true;
		case "penis":
			if (password.equals("penis"));
			return true;
		case "penispenis":
			if (password.equals("penis"));
			return true;
		default:
			return false;
		}

	}

}