package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	// Connection = an Interface that will represent a single (1) connection
		// Here, conn is an *instance variable* (of type Connection)
	private static Connection conn = null;
	
	// Make constructor *private* to PREVENT ever instantiating this class
	private ConnectionUtil() {
		super();
	}
	
	public static Connection getConnection() {
		// check to see if the class containing the needed driver is accessible 
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// if that class IS accessible,
			// use the DriverManager to obtain a connection to the database
			// 		(syntax:  DriverManager.getConnection(String url, String user, String password) )
			// Initially, the below gave STS error "Unhandled exception type SQLException";
			// chose 4th of 4 suggested fixes, "Surround with try/catch"
			try {
				conn = DriverManager.getConnection(
						"jdbc:oracle:thin:@training.cuu2unhhwabu.us-west-1.rds.amazonaws.com:1521:ORCL",
						"beaver",
						"chew"); // not a good idea - but here trivial data
			} catch (SQLException e) {
				e.printStackTrace();
			} // not a good idea to hard-code, but trivial data, here
		} catch (ClassNotFoundException e) {
			// couldn't find/access the class containing the needed driver
			System.out.println("Did not find the Oracle JDBC Driver class!");
		}
		
		return conn;
	}
}
