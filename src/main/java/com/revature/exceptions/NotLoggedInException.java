package com.revature.exceptions;

public class NotLoggedInException extends AuthorizationException {
	// Chose "Add generated serial version ID"
	private static final long serialVersionUID = 1232046471078479580L;

	// Chose "Generate Constructors from Superclass..." (ONLY)
	public NotLoggedInException() {
		super();
	}

	public NotLoggedInException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotLoggedInException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotLoggedInException(String message) {
		super(message);
	}

	public NotLoggedInException(Throwable cause) {
		super(cause);
	}
}
