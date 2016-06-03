package snmp;

import java.sql.SQLException;

// Class that defines polleri object

public class Polleri {

	
	//**********************************************************
	// Variables
	//**********************************************************
	
	private int id;					// Pollers id in the database
	private String name;			// Pollers name in the database
	private String virtuality;		// Pollers virtuality
	
	//**********************************************************
	// Constructor
	//**********************************************************
	
	


	// Database connection needs to be established before creating polleri
	// Creates poller object and adds it to database
	public Polleri(int id, String name, String virtuality ) throws SQLException {
		this.id = id;
		this.name = name;
		this.virtuality = virtuality;
		try {
			Database.insertPolleri(id, name);
			Database.insertTarget(id, name, virtuality);
		} catch (Exception e) {
			System.err.println("Adding polleri to database failed "+e);
		}
	}

	//**********************************************************
	// Functions
	//**********************************************************
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the virtuality
	 */
	public String getVirtuality() {
		return virtuality;
	}

	/* 
	 * return polleri as string
	 */
	@Override
	public String toString() {
		return "Polleri [id=" + id + ", name=" + name + ", virtuality=" + virtuality + "]";
	}

	
	
} // Polleri 
