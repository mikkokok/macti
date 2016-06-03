package snmp;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;


// Class for database connection
  
public class Database {
	
	
	//**********************************************************
	// Variables for database connection
	//**********************************************************
	
	
	private static Connection conn;
	private static String dbname = "macti"; //Database name
	private static String duser = "javauser"; // Database user
	private static String dpassword = "kissa123"; // Database user password
	private static String durl = "mssql.sotasankari.sandels"; // Database server name
	private static String connectionstring = "jdbc:sqlserver://"+durl+";user="+duser+";password="+dpassword; // String for instance connection
	private static String dbconnectionstring = "jdbc:sqlserver://"+durl+";user="+duser+";password="+dpassword+";database="+dbname; // String for database connection
	private static String dbdriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // Driver which is being used for database connections
	private static Statement sta = null; // Initialize statement and leave it as null
	private static String sql = null; // Initialize string sql and leave it as null
	private static ResultSet rs = null;
	
	// ---------------- Variables ending ------------------------------
	
	
	//**********************************************************
	// Procedures
	//**********************************************************
	
	
	// Creates database and tables
	// User needs to be at least with dbcreator rights in the default instance
	public static void createDatabase() throws ClassNotFoundException, SQLException {
		System.out.println("Don't run me twice!");
		System.out.println("I will create you database named "+dbname+" and three tables: polleri,target and result");
		System.out.println("Thank you!");
		System.out.println("Lets begin");
		try {
			Class.forName(dbdriver);
			conn = DriverManager.getConnection(connectionstring);
			System.out.println("Creating database with name: "+dbname);
			sta = conn.createStatement();
			sql = "CREATE DATABASE ";
			sql = sql + dbname;
			sql = sql+ " SET ANSI_WARNINGS OFF";
			sta.executeUpdate(sql);
			System.out.println("Database with name: "+dbname+" created");
			System.out.println("Closing connection.");
			System.out.println("End of database creation");
			System.out.println("------------------------------------------------------------------- \n\n\n\n");
			Database.closeConnection();
		} catch (Exception e) {
			System.err.println("Creating database failed: "+e);
		} // try-catch
		Database.createTables();
	} // createDatabase
	
	
	// Creates three tables: polleri,target and result
	// User needs to be at least with dnowner rights in the database
	public static void createTables() throws SQLException {
		System.out.println("Creating tables for database: "+dbname);
		try {
			conn = DriverManager.getConnection(dbconnectionstring);
			sta = conn.createStatement();
			sql = "CREATE TABLE polleri" +			// Table Polleri
					"(id INTEGER not NULL, " +
					"name VARCHAR(50), " +
					"possibleoids VARCHAR(255)," + 
					"PRIMARY KEY ( id))" +
					"CREATE TABLE target" +			// Table Target
					"(id INTEGER not NULL, " +
					"name VARCHAR(50), " +
					"virtuality VARCHAR(255)," + 
					"PRIMARY KEY ( id))" +
					"CREATE TABLE result" +			// Table Result
					"(id INTEGER not NULL, " +
					"output VARCHAR(50), " +
					"usedoids VARCHAR(255)," + 
					"PRIMARY KEY ( id))";
			sta.executeUpdate(sql);
		} catch (Exception e) {
			System.err.println("Table creation failed with "+e);
			e.printStackTrace();
		}
		System.out.println("Tables created.");
		System.out.println("------------------------------------------------------------------- \n\n\n\n");
	} //createTables
	
	// Delete database, removes the database 
	// Database needs to exists. User needs to have sufficient rights to remove database
	public static void deleteDatabase() throws ClassNotFoundException, SQLException {
		System.out.println("Removing database: "+dbname);
		try {
			Class.forName(dbdriver);
			conn = DriverManager.getConnection(connectionstring);
			sta = conn.createStatement();
			sql = "ALTER DATABASE "+dbname+" SET SINGLE_USER WITH ROLLBACK IMMEDIATE";
			sql = sql + " DROP DATABASE ";
			sql = sql + dbname;
			sta.executeUpdate(sql);
		} catch (Exception e) {
			System.err.println("Removing database "+dbname+" failed with message: "+e);
		}
		System.out.println("Database "+dbname+" removed succesfully");
		Database.closeConnection();
	} // deleteDatabase
	
