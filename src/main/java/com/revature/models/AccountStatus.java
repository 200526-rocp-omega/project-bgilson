package com.revature.models;

import java.util.Objects;

/* 
 * Tracks the status of accounts.
 * Status possibilities are Pending, Open, Closed, or Denied.
 *  
 * Alternatively, this could be an Enum instead of a class:
 *  
 *  	public enum AccountStatus {
 *		  Pending, Open, Closed, Denied
 *		}
 */

public class AccountStatus {
	private int statusId; // primary key
	private String status; // not null, unique
	
	public AccountStatus() {
		super();
	}

	public AccountStatus(int statusId, String status) {
		super();
		this.statusId = statusId;
		this.status = status;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(status, statusId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AccountStatus)) {
			return false;
		}
		AccountStatus other = (AccountStatus) obj;
		return Objects.equals(status, other.status) && statusId == other.statusId;
	}

	@Override
	public String toString() {
		return "AccountStatus [statusId=" + statusId + ", status=" + status + "]";
	}
	
}
