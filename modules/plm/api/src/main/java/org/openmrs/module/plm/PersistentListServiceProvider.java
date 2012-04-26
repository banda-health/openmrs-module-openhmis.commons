package org.openmrs.module.plm;

import org.openmrs.module.plm.model.PersistentListModel;

/**
 * Represents classes that handle loading and saving {@link PersistentList}'s.
 */
public interface PersistentListServiceProvider {
	/**
	 * Gets all the {@link PersistentList}'s that are currently defined.
	 * @return The lists.
	 */
	PersistentListModel[] getLists();

	/**
	 * Adds a {@link PersistentList}.
	 * @param list The {@link PersistentList} to persist.
	 */
	void addList(PersistentListModel list);

	/**
	 * Removes a {@link PersistentList}.
	 * @param key The key of teh {@link PersistentList} to remove.
	 */
	void removeList(String key);
}
