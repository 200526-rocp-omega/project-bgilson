package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * This UserAccountDAO implements the IUserAccountDAO Interface, and is
 * a blueprint for creating UserAccountDAO's (Data Access Objects); a
 * UserAccountDAO is responsible for handling all logic that will be
 * used to interact with the Users_Accounts *join* table in the Database
 * for which it is designed.
 * 
 * This leaves other parts of an application free to interact with
 * the database without needing to think about this complex logic;
 * they simply use the methods available on the Interface, as
 * implemented by the applicable DAO class.
 */
public class UserAccountDAO implements IUserAccountDAO {

	public UserAccountDAO() {
		super();
	}

	@Override
	public List<Account> findByUser(int userId) {
	/* 
	 * In the DB, have to use the USERS_ACCOUNTS join table
	 * to find the account_id for all of the Account_s
	 * associated with the input userId (-> user_id)
	 * and get all ACCOUNTS with that id, and *then*
	 * INNER JOIN these with ACCOUNT_STATUS and ACCOUNT_TYPE.
	 * 
	 * Here, using a subquery for the first operation
	 * and a parent query for the second, which is then
	 * INNER JOINed with ACCOUNT_STATUS and ACCOUNT_TYPE.
	 *
	 * (Could instead have done a massive join along
	 *  with the triple-join - i.e., joined *4* tables,
	 *  including the USERS_ACCOUNTS DB join table -
	 *  but a subquery is preferable.)
	 * 
	 * This single SQL statement (a nested query, or
	 * query/subquery) is put into a single PreparedStatement.
	 *
	 * After execution, we walk through the ResultSet to
	 * obtain the values (orig. from the Accounts table fields).
	 * 
	 * For each resultSet row, we create an AccountStatus,
	 * AccountType, and Account (Account contains an
	 * AccountStatus and an AccountType object), putting
	 * the Account into a list.
	 * 
	 * The method returns this List<Account>
	 * (could contain just 1 list item (Account) for some User_s).
	 */
		List<Account> allAccountsForUser = new ArrayList<>();
		
		// Try-with-resources block
		try (Connection conn = ConnectionUtil.getConnection()) {
			String subquery = "(SELECT ACCOUNT_ID FROM USERS_ACCOUNTS WHERE USER_ID = ?) ";
			String status_join = "ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id";
			String type_join = "ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id";
			String sql = "SELECT * FROM ACCOUNTS WHERE ID IN " + subquery
							+ " INNER JOIN " + status_join + " " + type_join;
			/* Desired query:
			 * --------------
			 * SELECT *
			 * 	FROM ACCOUNTS
			 *  WHERE ID IN
			 *  	(SELECT ACCOUNT_ID
			 *  	 	FROM USERS_ACCOUNTS
			 *  		WHERE USER_ID = ?)
			 *  INNER JOIN + status_join + " " + type_join;
			 */
			
			PreparedStatement stmt = conn.prepareStatement(sql);
						
			//System.out.println(sql); // use for testing, if needed
			
			// Inject a value into the PreparedStatement parameter
			stmt.setInt(1, userId);
			
			ResultSet rs = stmt.executeQuery();
			
			// Walk/loop through the ResultSet to get the data
			// from which to create an Account object
			while(rs.next()) {
				// create local variables;
					// accept the rs values (orig. from the Accounts table fields)
					// ((already) have the userId from this method's input parameter
					//  - but don't really need this going forward (unless wanted
					//  to display it on the web page with the (other) results))
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
				
				// add the Account object to the List
				allAccountsForUser.add(acct);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>(); // return an empty list if something goes wrong
		}
		
		return allAccountsForUser;
	}

}
