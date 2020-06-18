package com.revature.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.authorization.AuthService;
import com.revature.controllers.UserController;
import com.revature.exceptions.AuthorizationException;
import com.revature.exceptions.NotLoggedInException;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.templates.LoginTemplate;
import com.revature.templates.MessageTemplate;
import com.revature.templates.UpdateUserTemplate;

//Browse...d for Superclass on class creation (-> "extends HttpServlet")
public class FrontController extends HttpServlet {
	// to get rid of the warning:
	//		The serializable class FrontController does not declare
	//		a static final serialVersionUID field of type long
	// Choose the 2nd option, "Add a generated serial version ID"
	private static final long serialVersionUID = -4854248294011883310L;
	private static final UserController userController = new UserController();
		// use to extract the JSON from the request body and put it into a new Object
	  	// (ObjectMapper is from jackson.databind !!!)
	private static final ObjectMapper om = new ObjectMapper();
	private static final UserService service = new UserService();

	@Override	// add to help protect against typos, etc.
				// (forces a quick check during compilation that
				// are actually overriding a method)
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		res.setContentType("application/json");
		res.setStatus(404);	// Not Found (Client Error)
		
		final String URI = req.getRequestURI().replace("/rocp-project", "").replaceFirst("/", "");
		
		String[] portions = URI.split("/");
		
		//System.out.println(URI);
		System.out.println(Arrays.toString(portions)); // later commented out!
		
