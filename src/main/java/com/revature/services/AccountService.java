package com.revature.services;

import java.util.List;

import com.revature.dao.IAccountDAO;
import com.revature.dao.IUserAccountDAO;
import com.revature.dao.UserAccountDAO;
import com.revature.dao.AccountDAO;
import com.revature.models.Account;

/* The service layer is designed to enforce an application's "business logic"
 * - miscellaneous rules that define how the application will function.
 * 		Ex:  May not withdraw money over the current balance
 *
 * All interaction with the DAO's is through the service layer, furthering
 * the design of keeping functionality separate through application layers.
 * 
 * Since business logic can be rather arbitrary, designing this layer involves
 * the MOST creativity, largely leaving the design details up to the developer.
 * (Most other layers are pretty boilerplate, where most methods are pretty much
 * copied/pasted.)
 * 
 * The AccountService class enforces this Banking API's business logic with respect
 * to Accounts, and interacts with the AccountDAO to communicate with the database.
 * 
 * In this capacity, the AccountService ALSO interacts with the *UserAccountDAO* to
 * communicate with the database, using its findByUser(int userId) method (its only
 * method) to interact with the USERS_ACCOUNTS DB join table in conjunction with the
 * ACCOUNTS table to find all of a particular User's Account_s.
 */ 
public class AccountService {
	
	private IAccountDAO dao = new AccountDAO();
	private IUserAccountDAO dao2 = new UserAccountDAO();

	/* There are only some loose _guidelines_ for the code here ...
	 * 
	 * A good starting place is to create CRUD methods in each service layer
	 * Service that will be used to interact with those of the related DAO
	 * 
	 * Then can add other methods to enforce whatever features/rules need
	 * to be implemented (e.g., login/logout methods in the UserService).
	 */
	
	public int insert(Account acct) {
		return dao.insert(acct);
	}
	
	public List<Account> findAll() {
		return dao.findAll();
	}
	
	public Account findById(int accountId) {
		return dao.findById(accountId);
	}
	
	public Account findByStatus(int statusId) {
		return dao.findByStatus(statusId);
	}
	
	public Account findByType(int typeId) {
		return dao.findByType(typeId);
	}
	
	public int update(Account acct) {
		return dao.update(acct);
	}
	
	public int delete(int accountId) {
		return dao.delete(accountId);
	}

	// THIS method uses the IUserAccountDAO/UserAccountDAO
	public List<Account> findByUser(int userId) {
		return dao2.findByUser(userId);
	}
}