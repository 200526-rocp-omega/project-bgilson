package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

/**
 * This is a class that follows the DAO Design Pattern.
 * 
 * DAO stands for Data Access Object, and it is responsible for handling
 * all logic that will be used to interact with the Database.
 * 
 * This UserDAO implements the IUserDAO Interface, and is a blueprint
 * for creating UserDAO's (Data Access Objects); a UserDAO is responsible
 * for handling all logic that will be used to interact with the Users
 * table in the Database for which it is designed.
 * 
 * This leaves other parts of an application free to interact with
 * the database without needing to think about this complex logic;
 * they simply use the methods available on the Interface, as
 * implemented by the applicable DAO class.
 */
public class UserDAO implements IUserDAO {

	public UserDAO() {
		super();
	}
	
	@Override
	public int insert(User u) {
		// Below is a try-with-resources block:
		// It allows us to instantiate some variable (/object) for use only inside the try block;
		// at the end, it will automatically invoke the close() method on the resource,
		// which will prevent memory leaks
		
		// Step 1: [*Try* to] Get a Connection using ConnectionUtil
		//		   (handle any [SQL]Exception in the accompanying catch block)
		// (the Connection interface represents the physical connection to the database)
			// Initial STS error: "Unhandled exception type SQLException
			//					   thrown by automatic close() invocation on conn"
			// Chose fix #2 of 2:  "Add catch clause to surrounding try"
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			// Step 2: Define SQL Statements
				// The ? marks are placeholders for input values; they work for
				// PreparedStatement_s, and are designed to prevent SQL Injection
			String columns = "username, password, first_name, last_name, email, role_id";
			String sql = "INSERT INTO USERS (" + columns + ") VALUES (?, ?, ?, ?, ?, ?)";
			
			// Step 3[a]: Obtain the [Prepared]Statement object (on/from the Connection)
				// PreparedStatement is a sub-interface of Statement that provides extra
				// security to prevent SQL Injection. It accomplishes this by allowing
				// the use ? marks that we can replace with whatever data we want
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// Step 3b: If we are using a PreparedStatement, then inject values into the parameters
			// 			(insert values into each of the ? marks above - i.e., populate the parameters)
			// Note: It is HEAVILY Recommended to use PreparedStatements instead of String concatenation
			stmt.setString(1, u.getUsername());	 // replace 1st ? mark with u.getUsername()
			stmt.setString(2, u.getPassword());	 // replace 2nd ? mark with u.getPassword()
			stmt.setString(3, u.getFirstName()); // replace 3rd ? mark with u.getFirstName()
			stmt.setString(4, u.getLastName());	 // replace 4th ? mark with u.getLastName()
			stmt.setString(5, u.getEmail());	 // replace 5th ? mark with u.getEmail()
			stmt.setInt(6, u.getRole().getRoleId()); // replace 6th ? mark with u.getRole().getRoleId();
										// getRole returned *its* field, Role; then call Role.getRoleId()
			
			//System.out.println(sql); // use for testing, if needed
			
			// Step 4: Execute the Statement
			return stmt.executeUpdate();	// returns the row count (SQL DML, as here) or 0 (SQL w/ no return)
			
		} catch (SQLException e) {
			// Step 5:  Perform any exception handling in an appropriate way
			// (Might ultimately want to change how handling this here)
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public List<User> findAll() {
		List<User> allUsers = new ArrayList<>();
		
		// Try-with-resources block (instantiates Connection object conn for use only inside this block;
		// at the end, automatically invokes close() on the resource, which prevents memory leaks)
		
		// Step 1: [*Try* to] Get a Connection (to the database) using ConnectionUtil
		//		   (handle any [SQL]Exception in the accompanying catch block)
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			// Step 2: Define SQL Statements
			String sql = "SELECT * FROM USERS INNER JOIN ROLES ON USERS.role_id = ROLES.id";
			
			// Step 3: Obtain the Statement object (on/from the Connection)
				// Here, don't need a PreparedStatement since this is a read operation
				// that is not using parameters (not injecting values into ?'s)
			Statement stmt = conn.createStatement();
			
			// Step 4a: Execute the Statement	
			ResultSet rs = stmt.executeQuery(sql); // returns a ResultSet object
			
			// Step 4b: Walk/loop through the ResultSet to get the data
			//			to add Users to the List
			while(rs.next()) {  // returns a boolean
				// create local variables;
					// accept the rs values (orig. from the Users table fields)
				int userId = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				int roleId = rs.getInt("role_id"); // could instead use ROLES.id (same data (JOIN))
					// accept the rs values (orig. from the (non-JOIN) Roles table field)
				String roleName = rs.getString("role");
				
				// create a Role and User (User contains a Role object)
				// for each ResultSet row
				Role role_obj = new Role(roleId, roleName);
				User u = new User(userId, username, password, firstName, lastName, email, role_obj);
	
				//  add the User object to the List
				allUsers.add(u);
			}
		} catch (SQLException e) {
			// Step 5:  Perform any exception handling in an appropriate way
			e.printStackTrace();
			return new ArrayList<>(); // return an empty list if something goes wrong
		}
		
		// return the List of all Users
		return allUsers;
	}

