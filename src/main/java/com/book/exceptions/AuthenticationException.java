package com.book.exceptions;

public class AuthenticationException extends Exception {
	private static final long serialVersionUID = -7245791319533227144L;
	
	public AuthenticationException(String message) {
		super(message);
	}
}
