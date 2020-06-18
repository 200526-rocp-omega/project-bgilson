package com.revature.models;

import java.util.Objects;

/* 
 * Tracks the kind of account.
 * Type possibilities are Checking or Savings.
 * 
 * Alternatively, this could be an Enum instead of a class:
 *  
 *		public enum AccountType {
 *		  Checking, Savings
 *		}
 */

public class AccountType {
	private int typeId; // primary key
	private String type; // not null, unique
	
	public AccountType() {
		super();
	}

	public AccountType(int typeId, String type) {
		super();
		this.typeId = typeId;
		this.type = type;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, typeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AccountType)) {
			return false;
		}
		AccountType other = (AccountType) obj;
		return Objects.equals(type, other.type) && typeId == other.typeId;
	}

	@Override
	public String toString() {
		return "AccountType [typeId=" + typeId + ", type=" + type + "]";
	}
	
}
