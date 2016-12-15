package utill;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import DB.DB;
import domain.Konto;

public class ATM {

	public static void main(String[] args) throws SQLException {
		DB db = new DB();

		Scanner reader = new Scanner(System.in);

		System.out.print("Indtast brugernavn: ");
		String username = reader.nextLine();
		System.out.print("Indtast adgangskode: ");
		String password = reader.nextLine();

		try {
			MessageDigest alg;
			alg = MessageDigest.getInstance("MD5");
			String password1 = password;
			alg.reset();
			alg.update(password1.getBytes());
			byte[] msgDigest = alg.digest();
			BigInteger number = new BigInteger(1, msgDigest);
			String MD5krypteret = number.toString(16);
			password = MD5krypteret;
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		boolean korrekt = db.checkLogin(username.toLowerCase(), password);

		if (korrekt == true) {

			mainMenu(db, reader, username);

		}

	}

	private static void mainMenu(DB db, Scanner reader, String username) throws SQLException {
		System.out.println("Hvad vil du foretage dig?");
		System.out.println("1. Hæv penge");
		System.out.println("2. Indsæt penge");
		System.out.println("3. Se saldo");
		System.out.println("4. Luk");

		int checkNumber = Integer.parseInt(reader.nextLine());

		if (checkNumber == 1) {
			System.out.print("Hvilket kontonummer vil du hæve fra?: ");
			int kontonummer = Integer.parseInt(reader.nextLine());
			checkifuserejerkonto(username, kontonummer, db, reader);
			System.out.print("Hvor mange penge vil du hæve?: ");
			Long withdrawAmount = reader.nextLong();
			db.hæv(withdrawAmount, kontonummer);
			System.out.println("Du har nu hævet " + withdrawAmount + " kr. fra kontonr. " + kontonummer);
		
		}

		if (checkNumber == 2) {
			System.out.print("Hvilke kontonummer vil du indsætte penge på?: ");
			int kontonummer = Integer.parseInt(reader.nextLine());
			checkifuserejerkonto(username, kontonummer, db, reader);
			System.out.print("Hvor mange penge vil du indsætte? ");
			Long depositAmount = reader.nextLong();
			db.insertMoney(depositAmount, kontonummer);
			System.out.println("Du har nu indsat " + depositAmount + " kr. på kontonr. " + kontonummer);
		
		}

		if (checkNumber == 3) {
			System.out.print("På hvilken konto vil du gerne se din saldo?: ");
			int kontonummer = Integer.parseInt(reader.nextLine());
			checkifuserejerkonto(username, kontonummer, db, reader);
			double saldo = db.getSaldo(kontonummer);
			System.out.println("Der står " + saldo + " kr. på kontonr. " + kontonummer);
		

		}

		if (checkNumber == 4) {
			System.out.print("Tak fordi du brugte vores hæveautomat!");

		}
	}

	private static void checkifuserejerkonto(String username, int kontonummer, DB db, Scanner reader) throws SQLException {
		boolean isOwner = false;

		List<Konto> kontolist = db.listkonti(db.matchkundemedlogin(username));
		for (int i = 0; i < kontolist.size(); i++) {
			if (kontolist.get(i).getKontonummer() == kontonummer) {
				isOwner = true;
			}
		}
		if (isOwner == false) {
			System.out.println("Du ejer ikke denne konto. \n");
			mainMenu(db, reader, username);
		}
	}
}