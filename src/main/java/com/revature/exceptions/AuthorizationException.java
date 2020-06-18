package com.revature.exceptions;

public class AuthorizationException extends RuntimeException {
	// Chose "Add generated serial version ID"
	private static final long serialVersionUID = 1321261700336514368L;

	// Chose "Generate Constructors from Superclass..." (ONLY)
	// (Tried first:  choosing superclass RuntimeException (java.lang)
	// *while creating*; had to add in super(); below *manually* (!),
	// and constructor order was different.  Deleted and ran the
	// post-creation version.)
	public AuthorizationException() {
		super();
	}

	public AuthorizationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthorizationException(String message) {
		super(message);
	}

	public AuthorizationException(Throwable cause) {
		super(cause);
	}
}
