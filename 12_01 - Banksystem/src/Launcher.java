import login.Login_IO;

public class Launcher {

	public static void main(String[] args) {
		// Det her skal starte programmet (login_IO new stage)
		Login_IO login_io = new Login_IO();
		login_io.start(new Stage());

	}

}
