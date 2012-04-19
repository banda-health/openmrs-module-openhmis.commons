package org.openmrs.module.plm;

public class PersistentListException extends RuntimeException {
	public PersistentListException() {}

	public PersistentListException(String message) {
		super(message);
	}

	public PersistentListException(Throwable cause) {
		super(cause);
	}

	public PersistentListException(String message, Throwable cause) {
		super(message,  cause);
	}
}
