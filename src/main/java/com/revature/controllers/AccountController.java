package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.revature.services.UserService;

public class AccountController {

	private final AccountService accountService = new AccountService();
	
	// Need at least these methods:
	public List<User> findAllAccounts() {		// need to write the functionality
	List<User> allAccounts = new ArrayList<>();
		
		return allAccounts;
	}
	
	public int allUsersForAccount(int accountId) {
		
		return 0;
	}
	
	public Account findAccountById(int accountId) {
	
		return accountService.findById(accountId);
	}
	

}
