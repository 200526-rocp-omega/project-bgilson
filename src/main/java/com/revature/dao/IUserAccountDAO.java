package com.revature.dao;

import java.util.List;

import com.revature.models.Account;

/**
 * This is an Interface that follows the DAO Design Pattern.
 * 
 * Classes that implement this Interface will be blueprints for creating
 * UserAccountDAO's (Data Access Objects); a UserAccountDAO is responsible
 * for handling all logic that will be used to interact with the
 * Users_Accounts *join* table in the Database for which it is designed.
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
 * The UserAccountDAO Interface/implementing class contains ONLY
 * the CRUD (Create-Read-Update-Delete) method for findByUser(int userId)
 * for an Account(_s) - a R(ead) operation (returns a List<Account>).
 * 
 * (The other CRUD methods for an Account(_s) are in the AccountDAO
 *  Interface/implementing class, as they don't require User data (userId):
 * 						  C...UD:  insert, update, delete
 * 		4 *other* R(ead) methods:  findAll, findById, findByStatus, and findByType
 * 		  (B. Gilson:  I added the latter as an enhancement - not in the project specs) )
 * 
 */
// NOTE:  in an Interface, all methods are public and abstract by default,
//		  whether or not they are actually specified as so.
//		  It's helpful to specify public, so that the methods can just be
//		  copied as-is to the implementing class, without having to add the
//		  public access modifier there, afterward.  (B. Gilson, from Matt)
public interface IUserAccountDAO {
	
	public List<Account> findByUser(int userId); // READ

}
