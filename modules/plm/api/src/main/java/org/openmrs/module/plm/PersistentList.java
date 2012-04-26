package org.openmrs.module.plm;

import org.openmrs.module.plm.model.PersistentListModel;

/**
 * Represents classes that can persist items and return them according to some data structure.
 */
public interface PersistentList {
	/**
	 * Gets the list key.
	 * @return The list key.
	 */
	String getKey();

	/**
	 * Gets the list description.
	 * @return The list description.
	 */
	String getDescription();

	/**
	 * Sets the list description.
	 * @param description The list description.
	 */
	void setDescription(String description);

	/**
	 * Gets the list id or <code>null</code> if the list has not yet been persisted.
	 * @return The list id.
	 */
	Integer getId();

	/**
	 * Gets the {@link PersistentListProvider}.
	 * @return The {@link PersistentListProvider}.
	 */
	PersistentListProvider getProvider();

	/**
	 * Sets the {@link PersistentListProvider}
	 * @param provider The {@link PersistentListProvider}
	 */
	void setProvider(PersistentListProvider provider);

	/**
	 * Loads the settings from the specified {@link PersistentListModel}.
	 * @param model The {@link PersistentListModel} to load from.
	 */
	void load(PersistentListModel model);

	/**
	 * Adds new {@link PersistentListItem} to the list.
	 * @param items The {@link PersistentListItem}'s to add.
	 */
	void add(PersistentListItem... items);

	/**
	 * Removes the specified {@link PersistentListItem} from the list.
	 * @param item The {@link PersistentListItem} to remove.
	 * @return {@code true} if removed; otherwise, {@code false}.
	 */
	boolean remove(PersistentListItem item);

	/**
	 * Clears all items from the list.
	 */
	void clear();

	/**
	 * Gets all the {@link PersistentListItem}'s in the list in the proper list order.
	 * @return The list {@link PersistentListItem}'s.
	 */
	PersistentListItem[] getItems();

	/**
	 * Gets the next {@link PersistentListItem} as defined by the list implementation.  The item is not removed.
	 * @return The next {@link PersistentListItem} or {@code null} if no items are defined.
	 */
	PersistentListItem getNext();

	/**
	 * Gets the next {@link PersistentListItem} as defined by the list implementation and removes it from the list.
	 * @return The next {@link PersistentListItem}.
	 */
	PersistentListItem getNextAndRemove();

	/**
	 * Gets the number of items currently in the list.
	 * @return The number of items currently in the list.
	 */
	int getSize();
}