		try {
			switch(portions[0]) { // diff endpoints, but else same (logic) as for doPost
			case "users":
				// check the length of (# of entries in) "portions" (specs:  only 2 endpoints 
				// w/ "users":  /users and /users/:id  (1 and 2 portions, respectively))
				if(portions.length == 2) { // orig. had > 1; then Bryan!
					// Delegate to a Controller method to handle obtaining a User by ID
					int id = Integer.parseInt(portions[1]);
					// getSession() "false" parameter -> don't create a new session if not logged in
					AuthService.guard(req.getSession(false), id, "Employee", "Admin");
					User u = userController.findUserById(id);
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(u));
				} else {
					// Delegate to a Controller method to handle obtaining ALL Users
					// getSession() "false" parameter -> don't create a new session if not logged in
					AuthService.guard(req.getSession(false), "Employee", "Admin");
					List<User> all = userController.findAllUsers();
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(all));
				}
				break;
			case "accounts":
				// MUST FLESH OUT
				break;
			}
		} catch(AuthorizationException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("The incoming token has expired");
			res.getWriter().println(om.writeValueAsString(message));
		}
	}

	@Override	// add @Override annotation to help protect against typos, etc.
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

		// add this so response will display in (be detected as) JSON format
		res.setContentType("application/json");
		res.setStatus(404);	// Not Found (Client Error)
		
		// not need the entire URL -- just the "string" portion
		final String URI = req.getRequestURI().replace("/rocp-project", "").replaceFirst("/", "");
		
		String[] portions = URI.split("/");
		
		// print out the URI to check it
		//System.out.println(URI);
		System.out.println(Arrays.toString(portions)); // later commented out!
		
		try {
			switch(portions[0]) {
			case "login":
				// put the response info into the (user's HTTP) session
				// In Postman, this caused an extra Header line, *and* a Cookie!
					// It put the JSESSIONID value (plus 2 other pieces of
					// data from the cookie) into the new Set-Cookie header 
				// Thus, can use this to check user's(') cookies to see
				// if they've logged in (and/or are *currently* logged in)
				HttpSession session = req.getSession();
				
				// currentUser attribute got defined in a *previous* call
				// to this method (defined way down below (setAttribute))
				User current = (User) session.getAttribute("currentUser");

				
				// Already logged in
				if(current != null) {
					
					res.setStatus(400); // Bad Request (Client Error)
					// use ... .print("...") if don't want a /n (newline) at the end
					// (we see this in the Response in Postman);
					// per Matt, it's more common to end with a /n character ...
					res.getWriter().println("You are already logged in as user " + current.getUsername());
					return;
				}
				
				
				// If not already logged in, get the login info
				// contained in the request, to use for login attempt				
				
				BufferedReader reader = req.getReader();
				// reader.readLine() is a method use a lot;
				// returns null when done (!)
				
				StringBuilder sb = new StringBuilder();
				
				String line;	// will read/store a line at a time

				/*
				 * The (line = reader.readLine()) obtains a single line from
				 * the body of the request and stores it in the line variable.
				 * Then the != null compares line to null; if IS null, are
				 * at end of the request body.
				 */
				while ( (line = reader.readLine()) != null) {
					sb.append(line);
				}
				
				String body = sb.toString();

				//om.readValue(String content, Class <T> valueType)
					// the ".class" syntax means that at runtime,
					// Jackson data-bind analyzes all of the specified
					// class' variables, and makes sure that [the input (JSON) ?!?] matches them
				LoginTemplate lt = om.readValue(body, LoginTemplate.class);
				
				// Matt just used to verify input initially; later commented out (removed!)
				//System.out.println(lt); // prints lt to STS Console
						// WHEN send a request in Postman with the JSON to /login ,
						// output is (per toString() method in LoginTemplate.java):
						//		LoginTemplate [username=lbeaver, password=chew]
				
				
				// Attempt to login using the info obtained from the request				
				User u = service.login(lt);
				PrintWriter writer = res.getWriter(); // open a getWriter() on the res[ponse]
				
				// Handle the results (login failure/success)

				// Login failed (NO (such) User (in DB lookup))
				if(u == null) { // B.G.: WE only put in username and password
					res.setStatus(400); // Bad Request (Client Error)
					writer.println("Invalid Credentials:  Username or password was incorrect");
					return;
				}
				
				// Login successful
				session.setAttribute("currentUser", u);
				res.setStatus(200); // OK (Successful)
				writer.println(om.writeValueAsString(u));
				
				// add this so response will display in (be detected as) JSON format
				res.setContentType("application/json");
				
				// COULD do all of the above with the below (instead of using a BufferedReader,
				// the while loop (building the StringBuilder line-by-line), and changing the
				// StringBuilder toString() ) -- BECAUSE there's apparently a new overload for
				// readValue:		readValue(Reader src, Class <T> valueType) 
				// LoginTemplate lt = om.readValue(req.getReader(), LoginTemplate.class);
				break;
			case "logout":
				if(userController.logout(req.getSession(false))) {
					res.setStatus(200); // OK (Successful)
					res.getWriter().println("You have been successfully logged out");
				} else {
					res.setStatus(400); // Bad Request (Client Error)
					res.getWriter().println("You were not logged in to begin with");
				}
				break;
			}
		} catch(NotLoggedInException e) {
			res.setStatus(401); // Unauthorized (Client Error) 
			MessageTemplate message = new MessageTemplate("The incoming token has expired");
			res.getWriter().println(om.writeValueAsString(message));
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

		// add this so response will display in (be detected as) JSON format
		res.setContentType("application/json");
		res.setStatus(404);	// Not Found (Client Error)
		
		// not need the entire URL -- just the "string" portion
		final String URI = req.getRequestURI().replace("/rocp-project", "").replaceFirst("/", "");
		
		String[] portions = URI.split("/");
		
		// print out the URI to check it
		//System.out.println(URI);
		System.out.println(Arrays.toString(portions)); // later commented out!
		
		try {
			switch(portions[0]) { // same endpoint as *1* in doGet; same (logic)
			case "users":	// same endpoint as *1* in doGet; same (logic)
				// check the length of (# of entries in) "portions"
				// (*specs* just say "/users")
				if(portions.length == 1) {
/*					// Delegate to a Controller method to handle updating a User
					//int id = Integer.parseInt(portions[1]);
					int id = 
					// getSession() "false" parameter -> don't create a new session if not logged in
					AuthService.guard(req.getSession(false), id, "Admin");
					User u = userController.updateUser(id);
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(u)); */
					
					
					// get the User data contained in the request				
					
					BufferedReader reader = req.getReader();
					StringBuilder sb = new StringBuilder();
					String line;	// will read/store a line at a time
					
					/*
					 * The (line = reader.readLine()) obtains a single line from
					 * the body of the request and stores it in the line variable.
					 * Then the != null compares line to null; if IS null, are
					 * at end of the request body.
					 * 
					 * readline() returns null when done
					 */
					while ( (line = reader.readLine()) != null) {
						sb.append(line);
					}
					
					String body = sb.toString();

					//om.readValue(String content, Class <T> valueType)
						// the ".class" syntax means that at runtime,
						// Jackson data-bind analyzes all of the specified
						// class' variables, and makes sure that [the input (JSON) ?!?] matches them
					UpdateUserTemplate uut = om.readValue(body, UpdateUserTemplate.class);
					
					// use to verify input initially, if needed
					System.out.println(uut); // prints lt to STS Console
							// WHEN send a request in Postman with the JSON to /login ,
							// output is (per toString() method in UpdateUserTemplate.java)
					
					
					// Attempt to login using the info obtained from the request				
//					User u = service.update(uut);
//					PrintWriter writer = res.getWriter(); // open a getWriter() on the res[ponse]
					
					// Handle the results (login failure/success)

					// Login failed (NO (such) User (in DB lookup))
/*					if(u == null) { // B.G.: WE only put in username and password
						res.setStatus(400); // Bad Request (Client Error)
						writer.println("Invalid Credentials:  Username or password was incorrect");
						return;
					}
*/					
					// Login successful
//					session.setAttribute("currentUser", u);
//					res.setStatus(200); // OK (Successful)
//					writer.println(om.writeValueAsString(u));
					
					// add this so response will display in (be detected as) JSON format
					res.setContentType("application/json");
					
					// COULD do all of the above with the below (instead of using a BufferedReader,
					// the while loop (building the StringBuilder line-by-line), and changing the
					// StringBuilder toString() ) -- BECAUSE there's apparently a new overload for
					// readValue:		readValue(Reader src, Class <T> valueType) 
					// LoginTemplate lt = om.readValue(req.getReader(), LoginTemplate.class);
					break;
					
					
					
				}/* else {
					// Delegate to a Controller method to handle obtaining ALL Users
					// getSession() "false" parameter -> don't create a new session if not logged in
					AuthService.guard(req.getSession(false), "Admin");
					List<User> all = userController.findAllUsers();
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(all));
				}*/
				break;
			case "accounts":
				// MUST FLESH OUT
				break;
			}
		} catch(AuthorizationException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("The incoming token has expired");
			res.getWriter().println(om.writeValueAsString(message));
		}
	}
}