package org.openmrs.module.plm;

import java.util.EventListener;

/**
 * Represents types that can handle {@link ListEvent}'s.
 */
public interface ListEventListener extends EventListener {
	/**
	 * Called when a {@link PersistentListItem} is added to a list.
	 * @param event The event information.
	 */
	void itemAdded(ListEvent event);

	/**
	 * Called when a {@link PersistentListItem} is removed from a list.
	 * @param event The event information.
	 */
	void itemRemoved(ListEvent event);

	/**
	 * Called when a {@link PersistentList} is cleared of all items.
	 * @param event The event information.
	 */
	void listCleared(ListEvent event);
}

