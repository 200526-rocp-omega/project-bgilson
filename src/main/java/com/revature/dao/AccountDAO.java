package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.AccountStatus;
import com.revature.models.AccountType;
import com.revature.util.ConnectionUtil;

/**
 * This is a class that follows the DAO Design Pattern.
 * 
 * DAO stands for Data Access Object, and it is responsible for handling
 * all logic that will be used to interact with the Database.
 * 
 * This AccountDAO implements the IAccountDAO Interface, and is a blueprint
 * for creating AccountDAO's (Data Access Objects); an AccountDAO is responsible
 * for handling all logic that will be used to interact with the Accounts
 * table in the Database for which it is designed.
 * 
 * This leaves other parts of an application free to interact with
 * the database without needing to think about this complex logic;
 * they simply use the methods available on the Interface, as
 * implemented by the applicable DAO class.
 */
public class AccountDAO implements IAccountDAO {

	public AccountDAO() {
		super();
	}

	@Override
	public int insert(Account acct) {
		// Try-with-resources block
		try (Connection conn = ConnectionUtil.getConnection()) {
			String columns = "balance, status_id, type_id";
			String sql = "INSERT INTO USERS (" + columns + ") VALUES (?, ?, ?)";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// Inject values into the PreparedStatement parameters 
			stmt.setDouble(1, acct.getBalance());
				// Account object's getStatus() returns an AccountStatus *object*,
				// on which getStatusId() then returns its statusId (an int)
			stmt.setInt(2, acct.getStatus().getStatusId());
				// Account object's getType() returns an AccountType *object*,
				// on which getTypeId() then returns its typeId (an int)
			stmt.setInt(3, acct.getType().getTypeId());
			
			//System.out.println(sql); // use for testing, if needed
			
			return stmt.executeUpdate();	// returns the row count (SQL DML, as here) or 0 (SQL w/ no return)
			
		} catch (SQLException e) {
			// Perform any exception handling in an appropriate way
			// (Might ultimately want to change how handling this here)
			e.printStackTrace();
		}
				
		return 0;
	}

