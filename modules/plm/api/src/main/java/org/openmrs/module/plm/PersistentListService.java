package org.openmrs.module.plm;

import org.openmrs.api.OpenmrsService;

import java.util.Collection;

/**
 * Represents classes that can create and remove persistent lists.
 */
public interface PersistentListService extends OpenmrsService {
	/**
	 * Checks that the specified list exists and if it does not, creates it.
	 * @param list The list to check.
	 */
	void ensureList(PersistentList list);

	/**
	 * Removes the list and associated items.
	 * @param key The key of the list to remove.
	 */
    void removeList(String key);

	/**
	 * Gets the lists that are currently defined in the system.
	 * @return An array containing the lists or an empty array if no lists are defined.
	 */
    PersistentList[] getLists();

	/**
	 * Gets the specified list or <code>null</code> if this list is not found.
	 * @param key The list key.
	 * @return The list or <code>null</code> if the list cannot be found.
	 */
    PersistentList getList(String key);
}

