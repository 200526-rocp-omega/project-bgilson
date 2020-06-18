package com.revature.authorization;

import javax.servlet.http.HttpSession;

import com.revature.exceptions.NotLoggedInException;
import com.revature.exceptions.RoleNotAllowedException;
import com.revature.models.User;

public class AuthService {

	// varargs; = an array of Strings
	public static void guard(HttpSession session, String...roles) {
		// Copied from UserController's findUserById method; then edited
		User currentUser = (session == null ?  null : (User) session.getAttribute("currentUser"));
		// first S/N/B possible w/o 2nd (being logged in) - BUT someone might have broken into the HTTP request and created a fake session
		// (i.e., a fake cookie on their system that our app will see as an existing session) -- e.g., could do this in Postman
		// 2nd -> OK, so there's a session for this user - but is logged in?
		if(session == null || currentUser == null) {
			throw new NotLoggedInException();
		}
		
		boolean found = false;
		//Role role = currentUser.getRole();		// B.G.:  a Role *object*
		// refactored to the below, so not calling getRole() *twice*
			// first getRole() gets the entire Role *object* from User;
			// second getRole() gets the role instance variable from Role (object)
		// create *local* variable (also) named role
		String role = currentUser.getRole().getRole();	// get role instance variable from User currentUser = Role object; get latter's role from that 
		// specs for Endpoint "Find Users By Id":
		//     Allowed Roles:  Employee or Admin or if the id provided matches the id of the current user
		// if role is not Employee or Admin, check if id for which checking is their OWN id (i.e., current user's id)
		for(String allowedRole : roles) {
			if(allowedRole.equals(role)) {
				found = true;
				break;
			}
		}

		// *Are* logged in; just didn't have the proper permissions
		if(!found) {
			throw new RoleNotAllowedException();
		}
	}
	
	// this guard method leverages the above guard method - when RoleNotAllowedException is thrown
	public static void guard(HttpSession session, int id, String...roles) {
		try {
			guard(session, roles);
		} catch(RoleNotAllowedException e) {
			User current = (User) session.getAttribute("currentUser");
			if(id != current.getUserId()) {
				throw e;
			}
		}
	}
}