	@Override
	public List<Account> findAll() {
		List<Account> allAccounts = new ArrayList<>();
		
		// Try-with-resources block
		try (Connection conn = ConnectionUtil.getConnection()) {
			String status_join = "ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id";
			String type_join = "ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id";
			String sql = "SELECT * FROM ACCOUNTS INNER JOIN " + status_join
							+ " INNER JOIN " + type_join;
						
			// use Statement rather than PreparedStatement since no parameters are needed
			Statement stmt = conn.createStatement();
						
			//System.out.println(sql); // use for testing, if needed
			
			ResultSet rs = stmt.executeQuery(sql);
			
			// Walk/loop through the ResultSet to get the data to add Users to the List
			while(rs.next()) {  // returns a boolean
				// create local variables;
					// accept the rs values (orig. from the Accounts table fields)
				int accountId = rs.getInt("id");
				double balance = rs.getDouble("balance");
				int statusId = rs.getInt("status_id");
				int typeId = rs.getInt("type_id");
					// accept the rs values (orig. from the (non-JOIN)
					// Account_Status and Account_Type table fields)
				String status = rs.getString("status");
				String type = rs.getString("type");
				
				// For each resultSet row, create an AccountStatus, AccountType, and Account
				// (Account contains an AccountStatus and an AccountType object)
				AccountStatus status_obj = new AccountStatus(statusId, status);
				AccountType type_obj = new AccountType(typeId, type);
				Account acct = new Account(accountId, balance, status_obj, type_obj);
	
				//  add the Account object to the List
				allAccounts.add(acct);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>(); // return an empty list if something goes wrong
		}

		return allAccounts;
	}

	@Override
	public Account findById(int accountId) {
		// Try-with-resources block
		try (Connection conn = ConnectionUtil.getConnection()) {
			String status_join = "ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id";
			String type_join = "ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id";
			String sql = "SELECT * FROM ACCOUNTS INNER JOIN " + status_join
						 + " INNER JOIN " + type_join + " WHERE ACCOUNTS.id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
						
			//System.out.println(sql); // use for testing, if needed
			
			// Inject a value into the PreparedStatement parameter
			stmt.setInt(1, accountId);
			
			ResultSet rs = stmt.executeQuery();
			
			// Walk/loop through the ResultSet to get the data
			// from which to create an Account object
			while(rs.next()) {
				// create local variables;
					// accept the rs values (orig. from the Accounts table fields)
					// (already have the accountId from this method's input parameter)
				double balance = rs.getDouble("balance");
				int statusId = rs.getInt("status_id");
				int typeId = rs.getInt("type_id");
					// accept the rs values (orig. from the (non-JOIN)
					// Account_Status and Account_Type table fields)
				String status = rs.getString("status");
				String type = rs.getString("type");
				
				// For each resultSet row, create an AccountStatus, AccountType, and Account
				// (Account contains an AccountStatus and an AccountType object)
				AccountStatus status_obj = new AccountStatus(statusId, status);
				AccountType type_obj = new AccountType(typeId, type);
				return new Account(accountId, balance, status_obj, type_obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Account findByStatus(int statusId) {
		// Try-with-resources block
		try (Connection conn = ConnectionUtil.getConnection()) {
			String status_join = "ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id";
			String type_join = "ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id";
			String sql = "SELECT * FROM ACCOUNTS INNER JOIN " + status_join
							+ " INNER JOIN " + type_join + " WHERE status_id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
						
			//System.out.println(sql); // use for testing, if needed
			
			// Inject a value into the PreparedStatement parameter
			stmt.setInt(1, statusId);
			
			ResultSet rs = stmt.executeQuery();
			
			// Walk/loop through the ResultSet to get the data
			// from which to create an Account object
			while(rs.next()) {
				// create local variables;
					// accept the rs values (orig. from the Accounts table fields)
					// (already have the statusId from this method's input parameter)
				int accountId = rs.getInt("id");
				double balance = rs.getDouble("balance");
				int typeId = rs.getInt("type_id");
					// accept the rs values (orig. from the (non-JOIN)
					// Account_Status and Account_Type table fields)
				String status = rs.getString("status");
				String type = rs.getString("type");
				
				// For each resultSet row, create an AccountStatus, AccountType, and Account
				// (Account contains an AccountStatus and an AccountType object)
				AccountStatus status_obj = new AccountStatus(statusId, status);
				AccountType type_obj = new AccountType(typeId, type);
				return new Account(accountId, balance, status_obj, type_obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	//  B.Gilson:  not in Project specs; added as enhancement (here and in IAccountDAO)	
	public Account findByType(int typeId) {
		// Try-with-resources block
		try (Connection conn = ConnectionUtil.getConnection()) {
			String status_join = "ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id";
			String type_join = "ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id";
			String sql = "SELECT * FROM ACCOUNTS INNER JOIN " + status_join
							+ " INNER JOIN " + type_join + " WHERE type_id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
						
			//System.out.println(sql); // use for testing, if needed
			
			// Inject a value into the PreparedStatement parameter
			stmt.setInt(1, typeId);
			
			ResultSet rs = stmt.executeQuery();
			
			// Walk/loop through the ResultSet to get the data
			// from which to create an Account object
			while(rs.next()) {
				// create local variables;
					// accept the rs values (orig. from the Accounts table fields)
					// (already have the typeId from this method's input parameter)
				int accountId = rs.getInt("id");
				double balance = rs.getDouble("balance");
				int statusId = rs.getInt("status_id");
				//int typeId = rs.getInt("type_id");
					// accept the rs values (orig. from the (non-JOIN)
					// Account_Status and Account_Type table fields)
				String status = rs.getString("status");
				String type = rs.getString("type");
				
				// For each resultSet row, create an AccountStatus, AccountType, and Account
				// (Account contains an AccountStatus and an AccountType object)
				AccountStatus status_obj = new AccountStatus(statusId, status);
				AccountType type_obj = new AccountType(typeId, type);
				return new Account(accountId, balance, status_obj, type_obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return null;
	}	

	@Override
	public int update(Account acct) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String columns_params = "balance=?, status_id=?, type_id=?";
			String sql = "UPDATE ACCOUNTS SET " + columns_params + " WHERE id = ?";

				// Use a PreparedStatement since injecting values (need parameters/?'s)
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// Inject values into (populate) the PreparedStatement parameters (? marks above)
			stmt.setDouble(1, acct.getBalance());
			stmt.setInt(2, acct.getStatus().getStatusId()); // get AccountStatus, then its statusId (int)
			stmt.setInt(3, acct.getType().getTypeId()); // get AccountType, then its typeId (an int)
			stmt.setInt(4, acct.getAccountId());
			
			//System.out.println(sql); // use for testing, if needed
			
			return stmt.executeUpdate();	// returns the row count (SQL DML, as here) or 0 (SQL w/ no return)
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public int delete(int accountId) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "DELETE FROM ACCOUNTS WHERE id = ?";
			// can use cascade ___? w/ DELETE? -- like:
			//-- DROP TABLE ROLES CASCADE CONSTRAINTS;
				// Use a PreparedStatement since injecting a value (need a parameter/?)
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, accountId);
			
			return stmt.executeUpdate();	// returns the row count (SQL DML, as here) or 0 (SQL w/ no return)
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

}