	@Override
	public User findById(int userId) {
		// Try-with-resources block (instantiates Connection object conn for use only inside this block;
		// at the end, automatically invokes close() on the resource, which prevents memory leaks)
		
		// Step 1: [*Try* to] Get a Connection (to the database) using ConnectionUtil
		//		   (handle any [SQL]Exception in the accompanying catch block)
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			// Step 2: Define SQL Statements
			String sql = "SELECT * FROM USERS INNER JOIN ROLES ON USERS.role_id = ROLES.id where ROLES.id = ?";
			
			// Step 3[a]: Obtain the PreparedStatement object (on/from the Connection)
				// Need a PreparedStatement here, since injecting a value (need a parameter/?)
			PreparedStatement stmt = conn.prepareStatement(sql);

			// Step 3b: Inject a value into the PreparedStatement parameter (replace the ? above)
			stmt.setInt(1, userId);
			
			System.out.println(sql); // use for testing, if needed
			
			// Step 4a: Execute the Statement
			ResultSet rs = stmt.executeQuery(); // returns a ResultSet object

			// Step 4b: Walk/loop through the ResultSet to get the data
			//			from which to create a User object
			while(rs.next()) {  // returns a boolean
				// create local variables;
					// accept the rs values (orig. from the Users table fields)
					// (already have the userId from this method's input parameter)
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				int roleId = rs.getInt("role_id"); // could instead use ROLES.id (same data (JOIN))
					// accept the rs values (orig. from the (non-JOIN) Roles table field)
				String roleName = rs.getString("role");
				
				// create a Role and User (User contains a Role object),
				// returning the User object
				Role role_obj = new Role(roleId, roleName);
				return new User(userId, username, password, firstName, lastName, email, role_obj);
			}
		} catch (SQLException e) {
			// Step 5:  Perform any exception handling in an appropriate way
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public User findByUsername(String username) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM USERS INNER JOIN ROLES ON USERS.role_id = ROLES.id where username = ?";
				// Need a PreparedStatement here, since injecting a value (need a parameter/?)
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();

			// Walk/loop through the ResultSet to get the data
			// from which to create a User object
			while(rs.next()) {
					// accept the rs values (orig. from the Users table fields) into local variables
					// (already have the username from this method's input parameter)
				int userId = rs.getInt("id");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				int roleId = rs.getInt("role_id"); // could instead use ROLES.id (same data (JOIN))
					// accept the rs values (orig. from the (non-JOIN) Roles table field)
				String roleName = rs.getString("role");
				
				// create a Role and User (User contains a Role object),
				// returning the User object
				Role role_obj = new Role(roleId, roleName);
				return new User(userId, username, password, firstName, lastName, email, role_obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int update(User u) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String columns_params = "username=?, password=?, first_name=?, last_name=?, email=?, role_id=?";
			String sql = "UPDATE USERS SET " + columns_params + " WHERE id = ?";
				// Use a PreparedStatement since injecting values (need parameters/?'s)
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// Inject values into (populate) the PreparedStatement parameters (? marks above)
			stmt.setString(1, u.getUsername());
			stmt.setString(2, u.getPassword());
			stmt.setString(3, u.getFirstName());
			stmt.setString(4, u.getLastName());
			stmt.setString(5, u.getEmail());
			stmt.setInt(6, u.getRole().getRoleId()); // getRole returns the Role field; then call Role.getRoleId()
			stmt.setInt(7, u.getUserId());
			
			//System.out.println(sql); // use for testing, if needed
			
			return stmt.executeUpdate();	// returns the row count (SQL DML, as here) or 0 (SQL w/ no return)
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public int delete(int userId) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "DELETE FROM USERS WHERE id = ?";
			// can use cascade ___? w/ DELETE? -- like:
			//-- DROP TABLE ROLES CASCADE CONSTRAINTS;
				// Use a PreparedStatement since injecting a value (need a parameter/?)
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			
			return stmt.executeUpdate();	// returns the row count (SQL DML, as here) or 0 (SQL w/ no return)
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

}
