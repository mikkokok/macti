package snmp;

import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class Testi {
	static Scanner input = new Scanner (System.in);
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		String choice = null;
		String ending = "somethingelse";
		while (!ending.equals("exit")) {
			options();
			choice = input.next();
			switch (choice) {
			case "Create":
				Database.createDatabase();
				break;
			case "Connect":
				Database.getConnection();
				break;
			case "Close":
				Database.closeConnection();
				break;
			case "ShowTables":
				Database.showTables();
				break;
			case "Delete":
				Database.deleteDatabase();
				break;
			case "Exit":
				ending = "exit";
				System.out.println("Exiting...");
				break;
			case "Auto":
				Testi.autoTest();
				break;
			case "Polleri":
				Database.getPolleri();
				break;
			case "AllResults":
				Database.getResult();
				break;
			case "Result":
				System.out.println("Give ID ");
				for (String i: Database.getResultForId(input.nextInt()) ) {
					System.out.println(i);
				}
				break;
			default:
				System.err.println("You did not choose correctly!");
			} //switch
		}
	}
	
	
	// options gives the options which can be used for testing
	// Initial condition: instance needs to be available
	public static void options () throws SQLException {
	System.out.println("What would you like to do?");
	System.out.println("Option: (Create) Creates new database");
	System.out.println("Option: (Connect) Connects to database");
	System.out.println("Option: (Close) Closes connection to database");
	System.out.println("Option: (Delete) Deletes your database");
	System.out.println("Option: (ShowTables) Shows tables from your database");
	System.out.println("Option: (Polleri) Shows polleri table content");
	System.out.println("Option: (AllResults) Shows results table content");
	System.out.println("Option: (Result) Gets result for specified ID");
	System.out.println("Option: (Auto) Tests all necessary items through");
	System.out.println("Option: (Exit) Exit");
	} //options
	
	
	// autoTest runs testing sequence for macti database 
	// Initial condition: instance needs to be available for database class with sufficient rights
	public static void autoTest() throws ClassNotFoundException, SQLException {
		Database.createDatabase();
		Database.getConnection();
		Database.showTables();
		Testi.insertToPolleri();
		Testi.insertToTarget();
		Testi.insertToResult();
		Database.getPolleri();
		Database.getResult();
		Database.getTarget();
		for (String i: Database.getResultForId(5) ) {
			System.out.println(i);
		}
	} // autotest
	
	// insertToPolleri creates pollers for testing
	// Initial condition: polleri table must exist.
	@SuppressWarnings("unused")
	public static void insertToPolleri() throws SQLException {
			try {
				Polleri polleria  = new Polleri(1, "mastera.kis.sa", "virtual");
				Polleri pollerib  = new Polleri(2, "masterb.kis.sa", "virtual");
				Polleri polleric  = new Polleri(3, "masterc.kis.sa", "virtual");
				Polleri pollerid  = new Polleri(4, "masterd.kis.sa", "virtual");
				Polleri pollerie  = new Polleri(5, "mastere.kis.sa", "virtual");
				Polleri pollerif  = new Polleri(6, "masterf.kis.sa", "virtual");
				Polleri pollerig  = new Polleri(7, "masterg.kis.sa", "virtual");
				Polleri pollerih  = new Polleri(8, "masterh.kis.sa", "virtual");
				Polleri pollerii  = new Polleri(9, "masteri.kis.sa", "physical");
				Polleri pollerij  = new Polleri(10, "masterj.kis.sa", "physical");
				Polleri pollerik  = new Polleri(11, "masterk.kis.sa", "physical");
				Polleri polleril  = new Polleri(12, "masterl.kis.sa", "physical");
				Polleri pollerim  = new Polleri(13, "masterm.kis.sa", "physical");
				Polleri pollerin  = new Polleri(14, "mastern.kis.sa", "physical");
			} catch (Exception e) {
				System.err.println("Creating pollers failed: "+e);
				e.printStackTrace();
			}
	} // insertToPolleri
	
	// insertToPolleri creates pollers for testing
	// Initial condition: polleri table must exist.
	@SuppressWarnings("unused")
	public static void insertToTarget() throws SQLException {
		Target targeta = new Target(15, "targeta.kis.sa", "virtual");
		Target targetb = new Target(16, "targetb.kis.sa", "virtual");
		Target targetc = new Target(17, "targetc.kis.sa", "virtual");
		Target targetd = new Target(18, "targetd.kis.sa", "virtual");
		Target targete = new Target(19, "targete.kis.sa", "virtual");
		Target targetf = new Target(20, "targetf.kis.sa", "virtual");
		Target targetg = new Target(21, "targetg.kis.sa", "physical");
		Target targeth = new Target(22, "targeth.kis.sa", "physical");
		Target targeti = new Target(23, "targeti.kis.sa", "physical");
		Target targetj = new Target(24, "targetj.kis.sa", "physical");
		Target targetk = new Target(25, "targetk.kis.sa", "physical");
		Target targetl = new Target(26, "targetl.kis.sa", "physical");
		Target targetm = new Target(27, "targetm.kis.sa", "physical");
		Target targetn = new Target(28, "targetn.kis.sa", "physical");
	} // insertToTarget
	public static void insertToResult () throws SQLException {
		String results[] = {"up","down","2","100","52","95"};
		Random rantomi = new Random();
		
		for (int i = 1; i <= 28; i++ ) {
			try {
				Database.insertResult(i, results[rantomi.nextInt(results.length)], ".1.3.6.1.4.1.2021.11.53.0");
			} catch (Exception e) {
				System.err.println("Inserting result failed: "+e);
			}
		} // for
	} // insertToResult
} // class
