package com.revature.services;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.revature.dao.IUserDAO;
import com.revature.dao.UserDAO;
import com.revature.exceptions.NotLoggedInException;
import com.revature.models.User;
import com.revature.templates.LoginTemplate;

// The service layer is a layer that is designed to enforce your "business logic"
// These are miscellaneous rules that define how your application will function
// 		Ex:  May not withdraw money over the current balance
// All interaction with the DAO's will be through the service layer
// (B.G.:  means our main will not directly talk to the DAO's (or to the database))
// This design is simply furthering the same design structure that we have used up to now
// (keeping functionality separate)
// How you go about designing the details of this layer is up to you
// Due to the nature of the "business logic" being rather arbitrary,
// this layer has the MOST creativity involved
// Most other layers are pretty boilerplate, where you pretty much copy/paste most methods
//
// The UserService class enforces this Banking API's business logic with respect
// to Users, and interacts with the UserDAO to communicate with the database.
public class UserService {

	// Therefore, he can only provide some _guidelines_ for the code here ...
	
	private IUserDAO dao = new UserDAO();
	
	// A starting place Matt likes to use:  to also create CRUD methods in the service layer
	// that will be used to interact with the DAO
	
	// Then additionally, you can have extra methods to enforce whatever features/rules that you want
	// For example, we might also have a login/logout method
	
	public int insert(User u) {
		return dao.insert(u);
	}
	
	public List<User> findAll() {
		return dao.findAll();
	}
	
	public User findById(int userId) {
		return dao.findById(userId);
	}
	
	public User findByUsername(String username) {
		return dao.findByUsername(username);
	}
	
	public int update(User u) {
		return dao.update(u);
	}
	
	public int delete(int userId) {
		return dao.delete(userId);
	}
	
	public User login(LoginTemplate lt) {
		
		User userFromDB = findByUsername(lt.getUsername());
		
		// Username was incorrect
		if(userFromDB == null) {
			return null;	// a return value of null means that there's NO (such) User
		}
		
		// Username was correct and so was password
		if(userFromDB.getPassword().equals(lt.getPassword())) {
			return userFromDB;
		}
		
		// Username was correct, but password was not (drop-through from the 2 cases above)
		return null;		// a return value of null means that there's NO (such) User
	}
	
	public void logout(HttpSession session) {
		// They were never logged in to begin with
			// B.G.:  Per Matt, have to ALSO include session == null because, although unlikely,
			// the session could be expired, but someone fakes a cookie ... would be bad news
		if(session == null || session.getAttribute("currentUser") == null) {
			throw new NotLoggedInException("User must be logged in, in order to logout.");
		}
		
		// terminate the session
		session.invalidate(); // Invalidates this session then unbinds any objects bound to it.
	}
}