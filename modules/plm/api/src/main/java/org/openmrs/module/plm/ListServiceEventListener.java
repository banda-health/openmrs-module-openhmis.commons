package org.openmrs.module.plm;

import java.util.EventListener;

/**
 * Represents types that can handle {@link ListServiceEvent}'s.
 */
public interface ListServiceEventListener extends EventListener {
	/**
	 * Called when a {@link PersistentList} is added to the list service.
	 * @param event The event information.
	 */
	void listAdded(ListServiceEvent event);

	/**
	 * Called when a {@link PersistentList} is removed from the list service.
	 * @param event The event information.
	 */
	void listRemoved(ListServiceEvent event);
}

