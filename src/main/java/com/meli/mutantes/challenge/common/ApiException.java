package com.meli.mutantes.challenge.common;

public class ApiException extends Exception {
	private static final long serialVersionUID = 5962553423029173333L;

	public ApiException(String messages) {
		super(messages);
	}
}
