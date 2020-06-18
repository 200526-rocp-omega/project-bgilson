package com.revature.dao;

import java.util.List;

import com.revature.models.User;

/**
 * This is an Interface that follows the DAO Design Pattern.
 * 
 * Classes that implement this Interface will be blueprints for
 * creating UserDAO's (Data Access Objects); a UserDAO is responsible
 * for handling all logic that will be used to interact with the Users
 * table in the Database for which it is designed.
 * 
 * This leaves other parts of an application free to interact with
 * the database without needing to think about this complex logic;
 * they simply use the methods available on the Interface, as
 * implemented by the applicable DAO class.
 * 
 * ************************************************************************
 * Banking API Users can register, view, and update their own information.
 * Bank Employees can view (but NOT modify) information for all Users.
 * Bank Admins can view AND modify information for all Users.
 * 
 * Thus, the UserDAO Interface/implementing class must contain all
 * CRUD (Create-Read-Update-Delete) methods.  Here, the method names
 * follow the Oracle DB command names for C...UD (insert, update, delete);
 * for R(ead), 3 methods are included:  findAll, findById, and findByUsername.
 * 
 */
// NOTE:  in an Interface, all methods are public and abstract by default,
//		  whether or not they are actually specified as so.
//		  It's helpful to specify public, so that the methods can just be
//		  copied as-is to the implementing class, without having to add the
//		  public access modifier there, afterward.  (B. Gilson, from Matt)
public interface IUserDAO {

	public int insert(User u); // CREATE
	
	public List<User> findAll();  //READ
	public User findById(int userId); //READ
	public User findByUsername(String username); //READ
	
	public int update(User u); //UPDATE
	
	public int delete(int userId); //DELETE
}
