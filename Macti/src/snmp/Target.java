package snmp;

import java.sql.SQLException;
import java.util.LinkedList;

public class Target {
	

	//**********************************************************
	// Variables
	//**********************************************************
	
	private int id;					// Targets id in the database
	private String name;			// Targets name in the database
	private String virtuality;		// Targets virtuality or physicality
	
	//**********************************************************
	// Constructor
	//**********************************************************
	/**
	 * @param id
	 * @param name
	 * @param virtuality
	 * @throws SQLException 
	 */
	public Target(int id, String name, String virtuality) throws SQLException {
		this.id = id;
		this.name = name;
		this.virtuality = virtuality;
		try {
			Database.insertTarget(id, name, virtuality);
		} catch (Exception e) {
			System.err.println("Creating target failed "+e); 
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
	 * Returns target as string
	 */
	@Override
	public String toString() {
		return "Target [id=" + id + ", name=" + name + ", virtuality=" + virtuality + "]";
	}
	
	// Returns targets results as a string
	public LinkedList<String> getTargetResult() throws SQLException {
		return Database.getResultForId(this.id);
		
	}

	
}