	// Creates connection to the database
	// Database needs to be created before
	public static void getConnection() throws ClassNotFoundException, SQLException {
		try {
			Class.forName(dbdriver);	
			conn = DriverManager.getConnection(dbconnectionstring);
			System.out.println("Connection to database succesfully created");
		} catch (Exception e) {
			System.err.println("Error when trying to connect to database: "+e);
		} // try-catch
	} // getConnection
	// Closes connection to database
	public static void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.err.println("Error when closing database connection: "+e);
			} // try-catch
	} // closeConnection
	
	//**********************************************************
	// Functions
	//**********************************************************
	
	
	// Shows all tables from used database
	// Initial condition: Database needs to exists
	public static void showTables () throws SQLException {
		try {
			sta = conn.createStatement();
			sql = "SELECT * FROM information_schema.tables";
			rs = sta.executeQuery(sql);
			System.out.println("TABLE_CATALOG \t TABLE_SCHEMA \t TABLE_NAME \t TABLE_TYPE");
			while(rs.next()) {
				String TABLE_CATALOG = rs.getString("TABLE_CATALOG");
				String TABLE_SCHEMA = rs.getString("TABLE_SCHEMA");
				String TABLE_NAME = rs.getString("TABLE_NAME");
				String TABLE_TYPE = rs.getString("TABLE_TYPE");
				System.out.println(TABLE_CATALOG+"\t"+TABLE_SCHEMA+"\t"+TABLE_NAME+"\t"+TABLE_TYPE);
			} // while
		} catch (Exception e) {
			System.err.println("Something funny happened during the query: "+e);
		}
	} // showTables	
	
	// ---------------------------- Functions for polleri table starts ----------------------------
	
	// Initial condition: database connection needs to be alive
	// Getpolleri shows content of polleri table
	public static void getPolleri() throws SQLException {
		try {
			sta = conn.createStatement();
			sql = "SELECT * FROM polleri";
			rs = sta.executeQuery(sql);
			System.out.println("ID \t Name \t\t possibleoids");
			while(rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String possibleoids = rs.getString("possibleoids");
				System.out.println(id+"\t"+name+"\t\t"+possibleoids);
			} // while
		} catch (Exception e) {
			System.err.println("Something funny happened during the query: "+e);
		}
	} // getPolleri
	
	// Fills the polleri table with provided information
	// Initial condition: User needs to be at least with dbowner rights in the database
	public static void insertPolleri(int id, String name) throws SQLException {
		try {
			conn = DriverManager.getConnection(dbconnectionstring);
			sta = conn.createStatement();
			sql = "INSERT INTO polleri " +
					"VALUES ('"+id+"', '"+name+"','.1.3.6.1.4.1.2021.11.53.0')"; // Raw cpu idle
			sta.executeUpdate(sql);
		} catch (Exception e) {
			System.err.println("Something funny happened "+e );
		} // try-catch
	} // insertPolleri
	
	// ---------------------------- Functions for polleri table ends ----------------------------
	
	// ---------------------------- Functions for target table starts ----------------------------
	
	// Initial condition: database connection needs to be alive
	// GetTarget shows content of target table
	public static void getTarget() throws SQLException {
		try {
			sta = conn.createStatement();
			sql = "SELECT * FROM target";
			rs = sta.executeQuery(sql);
			System.out.println("ID \t Name \t\t\t\t\t virtuality");
			while(rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String virtuality = rs.getString("virtuality");
				System.out.println(id+"\t"+name+"\t"+virtuality);
			} // while
		} catch (Exception e) {
			System.err.println("Something funny happened during the query: "+e);
		}
	} // getPolleri
	
	// Fills the polleri table with provided information
	// Initial condition: User needs to be at least with dbowner rights in the database
	public static void insertTarget(int id, String name, String virtuality) throws SQLException {
		try {
			conn = DriverManager.getConnection(dbconnectionstring);
			sta = conn.createStatement();
			sql = "INSERT INTO target " +
					"VALUES ('"+id+"', '"+name+"','"+virtuality+"')"; // Raw cpu idle
			sta.executeUpdate(sql);
		} catch (Exception e) {
			System.err.println("Something funny happened "+e );
		} // try-catch
	} // insertTarger
	
	// ---------------------------- Functions for target table ends ----------------------------
	
	// ---------------------------- Functions for result table starts ----------------------------
	
	// Initial condition: database connection needs to be alive
	// Getresult shows content of result table
	public static void getResult() throws SQLException {
		try {
			sta = conn.createStatement();
			sql = "SELECT * FROM result";
			rs = sta.executeQuery(sql);
			System.out.println("ID \t output \t\t usedoids");
			while(rs.next()) {
				String id = rs.getString("id");
				String output = rs.getString("output");
				String usedoids = rs.getString("usedoids");
				System.out.println(id+"\t"+output+"\t"+usedoids);
			} // while
		} catch (Exception e) {
			System.err.println("Something funny happened during the query: "+e);
		}
	} // getResult
	
	// Fills the result table with provided information
	// Initial condition: User needs to be at least with dbowner rights in the database
	public static void insertResult(int id, String output, String usedoids) throws SQLException {
		try {
			conn = DriverManager.getConnection(dbconnectionstring);
			sta = conn.createStatement();
			sql = "INSERT INTO result " +
					"VALUES ('"+id+"', '"+output+"','"+usedoids+"')"; // Raw cpu idle
			sta.executeUpdate(sql);
		} catch (Exception e) {
			System.err.println("Something funny happened "+e );
		} // try-catch
	} // insertResult
	
	// ---------------------------- Functions for result table ends ----------------------------
	
	// Initial condition: database connection needs to be alive
	// Getresult shows content of result table
	public static LinkedList<String> getResultForId(int number) throws SQLException {
		LinkedList<String> results = new LinkedList<String>();
		String testi = null;
		try {
			sta = conn.createStatement();
			sql = "SELECT DISTINCT target.id, name, virtuality, output, usedoids FROM target, result WHERE target.id = result.id AND target.id = "+number;
			rs = sta.executeQuery(sql);
			results.add("ID \t Name \t\t Virtuality \t Output \t Usedoids");
			while(rs.next()) {
					String id = rs.getString("id");
					String name = rs.getString("name");
					String virtuality = rs.getString("virtuality");
					String output = rs.getString("output");
					String usedoids = rs.getString("usedoids");
					//System.out.println(id+"\t"+name+"\t"+virtuality+"\t"+output+"\t"+usedoids);
					testi = id+"\t"+name+"\t"+virtuality+"\t\t  "+output+"\t   "+usedoids;
					//results.add(id+"\t"+name+"\t"+virtuality+"\t"+output+"\t"+usedoids);
					results.add(testi);
					
			} // while
		} catch (Exception e) {
			System.err.println("Something funny happened during the query: "+e);
		}
		return results;
	} // getResult

} //class