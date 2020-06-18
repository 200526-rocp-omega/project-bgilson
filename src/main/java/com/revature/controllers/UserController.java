package com.revature.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.revature.exceptions.NotLoggedInException;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.UserService;

public class UserController {

	private final UserService userService = new UserService();
	
	public boolean logout(HttpSession session) {
		try {
			userService.logout(session);
		} catch(NotLoggedInException e) {
			return false;
		}
		return true;
	}
	
	public User findUserById(int id) {
	// added Fri!!
	//public User findUserById(HttpSession session, int id) {
/*		//User currentUser = (User) session.getAttribute("currentUser");	-- possible NullPointerException, if no session (null); refactor to:
		User currentUser = (session == null ?  null : (User) session.getAttribute("currentUser"));
		// first S/N/B possible w/o 2nd (being logged in) - BUT someone might have broken into the HTTP request and created a fake session
		// (i.e., a fake cookie on their system that our app will see as an existing session) -- e.g., could do this in Postman
		// 2nd -> OK, so there's a session for this user - but is logged in?
		if(session == null || currentUser == null) {
			throw new NotLoggedInException();
		}
		
		//Role role = currentUser.getRole();		// B.G.:  a Role *object*
		//if(!role.getRole().equals("Employee") && !role.getRole().equals("Admin")) { // B.G.:  get role instance variable within the Role object
		// refactored to the below, so not calling getRole() *twice*
			// first getRole() gets the entire Role *object* from User;
			// second getRole() gets the role instance variable from Role (object)
		// create *local* variable (also) named role
		String role = currentUser.getRole().getRole();	// get role instance variable from User currentUser = Role object; get latter's role from that 
		// specs for Endpoint "Find Users By Id":
		//     Allowed Roles:  Employee or Admin or if the id provided matches the id of the current user
		// if role is not Employee or Admin, check if id for which checking is their OWN id (i.e., current user's id)
		if( (!role.equals("Employee") && !role.equals("Admin")) && currentUser.getId() != id) {
			// The User does not have permission [B.G.:  to see this User's info (via findUseribyId)]
			throw new NotLoggedInException();
		} */
		// replace above with newly-written call to AuthService class' methods ***in FrontController***
		
		return userService.findById(id);
		
	}

	public List<User> findAllUsers() {
	// added Fri!!
	//public List<User> findAllUsers(HttpSession session) {
/*		//pasted entire findUserById body; then edited
		User currentUser = (session == null ?  null : (User) session.getAttribute("currentUser"));
		// first S/N/B possible w/o 2nd (being logged in) - BUT someone might have broken into the HTTP request and created a fake session
		// (i.e., a fake cookie on their system that our app will see as an existing session) -- e.g., could do this in Postman
		// 2nd -> OK, so there's a session for this user - but is logged in?
		if(session == null || currentUser == null) {
			throw new NotLoggedInException();		
		}
		
		// create *local* variable (also) named role
			// first getRole() gets the entire Role *object* from User;
			// second getRole() gets the role instance variable from Role (object)
		String role = currentUser.getRole().getRole();	// get role instance variable from User currentUser = Role object; get latter's role from that 
		// specs for Endpoint "Find Users By Id":
		//     Allowed Roles:  Employee or Admin or if the id provided matches the id of the current user
		// if role is not Employee or Admin
		if(!role.equals("Employee") && !role.equals("Admin")) {
			// The User does not have permission [B.G.:  to see this User's info (via findUseribyId)]
			throw new NotLoggedInException();
		} */
		// replace above with newly-written call to AuthService class' methods ***in FrontController***
		
		return userService.findAll();		
	}
	
	public int updateUser(User u) {
		return userService.update(u);
	}
}
