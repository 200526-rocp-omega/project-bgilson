package com.revature.dao;

import java.util.List;

import com.revature.models.Account;

/**
 * This is an Interface that follows the DAO Design Pattern.
 * 
 * Classes that implement this Interface will be blueprints for creating
 * AccountDAO's (Data Access Objects); an AccountDAO is responsible
 * for handling all logic that will be used to interact with the Accounts
 * table in the Database for which it is designed.
 * 
 * This leaves other parts of an application free to interact with
 * the database without needing to think about this complex logic;
 * they simply use the methods available on the Interface, as
 * implemented by the applicable DAO class.
 * 
 * ************************************************************************
 * Banking API Accounts can be created, viewed (read), updated, and deleted (i.e., CRUD).
 * Bank Employees can view (but NOT modify) information for all Users - thus, Accounts as well.
 * Bank Admins can view AND modify information for all Users - thus, Accounts as well.
 * _Standard_ Users cannot modify Accounts - but _Premium_ users can. 
 * 
 * Thus, the AccountDAO Interface/implementing class must contain all
 * CRUD (Create-Read-Update-Delete) methods.  Here, the method names
 * follow the Oracle DB command names for C...UD (insert, update, delete);
 * for R(ead), 4 methods are included:  findAll, findById, findByStatus,
 * and findByType (B. Gilson:  I added the latter as an enhancement
 * - not in the project specs).
 * 
 *   findByUsername.
 *
 * (Another (5th) R(ead) method for an Account(_s), findByUser(int userId),
 * is in the UserAccountDAO Interface/implementing class (its ONLY method),
 * as it requires User data (userId).)
 *  
 */
// NOTE:  in an Interface, all methods are public and abstract by default,
//		  whether or not they are actually specified as so.
//		  It's helpful to specify public, so that the methods can just be
//		  copied as-is to the implementing class, without having to add the
//		  public access modifier there, afterward.  (B. Gilson, from Matt)
public interface IAccountDAO {

	public int insert(Account acct); // CREATE
	
	public List<Account> findAll();  //READ
	public Account findById(int accountId); //READ
	public Account findByStatus(int statusId); //READ
	public Account findByType(int typeId); //READ  -- B.Gilson:  not in Project specs; added as enhancement
	
	public int update(Account acct); //UPDATE
	
	public int delete(int accountId); //DELETE
}
