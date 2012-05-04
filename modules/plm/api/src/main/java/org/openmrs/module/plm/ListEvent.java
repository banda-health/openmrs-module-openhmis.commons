package org.openmrs.module.plm;

import java.util.EventObject;

/**
 * The type that contains the information about a list event.
 */
public class ListEvent extends EventObject {
	/**
	 * The list operation types.
	 */
	public enum ListOperation {
		/**
		 * Occurs when an item is added to the list.
		 */
		ADDED,
		/**
		 * Occurs when an item is removed from the list.
		 */
		REMOVED,
		/**
		 * Occurs when all the items are cleared from the list.
		 */
		CLEARED
	}

	private transient PersistentListItem item;
	private transient ListOperation operation;

	/**
	 * Constructs the list event.
	 *
	 * @param list The {@link PersistentList} on which the event initially occurred.
	 * @param item The {@link PersistentListItem} that the operation occurred upon.
	 * @param operation The {@link ListOperation} that occurred.
	 * @throws IllegalArgumentException if list is null or if the item is null and the operation is not CLEARED.
	 */
	public ListEvent(PersistentList list, PersistentListItem item, ListOperation operation) {
		super(list);

		if (operation != ListOperation.CLEARED && item == null) {
			throw new IllegalArgumentException("The item must be defined when the operation is not CLEARED.");
		}

		this.item = item;
		this.operation = operation;
	}

	/**
	 * Gets the {@link PersistentListItem} for this event.
	 * @return The {@link PersistentListItem} for this event.
	 */
	public PersistentListItem getItem() {
		return item;
	}

	/**
	 * Gets the operation that occurred to fire this event.
	 * @return The {@link ListOperation} that occurred.
	 */
	public ListOperation getOperation() {
		return operation;
	}
}

