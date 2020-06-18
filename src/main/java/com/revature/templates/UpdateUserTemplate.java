package com.revature.templates;

import java.util.Objects;

import com.revature.models.Role;

//used for Jackson data-bind - compare JSON input to this
public class UpdateUserTemplate {

	private int userId;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private Role role;
	
	public UpdateUserTemplate() {
		super();
	}

	// Jackson data-bind doesn't need THIS constructor - but *we* might
	public UpdateUserTemplate(int userId, String username, String password, String firstName, String lastName,
			String email, Role role) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, lastName, password, role, userId, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof UpdateUserTemplate)) {
			return false;
		}
		UpdateUserTemplate other = (UpdateUserTemplate) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& Objects.equals(role, other.role) && userId == other.userId
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "UpdateUserTemplate [userId=" + userId + ", username=" + username + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", role=" + role + "]";
	}
	
}


/*

package com.revature.templates;

import java.util.Objects;

import com.revature.models.Role;

//used for Jackson data-bind - compare JSON input to this
public class UpdateUserTemplate {

	private int userId;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	//private Role role;
	private int roleId;
	private String roleName;
	
	public UpdateUserTemplate() {
		super();
	}

	// Jackson data-bind doesn't need THIS constructor - but *we* might
//	public UpdateUserTemplate(int userId, String username, String password, String firstName, String lastName,
//			String email, Role role) {
	public UpdateUserTemplate(int userId, String username, String password, String firstName, String lastName,
			String email, int roleId, String roleName) {

		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		//this.role = role;
		this.roleId = roleId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

/*	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	} */

/**	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, lastName, password, role, userId, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof UpdateUserTemplate)) {
			return false;
		}
		UpdateUserTemplate other = (UpdateUserTemplate) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& Objects.equals(role, other.role) && userId == other.userId
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "UpdateUserTemplate [userId=" + userId + ", username=" + username + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", role=" + role + "]";
	}
	
}
**/
