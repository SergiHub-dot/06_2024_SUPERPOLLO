package com.sinensia.superpollo.presentation.controllers;

public class ErrorHttpCustomizado {

	private String error;
	
	public ErrorHttpCustomizado(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}
}
