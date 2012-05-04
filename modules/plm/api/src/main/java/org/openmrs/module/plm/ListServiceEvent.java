package org.openmrs.module.plm;

import java.util.EventObject;

public class ListServiceEvent extends EventObject {
	/**
	 * The service operation types.
	 */
	public enum ServiceOperation {
		/**
		 * Occurs when a list is added.
		 */
		ADDED,
		/**
		 * Occurs when a list is removed.
		 */
		REMOVED
	}

	private transient PersistentList list;
	private transient ServiceOperation operation;

	/**
	 * Constructs a list service event.
	 *
	 * @param service The {@link PersistentListService} on which the event occurred.
	 * @param list The {@link PersistentList} that the operation occurred on.
	 * @param operation The {@link ServiceOperation} that occurred.
	 * @throws IllegalArgumentException if service or list is null.
	 */
	public ListServiceEvent(PersistentListService service, PersistentList list, ServiceOperation operation) {
		super(service);

		if (list == null) {
			throw new IllegalArgumentException("The list must be defined.");
		}

		this.list = list;
		this.operation = operation;
	}

	/**
	 * Gets the {@link PersistentList} for this event.
	 * @return The {@link PersistentList} for this event.
	 */
	public PersistentList getList() {
		return list;
	}

	/**
	 * Gets the {@link ServiceOperation} for this event.
	 * @return The {@link ServiceOperation} for this event.
	 */
	public ServiceOperation getOperation() {
		return operation;
	}
}
