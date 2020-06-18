package com.revature.exceptions;

public class RoleNotAllowedException extends AuthorizationException {
	// Chose "Add generated serial version ID"
	private static final long serialVersionUID = 8216120613659562059L;

	// Chose "Generate Constructors from Superclass..." (ONLY)
	public RoleNotAllowedException() {
		super();
	}

	public RoleNotAllowedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RoleNotAllowedException(String message, Throwable cause) {
		super(message, cause);
	}

	public RoleNotAllowedException(String message) {
		super(message);
	}

	public RoleNotAllowedException(Throwable cause) {
		super(cause);
	}
}
