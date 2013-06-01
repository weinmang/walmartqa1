package com.cloudbees.walmartqa1.exception;

public class ApiException extends Exception {

	public ApiException() {
	}

	public ApiException(String arg0) {
		super(arg0);
	}

	public ApiException(Throwable arg0) {
		super(arg0);
	}

	public ApiException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ApiException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
