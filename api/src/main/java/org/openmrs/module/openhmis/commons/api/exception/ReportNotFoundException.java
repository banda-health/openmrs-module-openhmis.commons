package org.openmrs.module.openhmis.commons.api.exception;

import org.openmrs.api.APIException;

/**
 * Represents an exception that occurs when a report file cannot be found.
 */
public class ReportNotFoundException extends APIException {
	public static final long serialVersionUID = 22323L;

	public ReportNotFoundException() {
		super();
	}

	public ReportNotFoundException(String message) {
		super(message);
	}

	public ReportNotFoundException(Throwable cause) {
		super(cause);
	}

	public ReportNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
