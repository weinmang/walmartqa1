package com.cloudbees.walmartqa1.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8332072612612865311L;

	public NotFoundException(String arg0) {
		super(arg0);
	}

	public NotFoundException(Throwable arg0) {
		super(arg0);
	}

	public NotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NotFoundException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
